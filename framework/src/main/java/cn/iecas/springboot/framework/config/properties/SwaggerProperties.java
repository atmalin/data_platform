package cn.iecas.springboot.framework.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Swagger配置属性
 *
 * @author ch
 * @date 2021-09-29
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring-boot-master.swagger")
public class SwaggerProperties {

    /**
     * 是否启用Swagger
     */
    private boolean enable;

    /**
     * 扫描的基本包
     */
    @Value("${spring-boot-master.swagger.base.package}")
    private String basePackage;

    /**
     * 联系人名称
     */
    @Value("${spring-boot-master.swagger.contact.name}")
    private String contactName;

    /**
     * 描述
     */
    private String description;

    /**
     * 标题
     */
    private String title;

    /**
     * 版本
     */
    private String version;

    /**
     * 自定义参数配置
     */
    @NestedConfigurationProperty
    private List<ParameterConfig> parameterConfig;

    /**
     * 自定义参数配置
     */
    @Data
    public static class ParameterConfig {

        /**
         * 名称
         */
        private String name = "token";

        /**
         * 描述
         */
        private String description;

        /**
         * 参数类型
         * header, cookie, body, query
         */
        private String type = "head";

        /**
         * 数据类型
         */
        private String dataType = "String";

        /**
         * 是否必填
         */
        private boolean required;

        /**
         * 默认值
         */
        private String defaultValue;

    }
}
