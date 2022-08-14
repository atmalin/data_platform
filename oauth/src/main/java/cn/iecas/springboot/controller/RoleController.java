package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.RoleBean;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.core.validator.groups.Update;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.param.role.UpdateRolePermissionParam;
import cn.iecas.springboot.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 * 系统角色 前端控制器
 * </pre>
 *
 * @author ch
 * @since 2021-10-14
 */
@Slf4j
@Api(tags = {"系统角色管理"})
@RestController
@Module("role")
@RequestMapping("/role")
@CrossOrigin(value = "*", maxAge = 3600)
public class RoleController extends BaseController<RoleBean, Long> {

    @Autowired
    private RoleService roleService;

    @Override
    @PostMapping("/add")
    @ApiOperation("添加系统角色")
    @RequiresPermissions("sys:role:add")
    @OperationLog(name = "添加系统角色", type = OperationLogType.ADD)
    protected ApiResult<RoleBean> add(@Validated(Add.class) @RequestBody RoleBean data) {
        return roleService.add(data);
    }

    @Override
    @DeleteMapping("/{id}")
    @ApiOperation("删除系统角色")
    @RequiresPermissions("sys:role:del")
    @OperationLog(name = "删除系统角色", type = OperationLogType.DELETE)
    protected ApiResult<String> delete(@PathVariable("id") Long id) {
        return roleService.remove(id);
    }

    @Override
    @PostMapping("/modify")
    @RequiresPermissions("sys:role:modify")
    @OperationLog(name = "修改系统角色", type = OperationLogType.UPDATE)
    @ApiOperation("修改系统角色")
    protected ApiResult<RoleBean> update(@Validated(Update.class) @RequestBody RoleBean data) {
        return roleService.modify(data);
    }

    @Override
    @PostMapping("/info/{id}")
    @RequiresPermissions("sys:role:info")
    @OperationLog(name = "系统角色详情", type = OperationLogType.INFO)
    @ApiOperation("系统角色详情")
    protected ApiResult<RoleBean> getOne(@PathVariable("id") Long id) {
        return roleService.getOne(id);
    }

    @Override
    @PostMapping("/list")
    @RequiresPermissions("sys:role:list")
    @OperationLog(name = "修改系统角色权限", type = OperationLogType.UPDATE)
    @ApiOperation("修改系统角色权限")
    protected ApiResult<PageResult<RoleBean>> getList(@RequestBody SearchParam param) {
        return roleService.getList(param);
    }

    @PostMapping("/role/permission/update")
    @RequiresPermissions("sys:role:permission:update")
    @OperationLog(name = "修改系统角色权限", type = OperationLogType.UPDATE)
    @ApiOperation("修改系统角色权限")
    public ApiResult<String> updateRolePermission(@Validated @RequestBody UpdateRolePermissionParam param) {
        return roleService.updateRolePermission(param);
    }
}
