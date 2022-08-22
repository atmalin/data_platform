package cn.iecas.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("modify请求包装类")
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEntity implements Serializable {
    private static final long serialVersionUID = 3457632922896008909L;
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("数据库url")
    private String address;
    @ApiModelProperty("密码")
    private String connectPass;
    @ApiModelProperty("用户名")
    private String connectUser;
    @ApiModelProperty("数据库类型")
    private String dbType;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("数据库名称")
    private String name;
    @ApiModelProperty("数据库id")
    private String sourceId;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private Date createTime;
}
