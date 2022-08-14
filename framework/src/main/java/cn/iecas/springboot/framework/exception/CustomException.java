package cn.iecas.springboot.framework.exception;

import cn.iecas.springboot.framework.result.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author ch
 * @date 2021-09-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = -2470461654663264392L;

    private Integer errorCode;
    private String message;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public CustomException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.errorCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
