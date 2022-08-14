package cn.iecas.springboot.framework.core.pagination;

import cn.iecas.springboot.framework.common.constant.CommonConstant;
import cn.iecas.springboot.framework.core.pagination.enums.SortStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 *
 * @author ch
 * @since 2021-11-2
 */
@Data
@ApiModel("列表参数")
public class PageParam implements Serializable {

    private static final long serialVersionUID = 6664893804354562002L;

    @ApiModelProperty(value = "排序字段", example = "create_time")
    private String sortedField = CommonConstant.SORTED_FIELD;

    @ApiModelProperty(value = "排序类型", example = "DESC")
    private SortStateEnum sortedType = CommonConstant.SORTED_TYPE;

    @ApiModelProperty(value = "当前页面", example = "1")
    private Integer curPage = CommonConstant.DEFAULT_CUR_PAGE;

    @ApiModelProperty(value = "页面大小", example = "100")
    private Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
}
