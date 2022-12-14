package cn.iecas.springboot.controller;

import cn.iecas.springboot.entity.RoleBean;
import cn.iecas.springboot.entity.dataBean.RoleAdd;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.core.validator.groups.Update;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
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
 * 系统角色管理
 *
 *
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

    @PostMapping("/add")
    @ApiOperation("添加系统角色")
    @RequiresPermissions("sys:role:add")
    @OperationLog(name = "添加系统角色", type = OperationLogType.ADD)
    protected ApiResult<RoleBean> add(@Validated(Add.class) @RequestBody RoleAdd data) {

        return roleService.add(data);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除系统角色")
    @RequiresPermissions("sys:role:delete")
    @OperationLog(name = "删除系统角色",type = OperationLogType.DELETE)
    protected ApiResult<String> delete(String aLong) {
        return roleService.remove(aLong);
    }

    /**
     * ’数据中台‘系统查询角色走的role/list 返回结果为所有角色信息
     * @param
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取角色列表")
    @RequiresPermissions("sys:role:list")
    @OperationLog(name = "获取角色分页列表",type = OperationLogType.LIST)
    protected ApiResult<List<RoleBean>> List() {
        return roleService.list();
    }

    @PostMapping("/modify")
    @RequiresPermissions("sys:role:modify")
    @OperationLog(name = "修改系统角色", type = OperationLogType.UPDATE)
    @ApiOperation("修改系统角色")
    protected ApiResult<RoleBean> update(@Validated(Update.class) @RequestBody RoleAdd data) {
        return roleService.modify(data);
    }



    @Override
    protected ApiResult<RoleBean> add(RoleBean data) {
        return null;
    }

    @Override
    protected ApiResult<String> delete(Long aLong) {
        return null;
    }

    @Override
    protected ApiResult<RoleBean> update(RoleBean data) {
        return null;
    }

    @Override
    protected ApiResult<RoleBean> getOne(Long aLong) {
        return null;
    }

    @Override
    protected ApiResult<PageResult<RoleBean>> getList(SearchParam param) {
        return null;
    }

//    @GetMapping("/find")
//    @ApiOperation("查询所有系统角色")
//    @RequiresPermissions("sys:role:find")
//    @OperationLog(name = "查询系统角色", type = OperationLogType.OTHER_QUERY)
//    protected ApiResult<String> find() {
//        return roleService.find();
//    }

//
//    @Override
//    protected ApiResult<RoleBean> update(RoleBean data) {
//        return null;
//    }
//
//    @Override
//    protected ApiResult<RoleBean> getOne(Long aLong) {
//        return null;
//    }
//
//    @Override
//    protected ApiResult<PageResult<RoleBean>> getList(SearchParam param) {
//        return null;
//    }
}
