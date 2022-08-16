package cn.iecas.springboot.asset.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * 标签集与标签关系表 
 * </p>
 *
 * @author malin
 * @since 2022-08-01
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)//这样就可以用链式访问，该注解设置为chain=true，生成setter方法返回this（也就是返回的是对象），代替了默认的返回void
@ApiModel("资产标签集与标签的关联bean")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")//是hibernate所提供的自定义主键生成策略生成器
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
//表示当前的实体类与哪张表进行直接映射
@Entity(name = "asset_label_rel")
public class AssetLabelRel implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @Id //表示指定实体类中用于数据映射的主键是谁
    @GeneratedValue(generator = "jpa-uuid")
//    @Column(length = 64)
    @ApiModelProperty("id")
    private String id;

    /**
     * 标签集id
     */
    @ApiModelProperty("label_set_id")
    private String labelSetId;

    /**
     * 标签id
     */
    @ApiModelProperty("label_id")
    private String labelId;


}
