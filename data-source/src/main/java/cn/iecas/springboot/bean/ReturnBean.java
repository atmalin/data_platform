package cn.iecas.springboot.bean;

import cn.iecas.springboot.entity.ExampleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("封装查询结果")
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBean implements Serializable {

    private static final long serialVersionUID = 6506454976929770101L;
    @ApiModelProperty("Info")
    private DbInfoBean dbInfoBean;
    @ApiModelProperty("Type")
    private DbTypeBean dbTypeBean;
    @ApiModelProperty("Source")
    private SourceBean sourceBean;

}
