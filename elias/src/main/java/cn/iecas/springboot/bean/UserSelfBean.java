package cn.iecas.springboot.bean;

import cn.iecas.springboot.framework.common.bean.BaseBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@Accessors(chain = true)
@ApiModel("测试用户类")
@Table(name="user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class UserSelfBean extends BaseBean {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 64)
    @ApiModelProperty("id")
    private String id;

    @Column(nullable = false, unique = true, length = 20)
    @ApiModelProperty("用户名")
    private String username;

    @Column(nullable = false, length = 20)
    @ApiModelProperty("密码")
    private String password;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("注册时间")
    private Date regdate;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    @ApiModelProperty("标签")
    private LabelEntity tags;

}