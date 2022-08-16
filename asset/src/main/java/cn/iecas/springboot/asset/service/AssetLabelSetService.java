package cn.iecas.springboot.asset.service;

import cn.iecas.springboot.asset.dao.AssetLabelDao;
import cn.iecas.springboot.asset.dao.AssetLabelRelDao;
import cn.iecas.springboot.asset.dao.AssetLabelSetDao;
import cn.iecas.springboot.asset.entity.AssetLabel;
import cn.iecas.springboot.asset.entity.AssetLabelSet;


import cn.iecas.springboot.asset.entity.vo.AssetLabelSetVo;
import cn.iecas.springboot.asset.entity.vo.AssetLabelVo;

import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 标签集  服务类
 * </p>
 *
 * @author malin
 * @since 2022-08-01
 */
@Service
public class AssetLabelSetService {

    @Autowired
    private AssetLabelSetDao assetLabelSetDao;

    @Autowired
    private AssetLabelDao assetLabelDao;

    @Autowired
    private AssetLabelRelDao assetLabelRelDao;

    public ApiResult<AssetLabelSetVo> add(AssetLabelSetVo assetLabelSetVo) {
        AssetLabelSet data = new AssetLabelSet();
        BeanUtils.copyProperties(assetLabelSetVo, data);
        data.setHot(1000000);
        AssetLabelSet assetLabelSet = assetLabelSetDao.save(data);
        BeanUtils.copyProperties(assetLabelSet, assetLabelSetVo);
        return ApiResult.success(assetLabelSetVo);
    }

    public ApiResult<String> delete(String id) {
        if (assetLabelSetDao.existsById(id)) {
            assetLabelSetDao.deleteById(id);
            assetLabelRelDao.deleteByLabelSetId(id);
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.fail("该资源已被删除");
        }
    }

    public ApiResult<AssetLabelSetVo> update(AssetLabelSetVo assetLabelSetVo) {
        AssetLabelSet data = new AssetLabelSet();
        BeanUtils.copyProperties(assetLabelSetVo, data);
        AssetLabelSet assetLabelSet = assetLabelSetDao.saveAndFlush(data);
        BeanUtils.copyProperties(assetLabelSet, assetLabelSetVo);
        return ApiResult.success(assetLabelSetVo);
    }

    public ApiResult<AssetLabelSetVo> getOne(String id) {
        if (assetLabelSetDao.existsById(id)) {
            AssetLabelSet assetLabelSet = assetLabelSetDao.getOne(id);
            AssetLabelSetVo data = new AssetLabelSetVo();
            BeanUtils.copyProperties(assetLabelSet, data);
            return ApiResult.success(data);
        } else {
            return ApiResult.fail("该资源不存在");
        }
    }

    public ApiResult<String> move(AssetLabelSetVo assetLabelSetVo) {
        String id = assetLabelSetVo.getId();
        Integer hot = assetLabelSetVo.getHot();
        AssetLabelSet assetLabelSetNew = assetLabelSetDao.getOne(id);
        assetLabelSetNew.setHot(hot);

//        AssetLabelSet assetLabelSetExa = new AssetLabelSet();
//        assetLabelSetExa.setHot(hot);
//        Example<AssetLabelSet> example = Example.of(assetLabelSetExa,
//                ExampleMatcher.matching()
//                        .withMatcher("hot", ExampleMatcher.GenericPropertyMatcher::exact));
//
//        List<AssetLabelSet> all = assetLabelSetDao.findAll(example);
//        if (all != null) {
//            for(AssetLabelSet assetLabelSet : all){
//                assetLabelSet.setHot(hot + 1);
//                assetLabelSetDao.save(assetLabelSet);
//            }
//        }
        AssetLabelSet save = assetLabelSetDao.save(assetLabelSetNew);
        if(save != null){
            return ApiResult.success("成功");
        }else{
            return ApiResult.fail("失败");
        }
    }

    public ApiResult<PageResult<AssetLabelSetVo>> getList(SearchParam param) {
//        Example<AssetLabelSet> assetLabelSetExample = ExampleUtil.generateExampleWithDefaultIgnoreProps(param.getQueryCondition(), AssetLabelSet.class);

//        AssetLabelSet assetLabelSet = new AssetLabelSet();
//        assetLabelSet.setNameCn("钢铁是怎么炼成的");
//        Example<BookBean> example = Example.of(assetLabelSet,
//                ExampleMatcher.matching().withMatcher("name_cn", ExampleMatcher.GenericPropertyMatcher::exact));

        List<AssetLabelSetVo> assetLabelSetVos = new ArrayList<>();
//        List<AssetLabelSet> list = assetLabelSetDao.findAll();

        Example<AssetLabelSet> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), AssetLabelSet.class);
        Pageable pageable = PageableUtil.generatePageable(param);

        Page<AssetLabelSet> list;
        if (example == null) {
            list = assetLabelSetDao.findAll(pageable);
        } else {
            list = assetLabelSetDao.findAll(example, pageable);
        }
        list.stream().forEach(item -> {
            AssetLabelSetVo assetLabelSetVo = this.getLabelVos(item);
            assetLabelSetVos.add(assetLabelSetVo);
        });
        Page<AssetLabelSetVo> simplePage = new PageImpl(assetLabelSetVos);
        return ApiResult.success(new PageResult<>(simplePage));
    }

    private AssetLabelSetVo getLabelVos(AssetLabelSet assetLabelSet) {
        AssetLabelSetVo assetLabelSetVo = new AssetLabelSetVo();
        BeanUtils.copyProperties(assetLabelSet, assetLabelSetVo);

        String labelSetId = assetLabelSet.getId();
        List<String> labelIds = assetLabelSetDao.getAssetLabelSetById(labelSetId);
        if (labelIds != null) {
            List<AssetLabel> allLabelOfSet = assetLabelDao.findAllById(labelIds);
            List<AssetLabelVo> assetLabelVos = new ArrayList<>();
            for (AssetLabel assetLabelofSet : allLabelOfSet) {
                AssetLabelVo assetLabelVo = new AssetLabelVo();
                BeanUtils.copyProperties(assetLabelofSet, assetLabelVo);
                assetLabelVos.add(assetLabelVo);
            }
            assetLabelSetVo.setLabelList(assetLabelVos);
        }
        return assetLabelSetVo;
    }
}

