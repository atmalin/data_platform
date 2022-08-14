package cn.iecas.springboot.framework.exception;

/**
 * 业务异常
 *
 * @author ch
 * @date 2021-09-29
 */
public class BusinessException extends CustomException{
    private static final long serialVersionUID = -2303357122330162359L;

    public BusinessException(String message) {
        super(message);
    }
}
