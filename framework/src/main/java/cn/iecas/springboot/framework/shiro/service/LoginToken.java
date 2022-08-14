package cn.iecas.springboot.framework.shiro.service;

import java.io.Serializable;

/**
 * 获取登录token
 *
 * @author ch
 * @date 2021-10-15
 **/
public interface LoginToken extends Serializable {

    /**
     * 获取登录token
     *
     * @return
     */
    String getToken();

}
