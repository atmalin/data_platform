package cn.iecas.springboot.asset.controller;



import cn.iecas.springboot.asset.entity.vo.AssetLabelRelVo;
import cn.iecas.springboot.asset.entity.vo.AssetLabelSetVo;

import cn.iecas.springboot.asset.service.AssetLabelRelService;
import cn.iecas.springboot.asset.service.AssetLabelSetService;
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
 * 标签集  前端控制器
 * </p>
 *
 * @author malin
 * @since 2022-08-01
 * 前端展现的第二个界面的功能
 */
@Api(tags = "资产标签集管理接口")
@RestController
@Module("model")
@RequestMapping("/asset-label-set")
@CrossOrigin(value = "*", maxAge = 3600)
public class AssetLabelSetController extends BaseController<AssetLabelSetVo, String> {

    @Autowired
    private AssetLabelSetService assetLabelSetService;

    @Autowired
    private AssetLabelRelService assetLabelRelService;

    @Override
    @PutMapping("/add")
    @ApiOperation("增加标签集")
    protected ApiResult<AssetLabelSetVo> add( AssetLabelSetVo data) {
        return assetLabelSetService.add(data);
    }

    @Override
    @DeleteMapping("/delete")
    @ApiOperation("删除标签集")
    protected ApiResult<String> delete(String id) {
        return assetLabelSetService.delete(id);
    }

    @Override
    @PutMapping("/update")
    @ApiOperation("修改标签集")
    protected ApiResult<AssetLabelSetVo> update(AssetLabelSetVo data) {
        return assetLabelSetService.update(data);
    }

    @Override
    @PostMapping("/getOne")
    @ApiOperation("根据id查标签集")
    protected ApiResult<AssetLabelSetVo> getOne(String id) {
        return assetLabelSetService.getOne(id);
    }

    @Override
    @PostMapping("/getList")
    @ApiOperation("条件查询标签集")
    protected ApiResult<PageResult<AssetLabelSetVo>> getList(SearchParam param) {
        return assetLabelSetService.getList(param);
    }


    @PutMapping("/bind")
    @ApiOperation("标签集绑定标签")
    protected ApiResult<String> bind( AssetLabelRelVo data) {
        return assetLabelRelService.bind(data);
    }

    @DeleteMapping("/unBind/one")
    @ApiOperation("标签集解除标签")
    protected ApiResult<String> unbind(String labelSetId, String labelId) {
        return assetLabelRelService.unbind(labelSetId, labelId);
    }

    @DeleteMapping("/move")
    @ApiOperation("修改标签集hot值")
    protected ApiResult<String> move(AssetLabelSetVo assetLabelSetVo) {
        return assetLabelSetService.move(assetLabelSetVo);
    }

}

