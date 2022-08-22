package cn.iecas.springboot.controller;

import cn.iecas.springboot.bean.DiSchedulerBean;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.validator.groups.Add;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.log.annotation.OperationLog;
import cn.iecas.springboot.framework.log.enums.OperationLogType;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author dragon
 * @since 2022/8/15 15:47
 */
@Api("数据接入调度类管理")
@RestController
@Module("DiScheduler")
@RequestMapping("/di/scheduler")
@CrossOrigin(value = "*", maxAge = 3600)
public class SchedulerController extends BaseController<DiSchedulerBean, Long> {

    @Autowired
    private SchedulerService schedulerService;

    @GetMapping("/census")
    @ApiOperation("调度信息统计")
    protected ApiResult<Object> getCensus(){
        // TODO
        return ApiResult.success("todo");
    }

    @Override
    @PostMapping
    @ApiOperation("调度新增")
    protected ApiResult<DiSchedulerBean> add(@RequestBody DiSchedulerBean data) {
        return schedulerService.add(data);
    }

    @Override
    @DeleteMapping("/{schedulerId}")
    @ApiOperation("调度删除")
    protected ApiResult<String> delete(@PathVariable Long schedulerId) {
        return schedulerService.delete(schedulerId);
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation("调度批量删除")
    protected ApiResult<String> batchDelete(@RequestBody List<Long> schedulerIdList) {
        return schedulerService.batchDelete(schedulerIdList);
    }

    @Override
    protected ApiResult<DiSchedulerBean> update(@RequestBody DiSchedulerBean data) {
        return ApiResult.fail("add /{schedulerId} in url and try again!");
    }

    @PutMapping("/{schedulerId}")
    @ApiOperation("调度更新")
    protected ApiResult<DiSchedulerBean> update(@RequestBody DiSchedulerBean data, @PathVariable Long schedulerId) {
        // TODO
        return schedulerService.update(data, schedulerId);
    }

    @Override
    @GetMapping("/{schedulerId}")
    @ApiOperation("单个调度信息查询")
    protected ApiResult<DiSchedulerBean> getOne(@PathVariable Long schedulerId) {
        return schedulerService.getOne(schedulerId);
    }

    @Override
    @PostMapping("/find")
    @ApiOperation("调度信息分页查询")
    protected ApiResult<PageResult<DiSchedulerBean>> getList(SearchParam param) {
        // TODO
        return schedulerService.getList(param);
    }

    @PostMapping("/upOrDownLine")
    @ApiOperation("调度上线/下线")
    protected ApiResult<String> schedulerUpOrDownLine(Object object){
        // TODO: 调度, 审核, 任务的流程图
        return ApiResult.success("todo");
    }

}
