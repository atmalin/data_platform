package cn.iecas.springboot.asset.service;

import cn.iecas.springboot.asset.dao.AssetLabelDao;
import cn.iecas.springboot.asset.entity.AssetLabel;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签集  服务类
 * </p>
 *
 * @author malin
 * @since 2022-08-01
 */
@Service
public class AssetLabelService {

    @Autowired
    private AssetLabelDao assetLabelDao;

    public ApiResult<AssetLabel> add(AssetLabel data) {
        AssetLabel assetLabel = assetLabelDao.save(data);
        return ApiResult.success(assetLabel);
    }

    public ApiResult<String> delete(String id) {
        if (assetLabelDao.existsById(id)) {
            assetLabelDao.deleteById(id);
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.fail("该资源已被删除");
        }
    }

    public ApiResult<AssetLabel> update(AssetLabel data) {
        AssetLabel assetLabel = assetLabelDao.saveAndFlush(data);
        return ApiResult.success(assetLabel);
    }

    public ApiResult<AssetLabel> getOne(String id) {
        if (assetLabelDao.existsById(id)) {
            AssetLabel assetLabel = assetLabelDao.getOne(id);
            return ApiResult.success(assetLabel);
        } else {
            return ApiResult.fail("该资源不存在");
        }
    }

    public ApiResult<PageResult<AssetLabel>> getList(SearchParam param) {
        Example<AssetLabel> assetLabelExample = ExampleUtil.generateExampleWithDefaultIgnoreProps(param.getQueryCondition(), AssetLabel.class);

//        AssetLabel bookBean = new BookBean();
//        bookBean.setNameCn("钢铁是怎么炼成的");
//        Example<BookBean> example = Example.of(bookBean,
//                ExampleMatcher.matching().withMatcher("name_cn", ExampleMatcher.GenericPropertyMatcher::exact));


        Pageable pageable = PageableUtil.generatePageable(param);
        Page<AssetLabel> assetLabels;
        if (assetLabelExample == null) {
            assetLabels = assetLabelDao.findAll(pageable);
        } else {
            assetLabels = assetLabelDao.findAll(assetLabelExample, pageable);
        }
        return ApiResult.success(new PageResult<>(assetLabels));
    }
}

