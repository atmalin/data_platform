package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.UserSelfBean;
import cn.iecas.springboot.dao.UserSelfDao;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import com.sun.xml.internal.ws.api.pipe.helper.AbstractPipeImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserSelfService {

    @Autowired
    private UserSelfDao userSelfDao;

    public ApiResult<UserSelfBean> add(UserSelfBean data){
        UserSelfBean userSelfBean = userSelfDao.save(data);
        return ApiResult.success(userSelfBean);
    }

    public ApiResult<UserSelfBean> update(UserSelfBean data){
        UserSelfBean userSelfBean = userSelfDao.saveAndFlush(data);
        return ApiResult.success(userSelfBean);
    }

    public ApiResult<String> delete(String id){
        if (userSelfDao.existsById(id)){
            userSelfDao.deleteById(id);
            return ApiResult.success("删除成功");
        }
        else {
            return ApiResult.fail("已删除");
        }
    }

    public ApiResult<UserSelfBean> getOne(String id){
        if (userSelfDao.existsById(id)){
            UserSelfBean userSelfBean = userSelfDao.getOne(id);
            return ApiResult.success(userSelfBean);
        } else {
            return ApiResult.fail("资源不存在");
        }
    }

    public ApiResult<PageResult<UserSelfBean>> getList(SearchParam param){
        Example<UserSelfBean> userSelfBeanExample = ExampleUtil.generateExampleWithDefaultIgnoreProps(
                param.getQueryCondition(), UserSelfBean.class);

        UserSelfBean userSelfBean = new UserSelfBean();
        userSelfBean.setUsername("zhangsan");

        Example<UserSelfBean> example = Example.of(userSelfBean, ExampleMatcher.matching().withMatcher("username",
                ExampleMatcher.GenericPropertyMatcher::exact));
        Pageable pageable = PageableUtil.generatePageable(param);

        Page<UserSelfBean> userSelfBeans = userSelfDao.findAll(userSelfBeanExample, pageable);
        return ApiResult.success(new PageResult<>(userSelfBeans));

    }








}
