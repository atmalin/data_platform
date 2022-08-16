package cn.iecas.springboot.asset.service;

import cn.iecas.springboot.asset.dao.AssetLabelDao;
import cn.iecas.springboot.asset.dao.AssetLabelRelDao;
import cn.iecas.springboot.asset.entity.AssetLabel;
import cn.iecas.springboot.asset.entity.AssetLabelRel;
import cn.iecas.springboot.asset.entity.AssetLabelSet;
import cn.iecas.springboot.asset.entity.vo.AssetLabelRelVo;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签集  服务类
 * </p>
 *
 * @author malin
 * @since 2022-08-01
 */
@Service
public class AssetLabelRelService {

    @Autowired
    private AssetLabelRelDao assetLabelRelDao;


    public ApiResult<String> bind(AssetLabelRelVo data) {
        List<String> listIds = data.getLabelList();
        String labelSetId = data.getLabelSetId();
        for(String labelId : listIds){
            AssetLabelRel assetLabelRel = new AssetLabelRel();
            assetLabelRel.setLabelId(labelId);
            assetLabelRel.setLabelSetId(labelSetId);
            AssetLabelRel save = assetLabelRelDao.save(assetLabelRel);
            if(save == null) return ApiResult.fail("失败");
        }
        return ApiResult.success("成功");
    }

    public ApiResult<String> unbind(String labelSetId, String labelId) {

        int count = assetLabelRelDao.unbind(labelSetId, labelId);
        if (count != 0) {
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.fail("该资源已被删除");
        }
    }

}

