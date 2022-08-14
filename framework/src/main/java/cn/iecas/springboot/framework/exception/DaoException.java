package cn.iecas.springboot.framework.exception;


import cn.iecas.springboot.framework.result.ApiCode;

/**
 * DAO异常
 *
 * @author ch
 * @date 2021-10-14
 */
public class DaoException extends CustomException {
	private static final long serialVersionUID = -6912618737345878854L;

	public DaoException(String message) {
        super(message);
    }

    public DaoException(Integer errorCode, String message) {
        super(errorCode, message);
    }

    public DaoException(ApiCode apiCode) {
        super(apiCode);
    }
}
