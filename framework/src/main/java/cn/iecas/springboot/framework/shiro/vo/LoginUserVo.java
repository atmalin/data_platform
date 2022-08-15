package cn.iecas.springboot.framework.shiro.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * 登录用户对象，响应给前端
 * </p>
 *
 * @author ch
 * @date 2021-10-15
 **/
@Data
@Accessors(chain = true)
public class LoginUserVo implements Serializable {

    private static final long serialVersionUID = -4261077665508765946L;

    @ApiModelProperty("主键")
//    private Long id;
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("状态，0：禁用，1：启用，2：锁定")
    private Integer state;

    @ApiModelProperty("部门id")
    private Long departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("角色编码列表")
    private Set<String> roleCodes;

    @ApiModelProperty("权限编码列表")
    private Set<String> permissionCodes;
}
