package cn.iecas.springboot.framework.shiro.vo;

import cn.iecas.springboot.framework.common.bean.ClientInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登录用户Redis对象，后台使用
 *
 * @author ch
 * @date 2021-10-15
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class LoginUserRedisVo extends LoginUserVo {

    private static final long serialVersionUID = -4008181712004199269L;

    /**
     * 包装后的盐值
     */
    private String salt;

    /**
     * 登录ip
     */
    private ClientInfo clientInfo;
}
