package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.UserBean;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.core.validator.groups.Update;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.param.user.ResetPasswordParam;
import cn.iecas.springboot.param.user.UpdatePasswordParam;
import cn.iecas.springboot.param.user.UploadAvatarParam;
import cn.iecas.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * 系统用户 前端控制器
 * </pre>
 *
 * @author ch
 * @since 2021-09-27
 */
@Slf4j
@Api(tags = {"系统用户管理"})
@RestController
@Module("system")
@RequestMapping("/user")
@CrossOrigin(value = "*", maxAge = 3600)
public class UserController extends BaseController<UserBean, Long> {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/add")
    @ApiOperation("添加系统用户")
    @RequiresPermissions("sys:user:add")
    @OperationLog(name = "添加系统用户", type = OperationLogType.ADD)
    protected ApiResult<UserBean> add(@Validated(Add.class) @RequestBody UserBean userBean) {
        return userService.addUser(userBean);
    }

    @Override
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:user:del")
    @OperationLog(name = "删除系统用户", type = OperationLogType.DELETE)
    @ApiOperation("删除系统用户")
    protected ApiResult<String> delete(@PathVariable("id") Long id) {
        return userService.remove(id);
    }

    @Override
    @PostMapping("/modify")
    @RequiresPermissions("sys:user:modify")
    @OperationLog(name = "修改系统用户", type = OperationLogType.UPDATE)
    @ApiOperation("修改系统用户")
    protected ApiResult<UserBean> update(@Validated(Update.class) @RequestBody UserBean userBean) {
        return userService.modify(userBean);
    }

    @Override
    @PostMapping("/info/{id}")
    @RequiresPermissions("sys:user:info:id")
    @OperationLog(name = "根据 id 获取用户详情", type = OperationLogType.INFO)
    @ApiOperation("根据 id 获取用户详情")
    protected ApiResult<UserBean> getOne(@PathVariable("id") Long id) {
        return userService.getOne(id);
    }

    @Override
    @PostMapping("/list")
    @RequiresPermissions("sys:user:list")
    @OperationLog(name = "获取用户分页列表", type = OperationLogType.LIST)
    @ApiOperation("获取用户分页列表")
    protected ApiResult<PageResult<UserBean>> getList(@RequestBody SearchParam param) {
        return userService.list(param);
    }

    @PostMapping("/password/update")
    @RequiresPermissions("sys:user:password:update")
    @OperationLog(name = "修改密码", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改密码")
    public ApiResult<String> updatePassword(@Validated @RequestBody UpdatePasswordParam param){
        return userService.updatePassword(param);
    }

    @PostMapping("/password/reset")
    @RequiresPermissions("sys:user:password:reset")
    @OperationLog(name = "管理员重置用户密码", type = OperationLogType.UPDATE)
    @ApiOperation(value = "管理员重置用户密码")
    public ApiResult<String> resetPassword(@Validated @RequestBody ResetPasswordParam param) {
        return userService.resetPassword(param);
    }

    @PostMapping("/avatar/update")
    @RequiresPermissions("sys:user:avatar:update")
    @OperationLog(name = "修改头像", type = OperationLogType.UPDATE)
    @ApiOperation(value = "修改头像")
    public ApiResult<String> updateAvatar(@Validated @RequestBody UploadAvatarParam param) {
        return userService.updateAvatar(param);
    }
}
