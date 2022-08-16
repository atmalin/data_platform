package cn.iecas.springboot.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dragon
 * @since 2022/8/15 14:32
 */
@Data
public class DataSourceColumnsEntity implements Serializable {

    @ApiModelProperty("字段名")
    private String name;

    @ApiModelProperty("字段类型")
    private String type;

    @ApiModelProperty("字段序号")
    private Integer seqNumber;
}
