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
@Table(name = "department")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Department 对象", description = "部门")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class DepartmentBean extends BaseBean {

    private static final long serialVersionUID = -559624838986847907L;

    @ApiModelProperty("主键")
    @NotNull(message = "id 不能为空", groups = {Update.class})
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ApiModelProperty("部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @ApiModelProperty("父id")
    private Long pid;

    @ApiModelProperty("状态，0：禁用，1：启用")
    private Integer state;

    @ApiModelProperty("备注")
    private String remark;
}
