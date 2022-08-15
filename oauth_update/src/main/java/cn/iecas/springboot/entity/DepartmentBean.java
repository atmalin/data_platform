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

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "tb_dept")
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Department 对象", description = "部门")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class DepartmentBean  {

    private static final long serialVersionUID = -559624838986847907L;

    @ApiModelProperty(value = "主键", hidden = true)
    @NotNull(message = "id 不能为空", groups = {Update.class})
    @Id
    private Long id;

    @ApiModelProperty("部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @ApiModelProperty("父id")
    private Long parent_id;

    @ApiModelProperty("客户端id")
    private Integer client_id;

}
