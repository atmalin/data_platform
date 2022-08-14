package cn.iecas.springboot.framework.common.controller;

import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller父类
 *
 * @author ch
 * @date 2021-09-29
 */
@Slf4j
public abstract class BaseController<T, ID> {

    /**
     * 增加
     * @param data
     * @return
     */
    protected abstract ApiResult<T> add(T data);

    /**
     * 删除
     * @param id
     * @return
     */
    protected abstract ApiResult<String> delete(ID id);

    /**
     * 修改
     * @param data
     * @return
     */
   protected abstract ApiResult<T> update(T data);

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    protected abstract ApiResult<T> getOne(ID id);

    /**
     * 分页查询
     * @return
     */
    protected abstract ApiResult<PageResult<T>> getList(SearchParam param);

}
