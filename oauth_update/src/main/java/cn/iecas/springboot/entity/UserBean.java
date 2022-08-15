package cn.iecas.springboot.entity;

import cn.iecas.springboot.framework.common.bean.BaseBean;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.core.validator.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="tb_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User实体类", description = "用户")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class UserBean extends BaseBean {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键", hidden = true)
    @NotNull(message = "id 不能为空", groups = {Update.class})
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 64)
    private String id;

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空", groups = {Add.class})
    private String username;


    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty(value = "盐值", hidden = true)
    private String salt;


    @ApiModelProperty("注册手机号")
    private String phone;

    @ApiModelProperty("注册邮箱")
    private String email;



    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("公司名称--所属部门")
    @NotNull(message = "所属部门不能为空")
    private String company;

    @ApiModelProperty("头像路径")
    private String photo_path;




}
