package cn.iecas.springboot.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * spring-boot-plus 拦截器配置属性
 *
 * @author ch
 * @date 2021-10-20
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring-boot-master.interceptor")
public class SpringBootMasterInterceptorProperties {

    /**
     * 自定义权限拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig permission = new InterceptorConfig();

    /**
     * 资源拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig resource = new InterceptorConfig();

    /**
     * 上传拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig upload = new InterceptorConfig();

    /**
     * 下载拦截器
     */
    @NestedConfigurationProperty
    private InterceptorConfig download = new InterceptorConfig();

    @Data
    public static class InterceptorConfig {

        /**
         * 是否启用
         */
        private boolean enable;

        /**
         * 包含的路径
         */
        private String[] includePaths = new String[]{};

        /**
         * 排除路径
         */
        private String[] excludePaths = new String[]{};
    }
}
