package cn.iecas.springboot.bean;

import cn.iecas.springboot.entity.ExampleEntity;
import cn.iecas.springboot.framework.common.bean.BaseBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@ApiModel("书本类")
@Table(name="book")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class BookBean extends BaseBean {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 64)
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("中文名称")
    private String nameCn;

    @ApiModelProperty("英文名称")
    private String nameEn;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @ApiModelProperty("标签")
    private ExampleEntity tags;

    @ApiModelProperty("描述")
    private String description;
}
