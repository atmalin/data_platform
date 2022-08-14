package cn.iecas.springboot.enums;

import cn.iecas.springboot.framework.common.enums.BaseEnum;

/**
 * 层级枚举
 * @author ch
 * @date 2021-10-14
 **/
public enum RoleTypeEnum implements BaseEnum {
    /** 功能角色 **/
    FUNCTION(0, "功能角色"),
    /** 数据角色 **/
    DATA(1, "数据角色");

    private Integer code;
    private String desc;

    RoleTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
