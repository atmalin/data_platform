package cn.iecas.springboot.framework.core.pagination.enums;

import cn.iecas.springboot.framework.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 排序状态枚举
 *
 * @author ch
 * @date 2021-10-13
 */
@Getter
@AllArgsConstructor
public enum SortStateEnum implements BaseEnum {

    /** 升序排序 **/
    ASC(0, "升序"),
    /** 降序排序 **/
    DESC(-1, "降序");


    private Integer code;
    private String desc;
}
