package cn.iecas.springboot.entity.dataBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 用户添加参数
 */
@Data
@ApiModel("用户添加参数")
public class UserAdd implements Serializable {

    private static final long serialVersionUID = -2502834333307502756L;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @NotBlank
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "昵称")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "部门id")
    private String deptId;

    @NotEmpty
    @ApiModelProperty(value = "角色列表")
    private List<String> roleList;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
