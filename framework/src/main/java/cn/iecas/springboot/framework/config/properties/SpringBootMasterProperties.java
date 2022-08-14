package cn.iecas.springboot.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * spring-boot-master属性配置信息
 *
 * @author ch
 * @date 2021-10-13
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring-boot-master")
public class SpringBootMasterProperties {
    /**
     * 项目IP或域名地址
     */
    private String serverIp;

    /**
     * 上传目录
     */
    private String uploadPath;
    /**
     * 资源访问路径，前端访问
     */
    private String resourceAccessPath;
    /**
     * 资源访问路径，后段配置，资源映射/拦截器使用
     */
    private String resourceAccessPatterns;
    /**
     * 资源访问全路径
     */
    private String resourceAccessUrl;

    /**
     * 允许上传的文件后缀集合
     */
    private List<String> allowUploadFileExtensions;
    /**
     * 允许下载的文件后缀集合
     */
    private List<String> allowDownloadFileExtensions;

    /**
     * JWT配置
     */
    @NestedConfigurationProperty
    private JwtProperties jwt;

    /**
     * Shiro配置
     */
    @NestedConfigurationProperty
    private ShiroProperties shiro = new ShiroProperties();

    /**
     * 拦截器配置
     */
    @NestedConfigurationProperty
    private SpringBootMasterInterceptorProperties interceptor;

    /**
     * 过滤器配置
     */
    @NestedConfigurationProperty
    private SpringBootMasterFilterProperties filter;

    /**
     * 项目静态资源访问配置
     */
    private String resourceHandlers;
}
