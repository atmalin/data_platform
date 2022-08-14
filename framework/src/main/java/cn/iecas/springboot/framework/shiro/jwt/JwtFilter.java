package cn.iecas.springboot.framework.shiro.jwt;

import cn.iecas.springboot.framework.config.properties.JwtProperties;
import cn.iecas.springboot.framework.result.ApiCode;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.shiro.cache.LoginRedisService;
import cn.iecas.springboot.framework.shiro.service.ShiroLoginService;
import cn.iecas.springboot.framework.shiro.util.JwtTokenUtil;
import cn.iecas.springboot.framework.shiro.util.JwtUtil;
import cn.iecas.springboot.framework.util.HttpServletResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Shiro JWT 授权过滤器
 *
 * @author ch
 * @date 2021-10-15
 **/
@Slf4j
@AllArgsConstructor
public class JwtFilter extends AuthenticatingFilter {

    private ShiroLoginService shiroLoginService;

    private LoginRedisService loginRedisService;

    private JwtProperties jwtProperties;

    /**
     * 将JWT Token包装成AuthenticationToken
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String token = JwtTokenUtil.getToken();
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException("token 不能为空");
        }
        if (JwtUtil.isExpired(token)) {
            throw new AuthenticationException("JWT TOKEN已过期，token：" + token);
        }

        // 先在 redis 中判断 token 是否存在
        boolean redisExpired = loginRedisService.exists(token);
        if (!redisExpired) {
            throw new AuthenticationException("Redis Token 不存在, token:" + token);
        }

        String username = JwtUtil.getUsername(token);
        String salt;
        if (jwtProperties.isSaltCheck()) {
            salt = loginRedisService.getSalt(username);
        } else {
            salt = jwtProperties.getSecret();
        }
        return JwtToken.build(token, username, salt, jwtProperties.getExpireSecond());
    }

    /**
     * 访问失败处理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        // 返回 401
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应码为 401 或者直接输出信息
        String url = httpServletRequest.getRequestURI();
        log.error("onAccessDenied url：{}", url);
        ApiResult<String> apiResult = ApiResult.fail(ApiCode.UNAUTHORIZED);
        HttpServletResponseUtil.printJson(httpServletResponse, apiResult);
        return false;
    }

    /**
     * 判断是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String url = WebUtils.toHttp(request).getRequestURI();
        log.debug("isAccessAllowed url:{}", url);
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) {
            log.error("token 不能为空", e);
        } catch (Exception e) {
            log.error("访问错误", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 登陆成功处理
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        String url = WebUtils.toHttp(request).getRequestURI();
        log.debug("鉴权成功,token:{},url:{}", token, url);
        // 刷新 token
        JwtToken jwtToken = (JwtToken) token;
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        shiroLoginService.refreshToken(jwtToken, httpServletResponse);
        return true;
    }

    /**
     * 登录失败处理
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("登录失败，token:" + token + ",error:" + e.getMessage(), e);
        return false;
    }
}
