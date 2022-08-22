package cn.iecas.springboot.entity;


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
@Table(name="tb_button")
@EqualsAndHashCode
@ApiModel(value = "User实体类", description = "用户")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ButtonBean {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键", hidden = true)
    @NotNull(message = "id 不能为空", groups = {Update.class})
    @Id
    private Integer id;

    @ApiModelProperty(value = "菜单id")
    private String menu_id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "英文名称")
    private String en_name;

    @ApiModelProperty(value = "接口请求url")
    private String url;

    @ApiModelProperty(value = "路由url")
    private String route_url;
}
