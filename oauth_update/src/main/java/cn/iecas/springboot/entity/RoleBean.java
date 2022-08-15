package cn.iecas.springboot.entity;

import cn.iecas.springboot.framework.common.bean.BaseBean;
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

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "tb_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "role 实体类", description = "用户管理")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class RoleBean extends BaseBean {

    private static final long serialVersionUID = -487738234353456553L;

    @ApiModelProperty("主键")
    @NotNull(groups = Update.class, message = "主键不能为空")
    @GeneratedValue(generator = "jpa-uuid")
    @Id
    @Column(length = 64)
    private String id;

//    @ApiModelProperty("父角色")
//    private String parent_id;

    @ApiModelProperty("角色名称")
    @NotNull(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty("角色英文名称")
    @NotNull(message = "角色英文名称不能为空")
    private String en_name;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("客户端id")
    private String client_id;

    @ApiModelProperty("父角色")
    private String parent_id;

}
