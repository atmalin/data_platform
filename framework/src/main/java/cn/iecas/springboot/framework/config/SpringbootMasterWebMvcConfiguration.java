package cn.iecas.springboot.framework.config;

import cn.iecas.springboot.framework.config.properties.SpringBootMasterFilterProperties;
import cn.iecas.springboot.framework.config.properties.SpringBootMasterProperties;
import cn.iecas.springboot.framework.config.properties.SpringBootMasterInterceptorProperties;
import cn.iecas.springboot.framework.core.filter.RequestDetailFilter;
import cn.iecas.springboot.framework.core.interceptor.DownloadInterceptor;
import cn.iecas.springboot.framework.core.interceptor.PermissionInterceptor;
import cn.iecas.springboot.framework.core.interceptor.ResourceInterceptor;
import cn.iecas.springboot.framework.core.interceptor.UploadInterceptor;
import cn.iecas.springboot.framework.core.xss.XssFilter;
import cn.iecas.springboot.framework.util.IniUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * WebMvc配置
 *
 * @author ch
 * @date 2021-10-20
 */
@Slf4j
@Configuration
public class SpringbootMasterWebMvcConfiguration implements WebMvcConfigurer {

    /**
     * spring-boot-plus 配置属性
     */
    @Autowired
    private SpringBootMasterProperties springBootMasterProperties;

    /**
     * Filter 配置
     */
    private SpringBootMasterFilterProperties filterConfig;

    /**
     * 拦截器配置
     */
    private SpringBootMasterInterceptorProperties interceptorConfig;

    /**
     * RequestDetailFilter 配置
     */
    @Bean
    public FilterRegistrationBean requestDetailFilter() {
        SpringBootMasterFilterProperties.FilterConfig requestFilterConfig = filterConfig.getRequest();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new RequestDetailFilter());
        filterRegistrationBean.setEnabled(requestFilterConfig.isEnable());
        filterRegistrationBean.setUrlPatterns(filterRegistrationBean.getUrlPatterns());
        filterRegistrationBean.setOrder(requestFilterConfig.getOrder());
        filterRegistrationBean.setAsyncSupported(requestFilterConfig.isAsync());
        return filterRegistrationBean;
    }

    /**
     * XSSFilter 配置
     */
    @Bean
    public FilterRegistrationBean xssFilter() {
        SpringBootMasterFilterProperties.FilterConfig xssFilterConfig = filterConfig.getXss();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setEnabled(xssFilterConfig.isEnable());
        filterRegistrationBean.addUrlPatterns(xssFilterConfig.getUrlPatterns());
        filterRegistrationBean.setOrder(xssFilterConfig.getOrder());
        filterRegistrationBean.setAsyncSupported(xssFilterConfig.isAsync());
        return filterRegistrationBean;
    }

    /**
     * 自定义权限拦截器
     */
    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    /**
     * 上传拦截器
     */
    @Bean
    public UploadInterceptor uploadInterceptor() {
        return new UploadInterceptor();
    }

    /**
     * 资源拦截器
     *
     * @return
     */
    @Bean
    public ResourceInterceptor resourceInterceptor() {
        return new ResourceInterceptor();
    }

    /**
     * 下载拦截器
     *
     * @return
     */
    @Bean
    public DownloadInterceptor downloadInterceptor() {
        return new DownloadInterceptor();
    }

    @PostConstruct
    public void init() {
        filterConfig = springBootMasterProperties.getFilter();
        interceptorConfig = springBootMasterProperties.getInterceptor();
        // 打印 SpringBootMasterProperties 配置信息
        log.debug("SpringBootMasterProperties:{}", JSON.toJSONString(springBootMasterProperties));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 上传拦截器注册
        if (interceptorConfig.getUpload().isEnable()) {
            registry.addInterceptor(uploadInterceptor())
                    .addPathPatterns(interceptorConfig.getUpload().getIncludePaths());
        }

        // 资源拦截器注册
        if (interceptorConfig.getResource().isEnable()) {
            registry.addInterceptor(resourceInterceptor())
                    .addPathPatterns(interceptorConfig.getResource().getIncludePaths())
                    .excludePathPatterns(interceptorConfig.getResource().getExcludePaths());
        }

        // 下载拦截器注册
        if (interceptorConfig.getDownload().isEnable()) {
            registry.addInterceptor(downloadInterceptor())
                    .addPathPatterns(interceptorConfig.getDownload().getIncludePaths());
        }

        // 自定义权限拦截器注册
        if (interceptorConfig.getPermission().isEnable()) {
            registry.addInterceptor(permissionInterceptor())
                    .addPathPatterns(interceptorConfig.getPermission().getIncludePaths())
                    .excludePathPatterns(interceptorConfig.getPermission().getExcludePaths());
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 设置项目静态资源访问
        String resourceHandlers = springBootMasterProperties.getResourceHandlers();
        if (StringUtils.isNotBlank(resourceHandlers)) {
            Map<String, String> map = IniUtil.parseIni(resourceHandlers);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String pathPatterns = entry.getKey();
                String resourceLocations = entry.getValue();
                registry.addResourceHandler(pathPatterns)
                        .addResourceLocations(resourceLocations);
            }
        }

        // 设置上传文件访问路径
        registry.addResourceHandler(springBootMasterProperties.getResourceAccessPatterns())
                .addResourceLocations("file:" + springBootMasterProperties.getUploadPath());
    }
}
