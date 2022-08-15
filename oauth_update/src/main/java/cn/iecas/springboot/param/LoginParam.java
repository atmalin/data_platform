package cn.iecas.springboot.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录参数
 * @author ch
 * @date 2021-10-15
 */
@Data
@ApiModel("登录参数")
public class LoginParam implements Serializable {

    private static final long serialVersionUID = -2502834333307502756L;

    @NotBlank(message = "请输入账号")
    @ApiModelProperty(value = "账号", example = "admin")
    private String username;

    @NotBlank(message = "请输入密码")
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;
}
