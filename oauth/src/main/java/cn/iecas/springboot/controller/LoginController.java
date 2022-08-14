package cn.iecas.springboot.controller;

import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLogIgnore;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.shiro.util.JwtTokenUtil;
import cn.iecas.springboot.framework.shiro.vo.LoginUserVo;
import cn.iecas.springboot.param.LoginParam;
import cn.iecas.springboot.service.LoginService;
import cn.iecas.springboot.vo.LoginUserTokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 系统权限 前端控制器
 * </pre>
 *
 * @author ch
 * @since 2021-10-15
 */
@Slf4j
@RestController
@Module("system")
@Api(value = "系统登录 API", tags = {"系统登录"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    @OperationLogIgnore
    @ApiOperation("登录")
    public ApiResult<LoginUserTokenVo> login(@Validated @RequestBody LoginParam loginParam, HttpServletResponse response) {
        LoginUserTokenVo loginUserTokenVo = loginService.login(loginParam);
        // 设置 token 响应头
        response.setHeader(JwtTokenUtil.getTokenName(), loginUserTokenVo.getToken());
        return ApiResult.success(loginUserTokenVo, "登录成功");
    }

    @GetMapping("/user/info")
    @ApiOperation("根据请求获取系统登录用户信息")
    public ApiResult<LoginUserVo> getUserInfo() {
        String token =  JwtTokenUtil.getToken();
        String tokenSha256 = DigestUtils.sha256Hex(token);
        LoginUserVo loginSysUserVo = (LoginUserVo) redisTemplate.opsForValue().get(tokenSha256);
        return ApiResult.success(loginSysUserVo);
    }

    @GetMapping("/authentication")
    @ApiOperation("根据 token 获取系统登录用户信息")
    public ApiResult<LoginUserVo> authentication(String token) {
        String tokenSha256 = DigestUtils.sha256Hex(token);
        Object user = redisTemplate.opsForValue().get(tokenSha256);
        if (user != null) {
            LoginUserVo loginSysUserVo = (LoginUserVo) user;
            return ApiResult.success(loginSysUserVo);
        } else {
            return ApiResult.fail("token错误");
        }
    }

    @PostMapping("/logout")
    @OperationLogIgnore
    public ApiResult<String> logout(HttpServletRequest request) {
        loginService.logout(request);
        return ApiResult.success("退出成功");
    }
}
