package cn.iecas.springboot.framework.shiro.exception;

import cn.iecas.springboot.framework.exception.CustomException;
import cn.iecas.springboot.framework.result.ApiCode;

/**
 * Shiro配置异常
 *
 * @author ch
 * @date 2021-10-18
 */
public class ShiroConfigException extends CustomException {
    private static final long serialVersionUID = -4573955712491628431L;

    public ShiroConfigException(String message) {
        super(message);
    }

    public ShiroConfigException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public ShiroConfigException(ApiCode apiCode) {
        super(apiCode);
    }
}
