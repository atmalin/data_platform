package cn.iecas.springboot.framework.core.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询参数
 *
 * @author ch
 * @since 2021-09-29
 */
@Data
@ApiModel("查询参数对象")
public class  SearchParam extends PageParam {

    private static final long serialVersionUID = -6032953691980184901L;

    @ApiModelProperty(value = "查询条件")
    private List<QueryParam> queryCondition;
}
