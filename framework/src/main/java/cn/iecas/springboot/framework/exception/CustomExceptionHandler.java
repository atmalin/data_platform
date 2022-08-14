package cn.iecas.springboot.framework.exception;

import cn.iecas.springboot.framework.core.bean.RequestDetail;
import cn.iecas.springboot.framework.core.util.RequestDetailThreadLocal;
import cn.iecas.springboot.framework.result.ApiCode;
import cn.iecas.springboot.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理类
 *
 * @author ch
 * @date 2021-09-29
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    /**
     * 默认的异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<String> handleException(Exception e){
        printRequestDetail();
        printApiCodeException(ApiCode.SYSTEM_EXCEPTION, e);
        return ApiResult.fail(e.getMessage());
    }

    private void printRequestDetail() {
        RequestDetail requestDetail = RequestDetailThreadLocal.getRequestDetail();
        if (requestDetail != null) {
            log.error("异常来源：ip: {}, path: {}", requestDetail.getIp(), requestDetail.getPath());
        }
    }

    /**
     * 打印错误码及异常
     *
     * @param apiCode
     * @param exception
     */
    private void printApiCodeException(ApiCode apiCode, Exception exception) {
        log.error(getApiCodeString(apiCode), exception);
    }

    /**
     * 获取ApiCode格式化字符串
     *
     * @param apiCode
     * @return
     */
    private String getApiCodeString(ApiCode apiCode) {
        if (apiCode != null) {
            return String.format("errorCode: %s, errorMessage: %s", apiCode.getCode(), apiCode.getMessage());
        }
        return null;
    }
}
