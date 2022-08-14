package cn.iecas.springboot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 部门TreeVo
 * </pre>
 *
 * @author geekidea
 * @since 2019-11-1
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDepartmentTreeVo对象", description = "部门")
public class DepartmentTreeVo implements Serializable {
    private static final long serialVersionUID = -2250233632748939400L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("父id")
    private Long pid;

    @ApiModelProperty("状态，0：禁用，1：启用")
    private Integer state;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date lastUpdateTime;

    private List<DepartmentTreeVo> children;
}
