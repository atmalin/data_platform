package cn.iecas.springboot.asset.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

import java.io.Serializable;
import java.util.List;

/**
 * @author malin
 * @create 2022-08-04-17:11
 */
@Data
@ApiModel(value = "标签集展示bean")
//@TypeDef(name = "json", typeClass = JsonStringType.class)
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class AssetLabelSetVo implements Serializable {

    @ApiModelProperty("id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty("status")
    private String status;

    /**
     * 序号
     */
    @ApiModelProperty("hot")
    private Integer hot;

    /**
     * 创建时间
     */

//    @Column(name = "create_time")
//    private String createTime;

    /**
     * 创建人
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * 删除时间
     */
//    @Column(name = "update_time")
//    private String updateTime;

    /**
     * 删除人
     */
    @Column(name = "update_man")
    private String updateMan;

    private List<AssetLabelVo> labelList;
}
