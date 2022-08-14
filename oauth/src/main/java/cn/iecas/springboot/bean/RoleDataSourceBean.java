package cn.iecas.springboot.bean;

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

/**
 * <pre>
 * 角色权限映射关系
 * </pre>
 *
 * @author ch
 * @since 2021-09-26
 */
@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="role_datasource")
@ApiModel(value = "角色数据权限类", description = "角色与数据源的映射关系")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class RoleDataSourceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @NotNull(groups = Update.class, message = "id 不能为空")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ApiModelProperty("角色 id")
    @NotNull(message = "角色 id 不能为空")
    private Long roleId;

    @ApiModelProperty("数据源 id")
    @NotNull(message = "数据源 id 不能为空")
    private Long datasourceId;
}
