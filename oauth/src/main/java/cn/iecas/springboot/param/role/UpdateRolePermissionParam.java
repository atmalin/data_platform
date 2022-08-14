package cn.iecas.springboot.param.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author ch
 * @date 2021-10-14
 **/
@Data
@ApiModel("修改系统角色权限参数")
public class UpdateRolePermissionParam implements Serializable {

    private static final long serialVersionUID = -672108684986772098L;

    @ApiModelProperty("角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @ApiModelProperty("权限ID集合")
    private List<Long> permissionIds;
}
