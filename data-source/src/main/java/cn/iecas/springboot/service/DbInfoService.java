package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.*;
import cn.iecas.springboot.dao.DbInfoDao;
import cn.iecas.springboot.dao.DbTypeDao;
import cn.iecas.springboot.dao.SourceDao;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbInfoService {
    @Autowired
    private DbInfoDao dbInfoDao;
    @Autowired
    private DbTypeDao dbTypeDao;
    @Autowired
    private SourceDao sourceDao;

//    public ApiResult<GetBean> add(GetBean getBean){
//        DbInfoBean dbInfoBean = new DbInfoBean();
//        dbInfoBean.setName(getBean.getName());
//        dbInfoBean.setUserId(getBean.getUserId());
//        dbInfoBean.setDescription(getBean.getDescription());
//        dbInfoBean.setAddress(getBean.getAddress());
//        dbInfoBean.setParameter(getBean.getParameter());
//
//        String address = getBean.getAddress();
//        String[] addresses = address.split(":");
//        String dbType = addresses[1];
//
//        dbInfoBean.setDbType(dbType);
//        dbInfoBean.setConnectUser(getBean.getConnectUser());
//        dbInfoBean.setConnectPass(getBean.getConnectPass());
//        dbInfoBean.setSysType(getBean.getSysType());
//        dbInfoDao.save(dbInfoBean);
//
//        if(!sourceDao.existsById(dbInfoBean.getSourceId())){
//            SourceBean sourceBean = new SourceBean();
//            sourceBean.setId(dbInfoBean.getSourceId());
//            sourceBean.setName(getBean.getSysType());
//            sourceDao.save(sourceBean);
//        }
//        return ApiResult.success(getBean);
//    }

    public ApiResult<BigBean> add(BigBean bigBean){
        DbInfoBean dbInfoBean = new DbInfoBean();
        BeanUtils.copyProperties(bigBean, dbInfoBean);
        dbInfoDao.save(dbInfoBean);

        if(!sourceDao.existsById(dbInfoBean.getSourceId())){
            SourceBean sourceBean = new SourceBean();
            sourceBean.setId(dbInfoBean.getSourceId());
            sourceBean.setName(dbInfoBean.getSysType());
            sourceDao.save(sourceBean);
        }
        return ApiResult.success(bigBean);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(String id){
        if(dbInfoDao.existsById(id)){
            dbInfoDao.deleteById(id);
            return ApiResult.success("删除成功");
        }else{
            return ApiResult.fail("该资源已被删除");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<BigBean> update(BigBean bigBean){
        DbInfoBean dbInfoBean = new DbInfoBean();
        BeanUtils.copyProperties(bigBean, dbInfoBean);
        if(!dbInfoDao.existsById(dbInfoBean.getId())){
            throw new BusinessException("该数据源不存在");
        }
        dbInfoDao.saveAndFlush(dbInfoBean);
        return ApiResult.success(bigBean);
    }
//    @Transactional(rollbackFor = Exception.class)
//    public ApiResult<DbInfoBean> update(DbInfoBean dbInfoBean){
//        if(!dbInfoDao.existsById(dbInfoBean.getId())){
//            throw new BusinessException("该数据源不存在");
//        }
//        dbInfoDao.saveAndFlush(dbInfoBean);
//        return ApiResult.success(dbInfoBean);
//    }

    public ApiResult<BigBean> getOne(String id){
        if(!dbInfoDao.existsById(id)){
            throw new BusinessException("该数据源不存在");
        }
        DbInfoBean dbInfoBean = dbInfoDao.getOne(id);
        SourceBean sourceBean = sourceDao.findOneSourceById(dbInfoBean.getSourceId());
        DbTypeBean dbTypeBean = dbTypeDao.findOneDbTypeBeanByType(dbInfoBean.getDbType());
        BigBean bigBean = new BigBean();
        BeanUtils.copyProperties(dbInfoBean, bigBean);
        bigBean.setDbTypeBean(dbTypeBean);
        bigBean.setSourceBean(sourceBean);
        return ApiResult.success(bigBean);
    }


    public ApiResult<PageResult<BigBean>> getList(SearchParam param){
        Example<DbInfoBean> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), DbInfoBean.class);
        Pageable pageable = PageableUtil.generatePageable(param);
        Page<DbInfoBean> dbInfoBeans = dbInfoDao.findAll(example, pageable);
        List<BigBean> BigBeans = new ArrayList<>();
        for(DbInfoBean dbInfoBean:dbInfoBeans){
            BigBean bigBean = new BigBean();
            BeanUtils.copyProperties(dbInfoBean, bigBean);
            bigBean.setSourceBean(sourceDao.findOneSourceById(dbInfoBean.getSourceId()));
            bigBean.setDbTypeBean(dbTypeDao.findOneDbTypeBeanByType(dbInfoBean.getDbType()));
            BigBeans.add(bigBean);
        }
        Page<BigBean> page = new PageImpl(BigBeans);
        return ApiResult.success(new PageResult<>(page));
    }

}
