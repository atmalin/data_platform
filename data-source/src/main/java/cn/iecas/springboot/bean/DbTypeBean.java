package cn.iecas.springboot.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "db_type")
@Accessors(chain = true)
@ApiModel("数据源信息")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class DbTypeBean implements Serializable {
    private static final long serialVersionUID = -3272065580813510832L;
    @Id
    @ApiModelProperty("主键id")
    private String id;
    @Column(name = "source_type_id")
    @ApiModelProperty("0是jdbc，1是文本")
    private String sourceTypeId;
    @ApiModelProperty("源类型，关系型数据库还是非关系型")
    private String source;
    @ApiModelProperty("数据库类型")
    private String type;
    @Column(name = "jdbc_type")
    @ApiModelProperty("jdbc类型")
    private String jdbcType;
    @Column(name = "class_name")
    @ApiModelProperty("驱动")
    private String className;
    @Column(name = "db_url")
    @ApiModelProperty("数据库路径")
    private String dbUrl;
    @Column(name = "driver_location")
    @ApiModelProperty("驱动地址")
    private String driverLocation;
    @Column(name = "need_user")
    @ApiModelProperty("是否需要用户名")
    private Boolean needUser;
    @Column(name = "need_password")
    @ApiModelProperty("是否需要密码")
    private Boolean needPassword;
    @ApiModelProperty("状态")
    private Boolean status;
}
