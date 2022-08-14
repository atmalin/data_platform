package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.DepartmentBean;
import cn.iecas.springboot.convert.DepartmentConvert;
import cn.iecas.springboot.dao.DepartmentDao;
import cn.iecas.springboot.framework.common.enums.StatusEnum;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import cn.iecas.springboot.vo.DepartmentTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 部门 服务实现类
 * </pre>
 *
 * @author ch
 * @since 2021-10-23
 */
@Slf4j
@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<DepartmentBean> addDepartment(DepartmentBean departmentBean) {
        departmentDao.save(departmentBean);
        return ApiResult.success(departmentBean);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(Long id) {
        if (!departmentDao.existsById(id)) {
            throw new BusinessException("部门不存在");
        }
        departmentDao.deleteById(id);
        return ApiResult.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<DepartmentBean> modify(DepartmentBean data) {
        departmentDao.saveAndFlush(data);
        return ApiResult.success(data);
    }

    public ApiResult<DepartmentBean> getOne(Long id) {
        if (!departmentDao.existsById(id)) {
            throw new BusinessException("部门不存在");
        }
        DepartmentBean department = departmentDao.getOne(id);
        return ApiResult.success(department);
    }

    public ApiResult<PageResult<DepartmentBean>> list(SearchParam param) {
        Example<DepartmentBean> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), DepartmentBean.class);
        Pageable pageable = PageableUtil.generatePageable(param);
        Page<DepartmentBean> departmentBeans;
        if (example == null) {
            departmentBeans = departmentDao.findAll(pageable);
        } else {
            departmentBeans = departmentDao.findAll(example, pageable);
        }
        return ApiResult.success(new PageResult<>(departmentBeans));
    }

    public ApiResult<List<DepartmentBean>> listAll() {
        DepartmentBean departmentBean = new DepartmentBean().setState(StatusEnum.ENABLE.getCode());
        Example<DepartmentBean> example = Example.of(departmentBean);
        List<DepartmentBean> departmentBeans = departmentDao.findAll(example);
        return ApiResult.success(departmentBeans);
    }

    public ApiResult<List<DepartmentTreeVo>> getDepartmentTree() {
        List<DepartmentBean> departmentBeans = departmentDao.findAll();
        if (CollectionUtils.isEmpty(departmentBeans)) {
            throw new BusinessException("department 列表为空");
        }
        List<DepartmentTreeVo> list = DepartmentConvert.INSTANCE.listToTreeVoList(departmentBeans);
        List<DepartmentTreeVo> treeVos = new ArrayList<>();
        for (DepartmentTreeVo treeVo : treeVos) {
            if (treeVo.getPid() == null) {
                treeVos.add(findChildren(treeVo, list));
            }
        }
        return ApiResult.success(treeVos);
    }

    /**
     * 递归获取树形结果列表
     * @param tree
     * @param list
     * @return
     */
    private DepartmentTreeVo findChildren(DepartmentTreeVo tree, List<DepartmentTreeVo> list) {
        for (DepartmentTreeVo vo : list) {
            if (tree.getId().equals(vo.getPid())) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList<>());
                }
                tree.getChildren().add(findChildren(vo, list));
            }
        }
        return tree;
    }
}
