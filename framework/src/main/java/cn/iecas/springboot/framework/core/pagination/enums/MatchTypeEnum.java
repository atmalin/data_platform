package cn.iecas.springboot.framework.core.pagination.enums;

import cn.iecas.springboot.framework.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 匹配类型枚举
 *
 * @author ch
 * @date 2021-09-29
 */
@Getter
@AllArgsConstructor
public enum MatchTypeEnum implements BaseEnum {
    /** 精确匹配 **/
    ACCURATE(1, "精确匹配"),
    /** 模糊匹配 **/
    FUZZY(2,"模糊匹配"),
    // 过滤集合
    IN(3, "过滤集合"),
    /** 小于 **/
    LESS(4,"小于"),
    /** 等于 **/
    EQUAL(5, "等于"),
    /** 大于 **/
    GREATER(6, "大于"),
    /** 早于 **/
    BEFORE(7, "早于"),
    /** 晚于 **/
    LATER(8, "晚于"),
    /** 在之间 **/
    BETWEEN(9, "在之间");

    private Integer code;
    private String desc;
}
