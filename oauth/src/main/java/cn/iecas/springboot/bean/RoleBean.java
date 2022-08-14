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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "role 实体类", description = "系统角色")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class RoleBean extends BaseBean {

    private static final long serialVersionUID = -487738234353456553L;

    @ApiModelProperty("主键")
    @NotNull(groups = Update.class, message = "id 不能为空")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty("角色唯一编码")
    private String code;

    @ApiModelProperty("角色类型，0：功能角色， 1：数据角色")
    private Integer type;

    @ApiModelProperty("角色状态，0：禁用，1：启用")
    private Integer state;

    @ApiModelProperty("备注")
    private String remark;
}
