package cn.iecas.springboot.controller;



import cn.iecas.springboot.entity.UserBean;
import cn.iecas.springboot.entity.dataBean.UserAdd;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.core.validator.groups.Update;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 *
 *
 */
@Slf4j
@Api(tags = {"用户管理"})
@RestController
@Module("user")
@RequestMapping("/user")
@CrossOrigin(value = "*", maxAge = 3600)
public class UserController extends BaseController<UserAdd, Long> {

    @Autowired
    private UserService userService;


    @Override
    @PostMapping("/add")
    @ApiOperation("添加系统用户")
    @RequiresPermissions("sys:user:add")
    @OperationLog(name = "添加系统用户", type = OperationLogType.ADD)
    protected ApiResult<UserAdd> add(@Validated(Add.class) @RequestBody UserAdd user) {
        return userService.add(user);
    }

    @Override
    protected ApiResult<String> delete(Long aLong) {
        return null;
    }

    @PostMapping("/delete")
    @ApiOperation("删除系统用户")
    @RequiresPermissions("sys:user:delete")
    @OperationLog(name = "添加系统用户", type = OperationLogType.ADD)
    protected ApiResult<String> delete(String userId) {
        return userService.remove(userId);
    }

    @PutMapping("/update")
    @ApiOperation("修改系统用户")
    @RequiresPermissions("sys:user:update")
    @Override
    protected ApiResult<UserAdd> update(UserAdd userAddBean) {
        return userService.modify(userAddBean);
    }

    @Override
    protected ApiResult<UserAdd> getOne(Long aLong) {
        return null;
    }

    @Override
    protected ApiResult<PageResult<UserAdd>> getList(SearchParam param) {
        return null;
    }
    @PostMapping("/list")
    @RequiresPermissions("sys:user:list")
    @OperationLog(name = "获取用户分页列表", type = OperationLogType.LIST)
    @ApiOperation("获取用户分页列表")
    protected ApiResult<PageResult<UserBean>> List(@RequestBody SearchParam param) {
        return userService.list(param);
    }


    //
//
//
    @PostMapping("/find")
    @RequiresPermissions("sys:user:find")
    @OperationLog(name = "根据昵称查询系统用户", type = OperationLogType.UPDATE)
    @ApiOperation("根据昵称查询系统用户")
    protected ApiResult<List<UserBean>> getOne(String nickname) {

        return userService.find(nickname);
    }


}
