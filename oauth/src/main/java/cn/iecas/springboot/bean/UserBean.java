package cn.iecas.springboot.bean;

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

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User实体类", description = "系统用户")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class UserBean extends BaseBean {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键", hidden = true)
    @NotNull(message = "id 不能为空", groups = {Update.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空", groups = {Add.class})
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty(value = "盐值", hidden = true)
    private String salt;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态，0：禁用，1：启用，2：锁定")
    private Integer state;

    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空")
    private Long departmentId;
}
