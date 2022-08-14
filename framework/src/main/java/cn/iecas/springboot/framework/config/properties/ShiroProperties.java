package cn.iecas.springboot.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * Shiro配置映射类
 *
 * @author ch
 * @date 2021-10-18
 **/
@Data
@ConfigurationProperties(prefix = "spring-boot-plus.shiro")
public class ShiroProperties {

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 路径权限配置
     */
    private String filterChainDefinitions;

    /**
     * 设置无需权限路径集合
     */
    private List<String[]> anon;

    /**
     * 权限配置集合
     */
    @NestedConfigurationProperty
    private List<ShiroPermissionProperties> permission;
}
