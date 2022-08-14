package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.DepartmentBean;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.DepartmentService;
import cn.iecas.springboot.vo.DepartmentTreeVo;
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
 * 部门 前端控制器
 * </pre>
 *
 * @author ch
 * @since 2021-10-13
 */
@Slf4j
@RestController
@RequestMapping("/department")
@Module("system")
@Api(value = "系统部门API", tags = {"系统部门"})
public class DepartmentController extends BaseController<DepartmentBean, Long> {

    @Autowired
    private DepartmentService departmentService;


    @Override
    @PostMapping("/add")
    @RequiresPermissions("sys:department:add")
    @OperationLog(name = "添加部门", type = OperationLogType.ADD)
    @ApiOperation("添加部门")
    protected ApiResult<DepartmentBean> add(@Validated @RequestBody DepartmentBean departmentBean) {
        return departmentService.addDepartment(departmentBean);
    }

    @Override
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:department:del")
    @OperationLog(name = "删除部门", type = OperationLogType.DELETE)
    @ApiOperation("删除部门")
    protected ApiResult<String> delete(@PathVariable("id") Long id) {
        return departmentService.remove(id);
    }

    @Override
    @PostMapping("/modify")
    @RequiresPermissions("sys:department:update")
    @OperationLog(name = "修改部门", type = OperationLogType.UPDATE)
    @ApiOperation("修改部门")
    protected ApiResult<DepartmentBean> update(@Validated @RequestBody DepartmentBean data) {
        return departmentService.modify(data);
    }

    @Override
    @PostMapping("/info/{id}")
    @RequiresPermissions("sys:department:info")
    @OperationLog(name = "部门详情", type = OperationLogType.INFO)
    @ApiOperation("部门详情")
    protected ApiResult<DepartmentBean> getOne(@PathVariable("id") Long id) {
        return departmentService.getOne(id);
    }

    @Override
    @PostMapping("/list")
    @RequiresPermissions("sys:department:list")
    @OperationLog(name = "获取部门分页列表", type = OperationLogType.PAGE)
    @ApiOperation("获取部门分页列表")
    protected ApiResult<PageResult<DepartmentBean>> getList(@RequestBody SearchParam param) {
        return departmentService.list(param);
    }

    @PostMapping("/all/list")
    @RequiresPermissions("sys:department:all:list")
    @OperationLog(name = "获取所有启用部门列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation("获取所有启用部门列表")
    public ApiResult<List<DepartmentBean>> listAll() {
        return departmentService.listAll();
    }

    @PostMapping("/all/tree")
    @RequiresPermissions("sys:department:all:tree")
    @OperationLog(name = "获取所有部门的树形列表", type = OperationLogType.OTHER_QUERY)
    @ApiOperation("获取所有部门的树形列表")
    public ApiResult<List<DepartmentTreeVo>> getDepartmentTree() {
        return departmentService.getDepartmentTree();
    }
}
