package cn.iecas.springboot.framework.util;

import cn.iecas.springboot.framework.core.pagination.PageParam;
import cn.iecas.springboot.framework.core.pagination.enums.SortStateEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Pageable 生成工具
 * @author ch
 * @date 2021-10-12
 */
public class PageableUtil {

    public static Pageable generatePageable(PageParam param) {
        Sort sort = Sort.by(new Sort.Order(SortStateEnum.ASC == param.getSortedType() ? Sort.Direction.ASC : Sort.Direction.DESC, param.getSortedField()));
        return PageRequest.of(param.getCurPage() - 1, param.getPageSize(), sort);
    }
}
