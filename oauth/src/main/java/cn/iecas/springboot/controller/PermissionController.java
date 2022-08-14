package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.PermissionBean;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.PermissionService;
import cn.iecas.springboot.vo.PermissionTreeVo;
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
 * 系统权限 前端控制器
 * </pre>
 *
 * @author ch
 * @since 2021-10-14
 */
@Slf4j
@RestController
@RequestMapping("/permission")
@Module("system")
@Api(value = "系统权限 API", tags = {"系统权限"})
public class PermissionController extends BaseController<PermissionBean, Long> {

    @Autowired
    private PermissionService permissionService;

    @Override
    @PostMapping("/add")
    @RequiresPermissions("sys:permission:add")
    @OperationLog(name = "添加系统权限", type = OperationLogType.ADD)
    @ApiOperation("添加系统权限")
    protected ApiResult<PermissionBean> add(@Validated @RequestBody PermissionBean data) {
        return permissionService.add(data);
    }

    @Override
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:permission:del")
    @OperationLog(name = "删除系统权限", type = OperationLogType.DELETE)
    @ApiOperation("删除系统权限")
    protected ApiResult<String> delete(@PathVariable("id") Long id) {
        return permissionService.remove(id);
    }

    @Override
    @PostMapping("/modify")
    @RequiresPermissions("sys:permission:modify")
    @OperationLog(name = "修改系统权限", type = OperationLogType.UPDATE)
    @ApiOperation("修改系统权限")
    protected ApiResult<PermissionBean> update(@Validated @RequestBody PermissionBean data) {
        return permissionService.modify(data);
    }

    @Override
    @PostMapping("/info/{id}")
    @RequiresPermissions("sys:permission:info")
    @OperationLog(name = "获取权限详情", type =OperationLogType.INFO)
    @ApiOperation("获取权限详情")
    protected ApiResult<PermissionBean> getOne(@PathVariable("id") Long id) {
        return permissionService.getOne(id);
    }

    @Override
    @PostMapping("/list")
    @RequiresPermissions("sys:permission:list")
    @OperationLog(name = "系统权限分页列表", type = OperationLogType.PAGE)
    @ApiOperation("系统权限分页列表")
    protected ApiResult<PageResult<PermissionBean>> getList(@Validated @RequestBody SearchParam param) {
        return permissionService.getList(param);
    }

    @PostMapping("/all/list")
    @RequiresPermissions("sys:permission:all:list")
    @OperationLog(name = "获取所有菜单列表", type = OperationLogType.LIST)
    @ApiOperation("获取所有菜单列表")
    public ApiResult<List<PermissionBean>> getAllList() {
        return permissionService.getAllList();
    }

    @PostMapping("/all/tree")
    @RequiresPermissions("sys:permission:all:tree")
    @OperationLog(name = "获取所有菜单树形列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation("获取所有菜单树形列表")
    public ApiResult<List<PermissionTreeVo>> getAllTree() {
        return permissionService.getAllTree();
    }

    @PostMapping("/list/{uid}")
    @RequiresPermissions("sys:permission:user:list")
    @OperationLog(name = "根据用户 id 获取菜单列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation("根据用户 id 获取菜单列表")
    public ApiResult<List<PermissionBean>> getMenuListByUserId(@PathVariable("uid") Long uid) {
        return permissionService.getMenuListByUserId(uid);
    }

    @PostMapping("/tree/{uid}")
    @RequiresPermissions("sys:permission:tree:list")
    @OperationLog(name = "根据用户 id 获取菜单树形列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation("根据用户 id 获取菜单树形列表")
    public ApiResult<List<PermissionTreeVo>> getMenuTreeByUserId(@PathVariable("uid") Long uid) {
        return permissionService.getMenuTreeByUserId(uid);
    }

    @PostMapping("/level/three/{rid}")
    @RequiresPermissions("sys:permission:level:three")
    @OperationLog(name = "根据角色 id 获取该对应的所有三级权限 id", type = OperationLogType.OTHER_QUERY)
    @ApiOperation("根据角色 id 获取该对应的所有三级权限 id")
    public ApiResult<List<Long>> getThreeLevelPermissionIdsByRoleId(@PathVariable("rid") Long roleId) {
        return permissionService.getThreeLevelPermissionIdsByRoleId(roleId);
    }

    @PostMapping("/menu/tree")
    @RequiresPermissions("sys:permission:menu:tree")
    @OperationLog(name = "获取所有导航菜单(一级/二级菜单)", type = OperationLogType.OTHER_QUERY)
    @ApiOperation("获取所有导航菜单(一级/二级菜单)")
    public ApiResult<List<PermissionTreeVo>> getNavMenuTree() {
        return permissionService.getNavMenuTree();
    }
}
