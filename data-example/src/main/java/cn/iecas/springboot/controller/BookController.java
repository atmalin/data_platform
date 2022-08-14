package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.BookBean;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ch
 * @since 2022-07-23
 */
@Api("书本类管理")
@RestController
@Module("model")
@RequestMapping("/example/book")
@CrossOrigin(value = "*", maxAge = 3600)
public class BookController extends BaseController<BookBean, String> {

    @Autowired
    private BookService bookService;

    @Override
    @PostMapping("/add")
    @ApiOperation("增加书本")
    protected ApiResult<BookBean> add(BookBean data) {
        return bookService.add(data);
    }

    @Override
    protected ApiResult<String> delete(String id) {
        return bookService.delete(id);
    }

    @Override
    protected ApiResult<BookBean> update(BookBean data) {
        return bookService.update(data);
    }

    @Override
    protected ApiResult<BookBean> getOne(String id) {
        return bookService.getOne(id);
    }

    @Override
    protected ApiResult<PageResult<BookBean>> getList(SearchParam param) {
        return bookService.getList(param);
    }
}
