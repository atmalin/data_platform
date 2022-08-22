package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.DbInfoBean;
import cn.iecas.springboot.dao.DbInfoDao;
import cn.iecas.springboot.dao.DbTypeDao;
import cn.iecas.springboot.entity.BigEntity;
import cn.iecas.springboot.framework.result.ApiResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    @Autowired
    private DbInfoDao dbInfoDao;
    @Autowired
    private DbTypeDao dbTypeDao;

    public ApiResult<BigEntity> test(BigEntity bigEntity){
        DbInfoBean dbInfoBean = new DbInfoBean();
        BeanUtils.copyProperties(bigEntity, dbInfoBean);
        dbInfoDao.save(dbInfoBean);


        return ApiResult.success(bigEntity);
    }
}
