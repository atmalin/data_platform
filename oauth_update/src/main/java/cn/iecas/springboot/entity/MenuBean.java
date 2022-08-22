package cn.iecas.springboot.entity;

import cn.iecas.springboot.framework.common.bean.BaseBean;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.core.validator.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="tb_menu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Menu实体类", description = "菜单")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class MenuBean extends BaseBean {

    @ApiModelProperty(value = "主键", hidden = true)
    @NotNull(message = "id 不能为空", groups = {Update.class})
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 64)
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

    @Transient
    @ApiModelProperty("子菜单")
    private List<MenuBean> children;

    @Transient
    @ApiModelProperty("按钮")
    private List<ButtonBean> buttonBeans;

}
