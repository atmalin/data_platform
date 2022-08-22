package cn.iecas.springboot.controller;

import cn.iecas.springboot.entity.MenuBean;
import cn.iecas.springboot.entity.UserBean;
import cn.iecas.springboot.entity.dataBean.MenuVo;
import cn.iecas.springboot.entity.dataBean.UserAdd;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.MenuService;
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
@Api(tags = {"菜单管理"})
@RestController
@Module("menu")
@RequestMapping("/menu")
@CrossOrigin(value = "*", maxAge = 3600)
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    @OperationLog(name = "获取菜单分页列表", type = OperationLogType.LIST)
    @ApiOperation("获取菜单分页列表")
    protected ApiResult<List<MenuBean>> List() {
        return menuService.list();
    }

    @PostMapping("/add")
    @ApiOperation("添加菜单")
    @RequiresPermissions("sys:menu:add")
    @OperationLog(name = "添加菜单", type = OperationLogType.ADD)
    protected ApiResult<MenuBean> add(@Validated(Add.class) @RequestBody MenuBean menuBean) {
        return menuService.add(menuBean);
    }



}
