package cn.iecas.springboot.framework.core.pagination;


import cn.iecas.springboot.framework.core.pagination.enums.MatchTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 查询字段参数
 *
 * @author ch
 * @date 2021-09-29
 **/
@Data
@Accessors(chain = true)
@ApiModel("查询条件")
public class QueryParam {
    @ApiModelProperty("查询字段")
    private String key;

    @ApiModelProperty("查询字段值")
    private Object value;

    @ApiModelProperty("匹配方式")
    private MatchTypeEnum matchType;
}
