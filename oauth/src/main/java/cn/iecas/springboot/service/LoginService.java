package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.DepartmentBean;
import cn.iecas.springboot.bean.UserBean;
import cn.iecas.springboot.convert.UserConvert;
import cn.iecas.springboot.dao.*;
import cn.iecas.springboot.framework.common.enums.StatusEnum;
import cn.iecas.springboot.framework.config.properties.JwtProperties;
import cn.iecas.springboot.framework.config.properties.SpringBootMasterProperties;
import cn.iecas.springboot.framework.core.util.SaltUtil;
import cn.iecas.springboot.framework.shiro.cache.LoginRedisService;
import cn.iecas.springboot.framework.shiro.jwt.JwtToken;
import cn.iecas.springboot.framework.shiro.util.JwtTokenUtil;
import cn.iecas.springboot.framework.shiro.util.JwtUtil;
import cn.iecas.springboot.framework.shiro.vo.LoginUserVo;
import cn.iecas.springboot.framework.util.PasswordUtil;
import cn.iecas.springboot.param.LoginParam;
import cn.iecas.springboot.vo.LoginUserTokenVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 登录服务实现类
 * </p>
 *
 * @author ch
 * @date 2021-10-15
 **/
@Api
@Slf4j
@Service
public class LoginService {

    @Lazy
    @Autowired
    private UserDao userDao;

    @Lazy
    @Autowired
    private DepartmentDao departmentDao;

    @Lazy
    @Autowired
    private UserRoleDao userRoleDao;

    @Lazy
    @Autowired
    private PermissionDao permissionDao;

    @Lazy
    @Autowired
    private JwtProperties jwtProperties;

    @Lazy
    @Autowired
    private LoginRedisService loginRedisService;

    @Lazy
    @Autowired
    private RedisTemplate redisTemplate;

    @Lazy
    @Autowired
    private SpringBootMasterProperties springBootMasterProperties;

    @Transactional(rollbackFor = Exception.class)
    public LoginUserTokenVo login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        // 从数据库中获取登录用户信息
        UserBean user = userDao.findUserBeanByUsername(username);
        if (user == null) {
            log.error("登录失败,loginParam:{}", loginParam);
            throw new AuthenticationException("用户名或密码错误");
        }
        if (StatusEnum.DISABLE.getCode().equals(user.getState())) {
            throw new AuthenticationException("账号已禁用");
        }

        // 实际项目中，前端传过来的密码应先加密
        // 原始密码明文：123456
        // 原始密码前端加密：sha256(123456)
        // 后台加密规则：sha256(sha256(123456) + salt)
        String encryptPassword = PasswordUtil.encrypt(loginParam.getPassword(), user.getSalt());
        if (!encryptPassword.equals(user.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        // 将系统用户对象转换成登录用户对象
        LoginUserVo loginUserVo = UserConvert.INSTANCE.userToLoginUserVo(user);

        // 获取部门
        if (!departmentDao.existsById(user.getDepartmentId())) {
            throw new AuthenticationException("部门不存在");
        }
        DepartmentBean department = departmentDao.getOne(user.getDepartmentId());
        if (StatusEnum.DISABLE.getCode().equals(department.getState())) {
            throw new AuthenticationException("部门已禁用");
        }
        loginUserVo.setDepartmentId(department.getId())
                .setDepartmentName(department.getName());

        // 获取当前用户角色
        List<String> roleIds = userRoleDao.findRoleCodesByUid(user.getId());
        if (CollectionUtils.isEmpty(roleIds)) {
            throw new AuthenticationException("角色列表不能为空");
        }
        loginUserVo.setRoleCodes(new HashSet<>(roleIds));

        // 获取当前用户权限
        List<String> permissionCodes = permissionDao.findPermissionCodesByUserId(user.getId());
        if (CollectionUtils.isEmpty(permissionCodes)) {
            throw new AuthenticationException("权限列表不能为空");
        }
        loginUserVo.setPermissionCodes(new HashSet<>(permissionCodes));

        // 获取数据库中保存的盐值
        String newSalt = SaltUtil.getSalt(user.getSalt(), jwtProperties);

        // 生成 token 字符串并返回
        Long expireSecond = jwtProperties.getExpireSecond();
        String token = JwtUtil.generateToken(username, newSalt, Duration.ofSeconds(expireSecond));
        log.debug("token:{}", token);

        // 创建 AuthenticationToken
        JwtToken jwtToken = JwtToken.build(token, username, newSalt, expireSecond);

        // 从 SecurityUtils 里边创建一个 subject
        boolean enableShiro = springBootMasterProperties.getShiro().isEnable();
        if (enableShiro) {
            // 从SecurityUtils里边创建一个 subject
            Subject subject = SecurityUtils.getSubject();
            // 执行认证登录
            subject.login(jwtToken);
        } else {
            log.warn("未启用Shiro");
        }

        // 缓存登录信息到 redis
        loginRedisService.cacheLoginInfo(jwtToken, loginUserVo);
        log.debug("登录成功,username:{}", username);

        // 缓存登录信息到redis
        String tokenSha256 = DigestUtils.sha256Hex(token);
        redisTemplate.opsForValue().set(tokenSha256, loginUserVo, 1, TimeUnit.DAYS);

        // 返回 token 和登录用户信息对象
        LoginUserTokenVo loginUserTokenVo = new LoginUserTokenVo()
                .setToken(token)
                .setLoginUserVo(loginUserVo);
        return loginUserTokenVo;
    }

    public void logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        // 获取token
        String token = JwtTokenUtil.getToken(request);
        String username = JwtUtil.getUsername(token);
        // 删除Redis缓存信息
        loginRedisService.deleteLoginInfo(token, username);
        log.info("登出成功,username:{},token:{}", username, token);
    }
}
