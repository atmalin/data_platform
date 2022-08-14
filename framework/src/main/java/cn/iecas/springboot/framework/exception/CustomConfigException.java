package cn.iecas.springboot.framework.exception;

import cn.iecas.springboot.framework.result.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义配置异常
 *
 * @author ch
 * @date 2021-09-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomConfigException extends CustomException {
    private static final long serialVersionUID = 8952028631871769425L;

    private String message;

    public CustomConfigException(String message) {
        super(message);
        this.message = message;
    }
}
