package cn.iecas.springboot.controller;

import cn.iecas.springboot.entity.AddEntity;
import cn.iecas.springboot.entity.BigEntity;
import cn.iecas.springboot.entity.UpdateEntity;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.service.DbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("数据源管理")
@RestController
@Module("model")
@RequestMapping("/DbSource")
@CrossOrigin(value = "*", maxAge = 3600)
public class DbSourceController {
    @Autowired
    private DbService dbService;

    @PostMapping("/add")
    @ApiOperation("添加数据源")
    protected ApiResult<Boolean> add(AddEntity addEntity){
        return dbService.add(addEntity);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除数据源")
    protected ApiResult<String> remove(@PathVariable("id") String id) {
        return dbService.remove(id);
    }

    @PostMapping("/modify")
    @ApiOperation("修改数据源")
    protected ApiResult<Boolean> modify(UpdateEntity updateEntity) {
        return dbService.modify(updateEntity);
    }

    @PostMapping("/list")
    @ApiOperation("数据源分页列表")
    protected ApiResult<PageResult<BigEntity>> getList(@Validated @RequestBody SearchParam param) {
        return dbService.getList(param);
    }
}
