package cn.iecas.springboot.bean;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "source")
@Accessors(chain = true)
@ApiModel("数据来源信息")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class SourceBean implements Serializable {
    private static final long serialVersionUID = -8250790607324565015L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 128)
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
}
