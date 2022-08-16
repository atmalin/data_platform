package cn.iecas.springboot.asset.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author malin
 * @create 2022-08-10-14:39
 */
@Data
@ApiModel(value = "标签集绑定展示bean")
public class AssetLabelRelVo implements Serializable {

    /**
     * 标签集id
     */
    @ApiModelProperty("标签集id")
    private String labelSetId;

    /**
     * 标签id
     */
    @ApiModelProperty("标签的id集合")
    private List<String> labelList;
}
