package cn.iecas.springboot.bean;

import cn.iecas.springboot.framework.common.bean.BaseBean;
import cn.iecas.springboot.framework.core.validator.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 系统权限
 * </pre>
 *
 * @author ch
 * @since 2021-09-26
 */
@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="permission")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "权限实体类", description = "系统权限")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class PermissionBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @NotNull(groups = Update.class, message = "id 不能为空")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限编码")
    private String code;

    @ApiModelProperty("父id")
    private Long pid;

    @ApiModelProperty("路径")
    private String url;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("类型，1：菜单，2：按钮")
    @NotNull(message = "类型，1：菜单，2：按钮 不能为空")
    private Integer type;

    @ApiModelProperty("层级，1：第一级，2：第二级，N：第N级")
    @NotNull(message = "层级，1：第一级，2：第二级，N：第N级不能为空")
    private Integer level;

    @ApiModelProperty("状态，0：禁用，1：启用")
    private Integer state;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;
}
