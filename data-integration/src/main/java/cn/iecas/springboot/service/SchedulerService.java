package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.DiSchedulerBean;
import cn.iecas.springboot.dao.SchedulerDao;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author dragon
 * @since 2022/8/15 15:44
 */
@Slf4j
@Service
public class SchedulerService {

    @Autowired
    private SchedulerDao schedulerDao;

    public ApiResult<DiSchedulerBean> add(DiSchedulerBean data) {
        DiSchedulerBean diSchedulerBean = schedulerDao.save(data);
        return ApiResult.success(diSchedulerBean);
    }

    public ApiResult<String> delete(Long id) {
        if (schedulerDao.existsById(id)) {
            schedulerDao.deleteById(id);
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.fail("该资源已被删除");
        }
    }

    @Transactional
    public ApiResult<String> batchDelete(List<Long> schedulerIdList) {
        int res = schedulerDao.deleteByIdIn(schedulerIdList);
        log.info("result of batchDelete: " + res);
        if(res == 1){
            return ApiResult.success("成功");
        }else{
            return ApiResult.fail("失败");
        }
    }

    public ApiResult<DiSchedulerBean> update(DiSchedulerBean data, Long schedulerId) {
        DiSchedulerBean bookBean = schedulerDao.saveAndFlush(data);
        return ApiResult.success(bookBean);
    }

    public ApiResult<DiSchedulerBean> getOne(Long id) {
        if (schedulerDao.existsById(id)) {
            DiSchedulerBean bookBean = schedulerDao.getOne(id);
            return ApiResult.success(bookBean);
        } else {
            return ApiResult.fail("该资源不存在");
        }
    }

    public ApiResult<PageResult<DiSchedulerBean>> getList(SearchParam param) {
        Example<DiSchedulerBean> bookBeanExample = ExampleUtil.generateExampleWithDefaultIgnoreProps(param.getQueryCondition(), DiSchedulerBean.class);
        Pageable pageable = PageableUtil.generatePageable(param);
        Page<DiSchedulerBean> bookBeans = schedulerDao.findAll(bookBeanExample, pageable);
        return ApiResult.success(new PageResult<>(bookBeans));
    }
}
