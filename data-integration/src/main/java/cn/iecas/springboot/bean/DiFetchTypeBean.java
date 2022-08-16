package cn.iecas.springboot.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dragon
 * @since 2022/8/15 13:41
 */
@Data
@Entity
@Accessors(chain = true)
@ApiModel("数据接入支持接入方式类")
@Table(name="di_fetch_type")
public class DiFetchTypeBean{

    @Id
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    @Column(length = 32)
    private String name;

    @ApiModelProperty("英文名称")
    @Column(columnDefinition = "TINYINT", length = 1)
    private boolean enable;
}
