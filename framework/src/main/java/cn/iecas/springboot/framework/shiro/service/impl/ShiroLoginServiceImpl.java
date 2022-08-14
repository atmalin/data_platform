package cn.iecas.springboot.framework.shiro.service.impl;

import cn.iecas.springboot.framework.common.constant.CommonConstant;
import cn.iecas.springboot.framework.config.properties.JwtProperties;
import cn.iecas.springboot.framework.shiro.cache.LoginRedisService;
import cn.iecas.springboot.framework.shiro.jwt.JwtToken;
import cn.iecas.springboot.framework.shiro.service.ShiroLoginService;
import cn.iecas.springboot.framework.shiro.util.JwtTokenUtil;
import cn.iecas.springboot.framework.shiro.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Date;

/**
 * Shiro登录服务
 *
 * @author geekidea
 * @date 2020/3/24
 **/
@Slf4j
@Service
public class ShiroLoginServiceImpl implements ShiroLoginService {

    @Lazy
    @Autowired
    private LoginRedisService loginRedisService;

    @Lazy
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void refreshToken(JwtToken jwtToken, HttpServletResponse httpServletResponse){
        if (jwtToken == null) {
            return;
        }
        String token = jwtToken.getToken();
        if (StringUtils.isBlank(token)) {
            return;
        }
        // 获取过期时间
        Date expireDate = JwtUtil.getExpireDate(token);
        // 获取倒计时
        Integer countdown = jwtProperties.getRefreshTokenCountdown();
        // 如果(当前时间+倒计时) > 过期时间，则刷新token
        boolean refreshFlag = DateUtils.addSeconds(new Date(), countdown).after(expireDate);
        if (!refreshFlag) {
            return;
        }

        // 如果token继续发往后台，则提示，此token已失效，请使用新token，不在返回新token，返回状态码：461
        // 如果Redis缓存中没有，JwtToken没有过期，则说明，已经刷新token
        boolean exists = loginRedisService.exists(token);
        if (!exists) {
            httpServletResponse.setStatus(CommonConstant.JWT_INVALID_TOKEN_CODE);
            throw new ArithmeticException("token 已失效，请使用已刷新的 token");
        }
        String username = jwtToken.getUsername();
        String salt = jwtToken.getSalt();
        Long expireSecond = jwtProperties.getExpireSecond();
        // 生成新 token 字符串
        String newToken = JwtUtil.generateToken(username, salt, Duration.ofSeconds(expireSecond));
        // 生成新 JwtToken 对象
        JwtToken newJwtToken = JwtToken.build(token, username, salt, expireSecond);
        // 更新 redis 缓存
        loginRedisService.refreshLoginInfo(token, username ,newJwtToken);
        log.debug("刷新token成功，原token:{}，新token:{}", token, newToken);
        // 设置响应头
        // 刷新 token
        httpServletResponse.setStatus(CommonConstant.JWT_REFRESH_TOKEN_CODE);
        httpServletResponse.setHeader(JwtTokenUtil.getTokenName(), newToken);
    }
}
