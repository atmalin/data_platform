package cn.iecas.springboot.controller;

import cn.iecas.springboot.entity.BigEntity;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.DbInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("数据源管理")
@RestController
@Module("model")
@RequestMapping("/source")
@CrossOrigin(value = "*", maxAge = 3600)
public class DbController extends BaseController<BigEntity, String> {
    @Autowired
    private DbInfoService dbInfoService;

    @Override
    @PostMapping("/add")
    @ApiOperation("添加数据源")
    protected ApiResult<BigEntity> add(BigEntity bigEntity){
        return dbInfoService.add(bigEntity);
    }

    @Override
    @DeleteMapping("/{id}")
    @ApiOperation("删除数据源")
    protected ApiResult<String> delete(@PathVariable("id") String id) {
        return dbInfoService.remove(id);
    }

    @Override
    @PostMapping("/update")
    @ApiOperation("修改数据源")
    protected ApiResult<BigEntity> update(BigEntity bigEntity) {
        return dbInfoService.update(bigEntity);
    }

    @Override
    @PostMapping("/info/{id}")
    @ApiOperation("获取数据源详情")
    protected ApiResult<BigEntity> getOne(@PathVariable("id") String id) {
        return dbInfoService.getOne(id);
    }

    @Override
    @PostMapping("/list")
    @ApiOperation("数据源分页列表")
    protected ApiResult<PageResult<BigEntity>> getList(@Validated @RequestBody SearchParam param) {
        return dbInfoService.getList(param);
    }
}
