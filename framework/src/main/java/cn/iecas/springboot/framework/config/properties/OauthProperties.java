package cn.iecas.springboot.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用户相关属性配置
 *
 * @author ch
 * @date 2021-09-29
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring-boot-master.oauth")
public class OauthProperties {

    /**
     * 默认初始化超级管理员用户登录名
     */
    private String initUsername;

    /**
     * 默认初始化超级管理员用户登录密码
     */
    private String initPassword;

    /**
     * 默认头像地址
     */
    private String initAvatar;

    /**
     * 默认角色 id
     */
    private Long initRoleId;
}
