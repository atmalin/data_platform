package cn.iecas.springboot.entity.dataBean;

import cn.iecas.springboot.entity.ButtonBean;
import cn.iecas.springboot.entity.MenuBean;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "Menu添加参数", description = "菜单")
@Accessors(chain = true)
public class MenuVo implements Serializable {

    private static final long serialVersionUID = -2502834333307502756L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("父id")
    private String parent_id;

    @NotNull(message = "名称不能为空", groups = {Add.class})
    @ApiModelProperty("名称")
    private String name;

    @NotNull(message = "英文名称不能为空", groups = {Add.class})
    @ApiModelProperty("英文名称")
    private String en_name;

    @ApiModelProperty("授权路径")
    private String url;

    @ApiModelProperty("备注")
    private String description;

    @ApiModelProperty("客户端id")
    @NotNull(message = "客户端id不能为空", groups = {Add.class})
    private String client_id;

    @ApiModelProperty("授权路由")
    @NotNull(message = "授权路由不能为空", groups = {Add.class})
    private String route_url;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("子菜单")
    private List<MenuBean> children;

    @ApiModelProperty("按钮")
    private List<ButtonBean> buttonBeans;
}
