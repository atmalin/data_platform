package cn.iecas.springboot.entity;

import cn.iecas.springboot.framework.core.validator.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Accessors(chain = true)
@Table(name="tb_role_menu")
@ApiModel(value = "角色权限映射类", description = "角色权限映射关系")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class RolePermissionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @NotNull(groups = Update.class, message = "id 不能为空")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("角色 id")
    @NotNull(message = "角色 id 不能为空")
    private String roleId;

    @ApiModelProperty("权限 id")
    @NotNull(message = "权限 id 不能为空")
    private String menuId;

    @ApiModelProperty("按钮 id")
    private String button_id;
}
