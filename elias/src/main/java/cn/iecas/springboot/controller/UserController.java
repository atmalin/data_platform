package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.UserSelfBean;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.dao.UserSelfDao;
import cn.iecas.springboot.service.UserSelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("用户测试类管理")
@Module("model")
@RequestMapping("/userself")
@CrossOrigin(value = "*", maxAge = 3600)
public class UserController extends BaseController<UserSelfBean, String>  {

    @Autowired
    private UserSelfService userSelfService;

    @Override
    @PostMapping("/add")
    @ApiOperation("增加用户")
    protected ApiResult<UserSelfBean> add(UserSelfBean data) {
        return userSelfService.add(data);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    protected ApiResult<String> delete(@PathVariable("id") String id) {
        return userSelfService.delete(id);
    }

    @Override
    @PostMapping("/update")
    @ApiOperation("更新用户")
    protected ApiResult<UserSelfBean> update(UserSelfBean data) {
        return userSelfService.update(data);
    }

//    @Override
//    @GetMapping("/get")
//    @ApiOperation("获取一个用户")
//    protected ApiResult<UserSelfBean> getOne(String id) {
//        System.out.println(id);
//        return userSelfService.getOne(id);
//    }

    @Override
    @GetMapping("/get")
    @ApiOperation("获取一个用户")
    protected ApiResult<UserSelfBean> getOne(String id) {
        System.out.println(id);
        return userSelfService.getOne(id);
    }

    @Override
    protected ApiResult<PageResult<UserSelfBean>> getList(SearchParam param) {
        return null;
    }
}
