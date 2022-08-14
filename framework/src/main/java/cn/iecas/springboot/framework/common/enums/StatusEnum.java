package cn.iecas.springboot.framework.common.enums;

import cn.iecas.springboot.framework.common.enums.config.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 启用禁用状态枚举
 *
 * @author ch
 * @date 2021-09-29
 */
@Getter
@SwaggerDisplayEnum
@AllArgsConstructor
public enum StatusEnum implements BaseEnum {

    /** 禁用 **/
    DISABLE(0, "禁用"),
    /** 启用 **/
    ENABLE(1, "启用");

    private Integer code;
    private String desc;

    @Override
    public String toString() {
        return code + ":" +desc;
    }
}
