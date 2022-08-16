package cn.iecas.springboot.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel("修改页面返回的字段")
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBean implements Serializable {
    private static final long serialVersionUID = -509298889257616934L;
    @ApiModelProperty("数据库地址")
    private String address;
    @ApiModelProperty("用户名")
    private String connectUser;
    @ApiModelProperty("密码")
    private String connectPass;
    @ApiModelProperty("数据源名称")
    private String name;
    @ApiModelProperty("数据来源")
    private String sysType;
    @ApiModelProperty("描述")
    private String description;
}