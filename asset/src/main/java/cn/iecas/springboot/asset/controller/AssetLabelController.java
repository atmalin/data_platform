package cn.iecas.springboot.asset.controller;



import cn.iecas.springboot.asset.entity.AssetLabel;

import cn.iecas.springboot.asset.service.AssetLabelService;
import cn.iecas.springboot.framework.common.controller.BaseController;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.log.annotation.Module;
import cn.iecas.springboot.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签表  前端控制器
 * </p>
 *
 * @author malin
 * @since 2022-07-21
 * http://localhost:8081/asset/asset-label/findAllLabel
 * 前端展示的第一个界面的功能
 */


@Module("model")
@Api(tags = "资产标签管理接口")
@RestController
@RequestMapping("/asset-label")
@CrossOrigin(value = "*", maxAge = 3600)
public class AssetLabelController extends BaseController<AssetLabel, String> {

    @Autowired
    private AssetLabelService assetLabelService;


    @Override
    @PostMapping("/add")
    @ApiOperation("增加标签")
    protected ApiResult<AssetLabel> add(AssetLabel data) {
        return assetLabelService.add(data);
    }

    @Override
    @PostMapping("/delete")
    @ApiOperation("删除标签")
    protected ApiResult<String> delete(String id) {
        return assetLabelService.delete(id);
    }

    @Override
    @PostMapping("/update")
    @ApiOperation("修改标签")
    protected ApiResult<AssetLabel> update(AssetLabel data) {
        return assetLabelService.update(data);
    }

    @Override
    @PostMapping("/getOne")
    @ApiOperation("根据id查标签集")
    protected ApiResult<AssetLabel> getOne(String id) {
        return assetLabelService.getOne(id);
    }

    @Override
    @PostMapping("/getList")
    @ApiOperation("条件查询标签")
    protected ApiResult<PageResult<AssetLabel>> getList( SearchParam param) {
        return assetLabelService.getList(param);
    }


//    @ApiOperation("查询所有资产标签")
//    @GetMapping("/findAllLabel")
//    public Result findAllLabel() {
//        List<AssetLabel> list = assetLabelService.list();
//        return Result.ok(list).message("查询数据成功");
//    }


//    @ApiOperation("删除标签")
//    @DeleteMapping("remove/{id}")
//    public Result removeLabel(@ApiParam(name = "id", value = "ID", required = true)
//                              @PathVariable String id) {
//        boolean isSuccess = assetLabelService.removeById(id);
//        if (isSuccess) {
//            return Result.ok(null);
//        } else {
//            return Result.fail(null);
//        }
//    }

//    //3 条件查询分页
//    @ApiOperation("条件查询分页")
//    @PostMapping("findQueryPage/{page}/{limit}")
//    public Result findPage(
//            @ApiParam(name = "page", value = "当前页码", required = true)
//            @PathVariable long page,
//            @ApiParam(name = "limit", value = "每页记录数",required = true)
//            @PathVariable long limit,
//            @ApiParam(name = "teacherVo", value = "查询对象",required = false)
//            @RequestBody(required = false) LabelQueryVo labelQueryVo) {
//        //创建page对象
//        Page<AssetLabel> pageParam = new Page<>(page, limit);
//        //判断teacherQueryVo对象是否为空
//        if (labelQueryVo == null) {//查询全部
//            IPage<AssetLabel> pageModel =
//                    assetLabelService.page(pageParam, null);
//            return Result.ok(pageModel);
//        } else {
//            //获取条件值，
//            String name = labelQueryVo.getName();
////            Integer level = labelQueryVo.getLevel();
////            String joinDateBegin = labelQueryVo.getJoinDateBegin();
////            String joinDateEnd = labelQueryVo.getJoinDateEnd();
//            //进行非空判断，条件封装
//            QueryWrapper<AssetLabel> wrapper = new QueryWrapper<>();
//            if (!StringUtils.isEmpty(name)) {
//                wrapper.like("name", name);
//            }
////            if (!StringUtils.isEmpty(level)) {
////                wrapper.eq("level", level);
////            }
////            if (!StringUtils.isEmpty(joinDateBegin)) {
////                wrapper.ge("join_date", joinDateBegin);
////            }
////            if (!StringUtils.isEmpty(joinDateEnd)) {
////                wrapper.le("join_date", joinDateEnd);
////            }
//            //调用方法分页查询
//            IPage<AssetLabel> pageModel = assetLabelService.page(pageParam, wrapper);
//            //返回
//            return Result.ok(pageModel);
//        }
//    }

//    //4 添加标签
//    @ApiOperation("添加标签")
//    @PostMapping("saveLabel")
//    public Result saveLabel(@RequestBody AssetLabel assetLabel) {
//        boolean isSuccess = assetLabelService.save(assetLabel);
//        if (isSuccess) {
//            return Result.ok(null);
//        } else {
//            return Result.fail(null);
//        }
//    }

//    //5 修改-根据id查询标签
//    @ApiOperation("根据id查询标签")
//    @GetMapping("getLabel/{id}")
//    public Result getLabel(@PathVariable String id) {
//        AssetLabel assetLabel = assetLabelService.getById(id);
//        return Result.ok(assetLabel);
//    }

//    //6 标签修改
//    // {...}
//    @ApiOperation("标签修改")
//    @PostMapping("updateLabel")
//    public Result updateLabel(@RequestBody AssetLabel assetLabel) {
//        boolean isSuccess = assetLabelService.updateById(assetLabel);
//        if (isSuccess) {
//            return Result.ok(null);
//        } else {
//            return Result.fail(null);
//        }
//    }

//    //7 批量删除标签
//    // json数组 [1,2,3]
//    @ApiOperation("批量删除标签")
//    @DeleteMapping("removeBatch")
//    public Result removeBatch(@RequestBody List<String> idList) {
//        boolean isSuccess = assetLabelService.removeByIds(idList);
//        if (isSuccess) {
//            return Result.ok(null);
//        } else {
//            return Result.fail(null);
//        }
//    }
}

