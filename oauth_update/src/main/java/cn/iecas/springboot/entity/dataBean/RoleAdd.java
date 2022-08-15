package cn.iecas.springboot.entity.dataBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色添加参数
 */
@Data
@ApiModel("角色添加参数")
public class RoleAdd implements Serializable {

    private static final long serialVersionUID = -2502834333307502756L;

    @ApiModelProperty(value = "角色Id")
    private String RoleId;

    @ApiModelProperty(value = "角色名称")
    private String RoleName;

    @ApiModelProperty(value = "角色英文名称")
    private String RoleEnName;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "角色菜单权限")
    private List<List<String>> roleMenu;

}
