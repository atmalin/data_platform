package cn.iecas.springboot.framework.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("校验名称重复参数")
public class CheckNameParam {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;
}
