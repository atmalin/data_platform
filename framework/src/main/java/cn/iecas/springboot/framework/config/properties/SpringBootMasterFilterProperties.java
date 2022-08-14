package cn.iecas.springboot.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * Filter配置属性
 *
 * @author ch
 * @date 2021-10-20
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring-boot-master.filter")
public class SpringBootMasterFilterProperties {

    /**
     * RequestDetail Filter 配置
     */
    @NestedConfigurationProperty
    private FilterConfig request = new FilterConfig();

    /**
     * XSS Filter 配置
     */
    @NestedConfigurationProperty
    private FilterConfig xss = new FilterConfig();

    @Data
    public static class FilterConfig {

        /**
         * 是否启用
         */
        private boolean enable;

        /**
         * 过滤的路径
         */
        private String[] urlPatterns;

        /**
         * 排序
         */
        private int order;

        /**
         * 是否支持异步
         */
        private boolean async;

    }
}
