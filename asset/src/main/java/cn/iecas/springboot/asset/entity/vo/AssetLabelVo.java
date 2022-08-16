package cn.iecas.springboot.asset.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author malin
 * @create 2022-08-05-9:03
 */
@Data
@ApiModel(value = "标签展示bean")
public class AssetLabelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    @ApiModelProperty(value = "标签ID")
    private String id;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String name;

    /**
     * 标签图标
     */
    @ApiModelProperty(value = "标签图标")
    private String iconUrl;
}
