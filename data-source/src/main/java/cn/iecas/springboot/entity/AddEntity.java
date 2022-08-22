package cn.iecas.springboot.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Data
@ApiModel("add请求包装类")
@AllArgsConstructor
@NoArgsConstructor
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class AddEntity implements Serializable {
    private static final long serialVersionUID = -3383974777151488508L;
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
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "source_id", length = 64)
    @ApiModelProperty("数据库id")
    private String sourceId;
}
