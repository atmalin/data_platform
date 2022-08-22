package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.DbInfoBean;
import cn.iecas.springboot.dao.DbInfoDao;
import cn.iecas.springboot.dao.DbTypeDao;
import cn.iecas.springboot.dao.SourceDao;
import cn.iecas.springboot.entity.AddEntity;
import cn.iecas.springboot.entity.BigEntity;
import cn.iecas.springboot.entity.UpdateEntity;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbService {
    @Autowired
    private DbInfoDao dbInfoDao;
    @Autowired
    private DbTypeDao dbTypeDao;
    @Autowired
    private SourceDao sourceDao;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<Boolean> add(AddEntity data){
        DbInfoBean dbInfoBean = new DbInfoBean();
        BeanUtils.copyProperties(data, dbInfoBean);
        dbInfoBean.setSysType(sourceDao.findNameById(data.getSourceId()));
        dbInfoBean.setParameter("1");
        dbInfoBean.setUserId("001");
        dbInfoBean.setSysType("0");
        dbInfoDao.save(dbInfoBean);
        return ApiResult.success(true, "成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(String id) {
        if(dbInfoDao.existsById(id)){
            dbInfoDao.deleteById(id);
            return ApiResult.success("成功");
        }else{
            return ApiResult.fail("该资源已被删除");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<Boolean> modify(UpdateEntity data) {
        DbInfoBean dbInfoBean = new DbInfoBean();
        BeanUtils.copyProperties(data, dbInfoBean);
        if(!dbInfoDao.existsById(dbInfoBean.getId())){
            throw new BusinessException("该数据源不存在");
        }
        dbInfoBean.setSysType(sourceDao.findNameById(data.getSourceId()));
        dbInfoBean.setParameter("1");
        dbInfoBean.setUserId("001");
        dbInfoBean.setSysType("0");
        dbInfoDao.save(dbInfoBean);
        return ApiResult.success(true, "成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<PageResult<BigEntity>> getList(SearchParam param){
        Example<DbInfoBean> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), DbInfoBean.class);
        Pageable pageable = PageableUtil.generatePageable(param);
        Page<DbInfoBean> dbInfoBeans = dbInfoDao.findAll(example, pageable);
//        if (example == null) {
//            dbInfoBeans = dbInfoDao.findAll(pageable);
//        } else {
//            dbInfoBeans = dbInfoDao.findAll(example, pageable);
//        }
        List<BigEntity> bigEntities = new ArrayList<>();
        for(DbInfoBean dbInfoBean:dbInfoBeans){
            BigEntity bigEntity = new BigEntity();
            BeanUtils.copyProperties(dbInfoBean, bigEntity);
            bigEntity.setSourceBean(sourceDao.findOneSourceById(dbInfoBean.getSourceId()));
            bigEntity.setDbTypeBean(dbTypeDao.findOneDbTypeBeanByType(dbInfoBean.getDbType()));
            bigEntities.add(bigEntity);
        }
        Page<BigEntity> page = new PageImpl(bigEntities);
        return ApiResult.success(new PageResult<>(page), "成功");
    }
}
