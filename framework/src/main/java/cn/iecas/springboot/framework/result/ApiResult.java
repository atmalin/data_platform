package cn.iecas.springboot.framework.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * <p>
 * REST API 返回结果
 * </p>
 *
 * @author geekidea
 * @since 2018-11-08
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("返回参数")
public class ApiResult<T> implements Serializable {
	private static final long serialVersionUID = 8004487252556526569L;

	/**
     * 响应码
     */
    @ApiModelProperty("响应码")
    private int code;

    /**
     * 是否成功
     */
    @ApiModelProperty("是否成功")
    private String status;

    /**
     * 响应消息
     */
    @ApiModelProperty("响应消息")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    public static <T> ApiResult<T> result(ApiCode apiCode, T data, String message){
        String status = "failed";
        if (apiCode.getCode() == ApiCode.SUCCESS.getCode()){
            status = "success";
        }
        String apiMessage = apiCode.getMessage();
        if (StringUtils.isBlank(message)){
            message = apiMessage;
        }
        return (ApiResult<T>) ApiResult.builder()
                .code(apiCode.getCode())
                .message(message)
                .data(data)
                .status(status)
                .build();
    }

    public static <T> ApiResult<T> success(T data, String message) {
        return result(ApiCode.SUCCESS, data, message);
    }

    public static <T> ApiResult<T> success(T data) {
        return success(data, null);
    }

    public static ApiResult<Boolean> success() {
        return success(null);
    }

    public static <T> ApiResult<T> fail(String message) {
        return result(ApiCode.FAIL, null, message);
    }

    public static  ApiResult<String> fail(Integer errorCode,String message){
        return new ApiResult<String>()
                .setStatus("failed")
                .setCode(errorCode)
                .setMessage(message);
    }

    public static  ApiResult<String> fail(ApiCode errorCode){
        return new ApiResult<String>()
                .setStatus("failed")
                .setCode(errorCode.getCode());
    }
}