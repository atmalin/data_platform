package cn.iecas.springboot.framework.shiro.cache;

import cn.iecas.springboot.framework.common.bean.ClientInfo;
import cn.iecas.springboot.framework.common.constant.CommonRedisKey;
import cn.iecas.springboot.framework.config.properties.JwtProperties;
import cn.iecas.springboot.framework.shiro.convert.LoginUserVoConvert;
import cn.iecas.springboot.framework.shiro.convert.ShiroMapstructConvert;
import cn.iecas.springboot.framework.shiro.jwt.JwtToken;
import cn.iecas.springboot.framework.shiro.vo.JwtTokenRedisVo;
import cn.iecas.springboot.framework.shiro.vo.LoginUserRedisVo;
import cn.iecas.springboot.framework.shiro.vo.LoginUserVo;
import cn.iecas.springboot.framework.util.ClientInfoUtil;
import cn.iecas.springboot.framework.util.HttpServletRequestUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * 登录信息 Redis 缓存服务类
 *
 * @author ch
 * @date 2021-10-15
 */
@Service
public class LoginRedisService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * key-value: 有过期时间-->token过期时间
     * 1. tokenMd5:jwtTokenRedisVo
     * 2. username:loginSysUserRedisVo
     * 3. username:salt
     * hash: 没有过期时间，统计在线的用户信息
     * username:num
     */
    public void cacheLoginInfo(JwtToken jwtToken, LoginUserVo loginUserVo) {
        if (jwtToken == null) {
            throw new IllegalArgumentException("jwtToken 不能为空");
        }
        if (loginUserVo == null) {
            throw new IllegalArgumentException("loginUserVo 不能为空");
        }
        // token
        String token = jwtToken.getToken();
        // 盐值
        String salt = jwtToken.getSalt();
        // 登录用户名称
        String username = loginUserVo.getUsername();
        // token md5值
        String tokenMd5 = DigestUtils.md5Hex(token);

        // Redis 缓存 JWT Token 信息
        JwtTokenRedisVo jwtTokenRedisVo = ShiroMapstructConvert.INSTANCE.jwtTokenToJwtTokenRedisVo(jwtToken);

        // 用户客户端信息
        ClientInfo clientInfo = ClientInfoUtil.get(HttpServletRequestUtil.getRequest());

        // Redis 缓存登录用户信息
        // 将 LoginUserVo 对象复制到 LoginUserRedisVo，使用 mapstruct 进行对象属性复制
        LoginUserRedisVo loginUserRedisVo = LoginUserVoConvert.INSTANCE.voToRedisVo(loginUserVo)
                .setSalt(salt)
                .setClientInfo(clientInfo);

        // Redis 过期时间与 jwtToken 过期时间一致
        Duration expireDuration = Duration.ofSeconds(jwtToken.getExpireSecond());

        // 单个用户登录，每个用户只有一个有效 token
        deleteUserAllCache(username);

        // 1. tokenMd5:jwtTokenRedisVo
        String loginTokenRedisKey = String.format(CommonRedisKey.LOGIN_TOKEN, tokenMd5);
        redisTemplate.opsForValue().set(loginTokenRedisKey, jwtTokenRedisVo, expireDuration);
        // 2. username:loginSysUserRedisVo
        redisTemplate.opsForValue().set(String.format(CommonRedisKey.LOGIN_USER, username), loginUserRedisVo, expireDuration);
        // 3. salt hash,方便获取盐值鉴权
        redisTemplate.opsForValue().set(String.format(CommonRedisKey.LOGIN_SALT, username), salt, expireDuration);
        // 4. login user token
        redisTemplate.opsForValue().set(String.format(CommonRedisKey.LOGIN_USER_TOKEN, username, tokenMd5), loginTokenRedisKey, expireDuration);
    }

    public void deleteUserAllCache(String username) {
        Set<String> userTokenMd5Set = redisTemplate.keys(String.format(CommonRedisKey.LOGIN_USER_ALL_TOKEN, username));
        if (CollectionUtils.isEmpty(userTokenMd5Set)) {
            return;
        }

        // 1. 删除登录用户的所有token信息
        List<String> tokenMd5List = redisTemplate.opsForValue().multiGet(userTokenMd5Set);
        redisTemplate.delete(tokenMd5List);
        // 2. 删除登录用户的所有user:token信息
        redisTemplate.delete(userTokenMd5Set);
        // 3. 删除登录用户信息
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_USER, username));
        // 4. 删除登录用户盐值信息
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_SALT, username));
    }

    public void deleteLoginInfo(String token, String username) {
        if (token == null) {
            throw new IllegalArgumentException("token不能为空");
        }
        if (username == null) {
            throw new IllegalArgumentException("username不能为空");
        }
        String tokenMd5 = DigestUtils.md5Hex(token);
        // 1. delete tokenMd5
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_TOKEN, tokenMd5));
        // 2. delete username
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_USER, username));
        // 3. delete salt
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_SALT, username));
        // 4. delete user token
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_USER_TOKEN, username, tokenMd5));
    }

    public boolean exists(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token 不能为空");
        }
        String tokenMd5 = DigestUtils.md5Hex(token);
        Object object = redisTemplate.opsForValue().get(String.format(CommonRedisKey.LOGIN_TOKEN, tokenMd5));
        return object != null;
    }

    public void refreshLoginInfo(String oldToken, String username, JwtToken newJwtToken) {
        // 获取缓存的登录用户信息
        LoginUserRedisVo loginUserRedisVo = getLoginUserRedisVo(username);
        // 删除之前的 token 信息
        deleteLoginInfo(oldToken, username);
        // 缓存登录信息
        cacheLoginInfo(newJwtToken, loginUserRedisVo);
    }

    public LoginUserRedisVo getLoginUserRedisVo(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username 不能为空");
        }
        return (LoginUserRedisVo) redisTemplate.opsForValue().get(String.format(CommonRedisKey.LOGIN_USER, username));
    }

    public String getSalt(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username 不能为空");
        }
        return (String) redisTemplate.opsForValue().get(String.format(CommonRedisKey.LOGIN_SALT, username));
    }
}
