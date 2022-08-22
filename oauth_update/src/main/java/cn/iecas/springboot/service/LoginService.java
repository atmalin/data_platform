package cn.iecas.springboot.service;

import cn.iecas.springboot.convert.UserConvert;
import cn.iecas.springboot.dao.*;
import cn.iecas.springboot.entity.DepartmentBean;
import cn.iecas.springboot.entity.UserBean;
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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api
@Slf4j
@Service
public class LoginService {
    @Lazy
    @Autowired
    private UserDao userDao;

    @Lazy
    @Autowired
    private UserDeptDao userDeptDao;

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
    private JwtProperties jwtProperties; //framework

    @Lazy
    @Autowired
    private SpringBootMasterProperties springBootMasterProperties;//framework

    @Lazy
    @Autowired
    private LoginRedisService loginRedisService; //framework

    @Lazy
    @Autowired
    private RedisTemplate redisTemplate; //framework

    public LoginUserTokenVo login(LoginParam loginParam){
        String username = loginParam.getUsername();
        // 从数据库中获取登录用户信息

        UserBean user = userDao.findUserBeanByUsername(username);
        if (user == null) {
            log.error("登录失败,loginParam:{}", loginParam);
            throw new AuthenticationException("用户名或密码错误");
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
        //添加昵称
        loginUserVo.setNickname(user.getName());
        //获取部门-------
        Long deptId = userDeptDao.findDidByUid(user.getId());
        if (deptId==null){
            throw new AuthenticationException("部门不存在");
        }
        DepartmentBean deptBean = departmentDao.getOne(deptId);
        loginUserVo.setDepartmentId(deptBean.getId()).setDepartmentName(deptBean.getName());
        // 获取当前用户角色------
        List<String> roleListIds = userRoleDao.findU_R_idListByUid(user.getId());
        if (CollectionUtils.isEmpty(roleListIds)){
            throw new AuthenticationException("角色列表不能为空");
        }
        loginUserVo.setRoleCodes(new HashSet<>(roleListIds));
        // 获取当前用户权限------
        List<String> permissionIds = permissionDao.findPermissionByUserId(user.getId());
        if (CollectionUtils.isEmpty(permissionIds)){
            throw new AuthenticationException("权限列表不能为空");
        }
        loginUserVo.setPermissionCodes(new HashSet<>(permissionIds));
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
            //实现Shiro的认证和授权，需要自定义Realm继承于Au thorizingRealm，
            // 同时重写doGetAuthenticationInfo（认证）和doGetAuthorizationInfo（授权）这两个方法。
            // 如果认证成功，那么在系统的任何地方通过SecurityUtils.getSubject()方法就可以获取认证通过的信息。
            // 我们也可以借助它的这点特性，实现用户的自动登录。
            //通过注解的方式作用于接口。在controller中，方法如果加了@RequiresPermissions("sys:del")注解，
            // Shiro同样会调用自定义Realm获取权限信息，看"sys:del"是否在权限数据中存在，存在则授权通过，
            // 不存在则拒绝访问，从而实现对接口的权限校验。
            //用户登录的时候，将用户的账号和密码包装成一个UsernamePasswordToken后，再调用login提交账户认证，
            // shiro会自动调用我们重写的doGetAuthenticationInfo方法。
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
    public void logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        //获取token
        String token = JwtTokenUtil.getToken(request);
        String username = JwtUtil.getUsername(token);
        //删除Redis缓存信息
        loginRedisService.deleteLoginInfo(token, username);
        log.info("登出成功,username:{},token:{}", username, token);

    }
}
