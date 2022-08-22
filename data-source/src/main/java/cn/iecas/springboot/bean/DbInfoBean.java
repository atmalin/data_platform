package cn.iecas.springboot.bean;

import cn.iecas.springboot.framework.common.bean.BaseBean;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "db_info")
@Accessors(chain = true)
@ApiModel("数据源信息")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDef(name = "json", typeClass = JsonStringType.class)

public class DbInfoBean extends BaseBean {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 64)
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @Column(name = "user_id")
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("描述")
    private String description;
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "source_id", length = 64)
    @ApiModelProperty("来源Id")
    private String sourceId;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("连接后序参数")
    private String parameter;
    @ApiModelProperty("数据库类型")
    private String dbType;

    @Column(name = "connect_user")
    @ApiModelProperty("连接用户名")
    private String connectUser;
    @Column(name = "connect_pass")
    @ApiModelProperty("连接密码")
    private String connectPass;

    @Column(name = "sys_type")
    @ApiModelProperty("数据源分类")
    private String sysType;

//    @Column(name = "create_time")
//    @ApiModelProperty("创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @CreatedDate
//    private Date createTime;
//
//    @Column(name = "last_update_time")
//    @ApiModelProperty("修改时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @LastModifiedDate
//    private Date updateTime;
}
