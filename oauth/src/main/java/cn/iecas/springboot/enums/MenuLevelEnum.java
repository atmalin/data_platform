package cn.iecas.springboot.enums;

import cn.iecas.springboot.framework.common.enums.BaseEnum;

/**
 * 层级枚举
 * @author ch
 * @date 2021-10-14
 **/
public enum MenuLevelEnum implements BaseEnum {
    /** 一级菜单 **/
    ONE(1, "一级菜单"),
    /** 二级菜单 **/
    TWO(2, "二级菜单"),
    /** 功能菜单 **/
    THREE(3, "功能菜单");

    private Integer code;
    private String desc;

    MenuLevelEnum(Integer code, String desc) {
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
