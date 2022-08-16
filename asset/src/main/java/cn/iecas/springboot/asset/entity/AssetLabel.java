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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author malin
 * @since 2022-07-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)//此注解会生成equals(Object other) 和 hashCode()方法。
@Accessors(chain = true)//这样就可以用链式访问，该注解设置为chain=true，生成setter方法返回this（也就是返回的是对象），代替了默认的返回void
@ApiModel(value = "资产标签")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")//是hibernate所提供的自定义主键生成策略生成器
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
//表示当前的实体类与哪张表进行直接映射
@Entity(name = "asset_label")
public class AssetLabel extends BaseBean {

    /**
     * 标签ID
     */
    @Id //表示指定实体类中用于数据映射的主键是谁
    @GeneratedValue(generator = "jpa-uuid")
//    @Column(length = 64)
    @ApiModelProperty("id")
    private String id;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String name;

    /**
     * 标签描述
     */
    @ApiModelProperty(value = "标签描述")
    private String labelDescribe;

    /**
     * 标签图标
     */
    @ApiModelProperty(value = "标签图标")
    private String iconUrl;

    /**
     * 热度值
     */
    @ApiModelProperty(value = "热度值")

    private Integer hot;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 创建人
     */
//    @JsonIgnore
    @ApiModelProperty(value = "创建人")
    @Column(name = "create_man")
    private String createMan;

    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    @Column(name = "create_name")
    private String createName;

    /**
     * 创建日期
     */
//    @ApiModelProperty(value = "创建日期")
//    @TableField("create_time")
//    private String createTime;

//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @TableField("create_time")
//    private Date createTime;

//    /**
//     * 更新日期
//     */
//    @ApiModelProperty(value = "更新日期")
//    @TableField("update_time")
//    private String updateTime;

//    @ApiModelProperty(value = "更新日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @TableField("update_time")
//    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @Column(name = "update_man")
    private String updateMan;

    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称")
    @Column(name = "update_name")
    private String updateName;

}
