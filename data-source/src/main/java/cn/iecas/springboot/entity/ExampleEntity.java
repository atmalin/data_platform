package cn.iecas.springboot.entity;

import cn.iecas.springboot.bean.SourceBean;
import cn.iecas.springboot.bean.DbInfoBean;
import cn.iecas.springboot.bean.DbTypeBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Get返回信息")
public class ExampleEntity implements Serializable {

    private static final long serialVersionUID = -1607463537890015921L;
    @ApiModelProperty("Info")
    private DbInfoBean dbInfoBean;
    @ApiModelProperty("Type")
    private DbTypeBean dbTypeBean;
    @ApiModelProperty("Source")
    private SourceBean sourceBean;

}
