package cn.iecas.springboot.asset.entity;




import cn.iecas.springboot.framework.common.bean.BaseBean;

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

import javax.persistence.*;

/**
 * <p>
 * 标签集 
 * </p>
 *
 * @author malin
 * @since 2022-08-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)//此注解会生成equals(Object other) 和 hashCode()方法。
@Accessors(chain = true)//这样就可以用链式访问，该注解设置为chain=true，生成setter方法返回this（也就是返回的是对象），代替了默认的返回void
@ApiModel("资产标签集")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")//是hibernate所提供的自定义主键生成策略生成器
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
//表示当前的实体类与哪张表进行直接映射
@Entity(name = "asset_label_set")
public class AssetLabelSet extends BaseBean{

    /**
     * id
     */
    @Id //表示指定实体类中用于数据映射的主键是谁
    @GeneratedValue(generator = "jpa-uuid")
//    @Column(length = 64)
    @ApiModelProperty("id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("name")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty("status")
    private String status;

    /**
     * 序号
     */
    @ApiModelProperty("hot")
    private Integer hot;

    /**
     * 创建时间
     */

//    @Column(name = "create_time")
//    private String createTime;

    /**
     * 创建人
     */
    @Column(name = "create_man")
    private String createMan;

    /**
     * 删除时间
     */
//    @Column(name = "update_time")
//    private String updateTime;

    /**
     * 删除人
     */
    @Column(name = "update_man")
    private String updateMan;

    /**
     * 标签id集合
     */



}
