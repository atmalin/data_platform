package cn.iecas.springboot.entity;

import cn.iecas.springboot.bean.DbTypeBean;
import cn.iecas.springboot.bean.SourceBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("外部包装Bean")
@AllArgsConstructor
@NoArgsConstructor
public class BigEntity implements Serializable {
    private static final long serialVersionUID = -3685681079027132561L;

    @GeneratedValue(generator = "jpa-uuid")
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("描述")
    private String description;
    @GeneratedValue(generator = "jpa-uuid")
    @ApiModelProperty("来源Id")
    private String sourceId;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("连接后序参数")
    private String parameter;
    @ApiModelProperty("数据库类型")
    private String dbType;

    @ApiModelProperty("连接用户名")
    private String connectUser;
    @ApiModelProperty("连接密码")
    private String connectPass;

    @ApiModelProperty("数据源分类")
    private String sysType;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private Date createTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private Date lastUpdateTime;

    @ApiModelProperty("Type")
    private DbTypeBean dbTypeBean;
    @ApiModelProperty("Source")
    private SourceBean sourceBean;
}
