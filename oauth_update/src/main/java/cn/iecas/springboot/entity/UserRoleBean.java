package cn.iecas.springboot.entity;

import cn.iecas.springboot.framework.core.validator.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "tb_user_role")
@ApiModel(value = "用户角色映射类", description = "用户 id 与角色 id 进行映射")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class UserRoleBean implements Serializable {

    private static final long serialVersionUID =  -97396021233683470L;

    @Id
    @ApiModelProperty("主键")
    @NotNull(groups = Update.class, message = "id 不能为空")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("用户 id")
    @NotNull(message = "用户 id 不能为空")
    private String userId;

    @ApiModelProperty("角色 id")
    @NotNull(message = "角色 id 不能为空")
    private String roleId;

}
