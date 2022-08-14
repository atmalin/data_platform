package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.BookBean;
import cn.iecas.springboot.dao.BookDao;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public ApiResult<BookBean> add(BookBean data) {
        BookBean book = bookDao.save(data);
        return ApiResult.success(book);
    }

    public ApiResult<String> delete(String id) {
        if (bookDao.existsById(id)) {
            bookDao.deleteById(id);
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.fail("该资源已被删除");
        }
    }

    public ApiResult<BookBean> update(BookBean data) {
        BookBean bookBean = bookDao.saveAndFlush(data);
        return ApiResult.success(bookBean);
    }

    public ApiResult<BookBean> getOne(String id) {
        if (bookDao.existsById(id)) {
            BookBean bookBean = bookDao.getOne(id);
            return ApiResult.success(bookBean);
        } else {
            return ApiResult.fail("该资源不存在");
        }
    }

    public ApiResult<PageResult<BookBean>> getList(SearchParam param) {
        Example<BookBean> bookBeanExample = ExampleUtil.generateExampleWithDefaultIgnoreProps(param.getQueryCondition(), BookBean.class);

        BookBean bookBean = new BookBean();
        bookBean.setNameCn("钢铁是怎么炼成的");
        Example<BookBean> example = Example.of(bookBean,
                ExampleMatcher.matching().withMatcher("name_cn", ExampleMatcher.GenericPropertyMatcher::exact));


        Pageable pageable = PageableUtil.generatePageable(param);
        Page<BookBean> bookBeans = bookDao.findAll(bookBeanExample, pageable);
        return ApiResult.success(new PageResult<>(bookBeans));
    }
}
