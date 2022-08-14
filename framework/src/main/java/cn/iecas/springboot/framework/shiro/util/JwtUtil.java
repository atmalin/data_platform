package cn.iecas.springboot.framework.shiro.util;

import cn.iecas.springboot.framework.common.constant.CommonConstant;
import cn.iecas.springboot.framework.config.properties.JwtProperties;
import cn.iecas.springboot.framework.util.UUIDUtil;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

/**
 * JWT工具类
 * https://github.com/auth0/java-jwt
 *
 * @author ch
 * @date 2021-10-15
 */
@Slf4j
@Component
public class JwtUtil {

    private static JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        JwtUtil.jwtProperties = jwtProperties;
        log.info(JSON.toJSONString(JwtUtil.jwtProperties));
    }

    /**
     * 生成 jwt token
     * @param username 用户名
     * @param salt 盐值
     * @param expireDuration 过期时间
     * @return token
     */
    public static String generateToken(String username, String salt, Duration expireDuration) {
        try {
            if (StringUtils.isBlank(username)) {
                log.error("username 不能为空");
                return null;
            }
            log.debug("username:{}", username);

            // 如果盐值为空，则使用默认值：iecas
            if (StringUtils.isBlank(salt)) {
                salt = jwtProperties.getSecret();
            }
            log.debug("salt:{}", salt);

            // 过期时间，单位：秒
            Long expireSecond;
            // 默认过期时间为 1 小时
            if (expireDuration == null) {
                expireSecond = jwtProperties.getExpireSecond();
            } else {
                expireSecond = expireDuration.getSeconds();
            }
            log.debug("expireSecond:{}", expireSecond);
            Date expireDate = DateUtils.addSeconds(new Date(), expireSecond.intValue());
            log.debug("expireDate:{}", expireDate);

            // 生成 token
            Algorithm algorithm = Algorithm.HMAC256(salt);
            return JWT.create()
                    .withClaim(CommonConstant.JWT_USERNAME, username)
                    // jwt 唯一 id
                    .withJWTId(UUIDUtil.getUuid())
                    // 签发人
                    .withIssuer(jwtProperties.getIssuer())
                    // 主题
                    .withSubject(jwtProperties.getSubject())
                    // 签发的目标
                    .withAudience(jwtProperties.getAudience())
                    // 签发时间
                    .withIssuedAt(new Date())
                    // 过期时间
                    .withExpiresAt(expireDate)
                    // 签名
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("generateToken exception", e);
        }
        return null;
    }

    /**
     * 解析token，获取token数据
     *
     * @param token
     * @return
     */
    public static DecodedJWT getJwtInfo(String token) {
        return JWT.decode(token);
    }

    public static String getUsername(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        DecodedJWT decodedJwt = getJwtInfo(token);
        if (decodedJwt == null) {
            return null;
        }
        String username = decodedJwt.getClaim(CommonConstant.JWT_USERNAME).asString();
        return username;
    }

    /**
     * 获取过期时间
     *
     * @param token
     * @return
     */
    public static Date getExpireDate(String token) {
        DecodedJWT decodedJWT = getJwtInfo(token);
        return decodedJWT.getExpiresAt();
    }

    /**
     * 判断token是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpired(String token) {
        Date expireDate = getExpireDate(token);
        if (expireDate == null) {
            return true;
        }
        return expireDate.before(new Date());
    }

    public static boolean verifyToken(String token, String salt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtProperties.getIssuer())
                    .withSubject(jwtProperties.getSubject())
                    .withAudience(jwtProperties.getAudience())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt.getToken() != null) {
                return true;
            }
            return true;
        } catch (Exception e) {
            log.error("Verify Token Exception", e);
        }
        return false;
    }
}
