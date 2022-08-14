package cn.iecas.springboot.framework.core.filter;

import cn.iecas.springboot.framework.core.bean.RequestDetail;
import cn.iecas.springboot.framework.core.util.RequestDetailThreadLocal;
import cn.iecas.springboot.framework.util.IpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求详情信息 Filter
 *
 * @author ch
 * @date 2021-10-20
 **/
@Slf4j
public class RequestDetailFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("RequestDetailFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置请求详情信息
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 请求IP
        String ip = IpUtil.getRequestIp(httpServletRequest);
        // 请求路径
        String path = httpServletRequest.getRequestURI();
        RequestDetail requestDetail = new RequestDetail()
                .setIp(ip)
                .setPath(path);
        // 设置请求详情信息
        RequestDetailThreadLocal.setRequestDetail(requestDetail);
        chain.doFilter(request, response);
        // 释放
        RequestDetailThreadLocal.remove();
    }

    @Override
    public void destroy() {
        log.info("RequestDetailFilter destroy");
    }
}
