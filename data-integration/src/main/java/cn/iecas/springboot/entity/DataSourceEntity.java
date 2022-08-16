package cn.iecas.springboot.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dragon
 * @since 2022/8/15 14:20
 */
@Data
public class DataSourceEntity implements Serializable {

    @ApiModelProperty("数据库名")
    private String database;

    @ApiModelProperty("数据源类型")
    private String dataSourceType;

    @ApiModelProperty("数据源id")
    private String dataSourceId;

    @ApiModelProperty("数据表名")
    private String tableName;

    @ApiModelProperty("数据表字段信息")
    private List<DataSourceColumnsEntity> columns;
}
