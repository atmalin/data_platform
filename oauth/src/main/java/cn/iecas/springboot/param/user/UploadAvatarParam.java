package cn.iecas.springboot.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 上传头像参数
 *
 * @author ch
 * @date 2021-10-13
 **/
@Data
@Accessors(chain = true)
@ApiModel("上传头像参数")
public class UploadAvatarParam implements Serializable {

    private static final long serialVersionUID = -6871175837435010592L;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @ApiModelProperty("头像路径")
    @NotBlank(message = "头像不能为空")
    private String Avatar;

}
