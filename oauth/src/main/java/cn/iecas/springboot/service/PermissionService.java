package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.PermissionBean;
import cn.iecas.springboot.bean.RolePermissionBean;
import cn.iecas.springboot.component.PermissionComponent;
import cn.iecas.springboot.dao.PermissionDao;
import cn.iecas.springboot.dao.RolePermissionDao;
import cn.iecas.springboot.enums.MenuLevelEnum;
import cn.iecas.springboot.framework.common.enums.StatusEnum;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import cn.iecas.springboot.vo.PermissionTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 系统权限 服务实现类
 * </pre>
 *
 * @author ch
 * @since 2021-10-14
 */
@Slf4j
@Service
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private PermissionComponent permissionComponent;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<PermissionBean> add(PermissionBean data) {
        permissionDao.save(data);
        return ApiResult.success(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(Long id) {
        RolePermissionBean exampleBean = new RolePermissionBean()
                .setPermissionId(id);
        Example<RolePermissionBean> example = Example.of(exampleBean);
        if (rolePermissionDao.exists(example)) {
            throw new BusinessException("该权限存在角色关联关系，不能删除");
        }
        permissionDao.deleteById(id);
        return ApiResult.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<PermissionBean> modify(PermissionBean data) {
        if (!permissionDao.existsById(data.getId())) {
            throw new BusinessException("权限不存在");
        }
        permissionDao.saveAndFlush(data);
        return ApiResult.success(data);
    }

    public ApiResult<PermissionBean> getOne(Long id) {
        if (!permissionDao.existsById(id)) {
            throw new BusinessException("权限不存在");
        }
        PermissionBean permissionBean = permissionDao.getOne(id);
        return ApiResult.success(permissionBean);
    }


    public ApiResult<PageResult<PermissionBean>> getList(SearchParam param) {
        Example<PermissionBean> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), PermissionBean.class);
        Pageable pageable = PageableUtil.generatePageable(param);
        Page<PermissionBean> permissionBeans;
        if (example == null) {
            permissionBeans = permissionDao.findAll(pageable);
        } else {
            permissionBeans = permissionDao.findAll(example, pageable);
        }
        return ApiResult.success(new PageResult<>(permissionBeans));
    }

    public ApiResult<List<PermissionBean>> getAllList() {
        PermissionBean exampleBean = new PermissionBean()
                .setState(StatusEnum.ENABLE.getCode());
        Example<PermissionBean> example = Example.of(exampleBean);
        List<PermissionBean> permissionBeans = permissionDao.findAll(example);
        return ApiResult.success(permissionBeans);
    }

    public ApiResult<List<PermissionTreeVo>> getAllTree() {
        PermissionBean exampleBean = new PermissionBean()
                .setState(StatusEnum.ENABLE.getCode());
        Example<PermissionBean> example = Example.of(exampleBean);
        List<PermissionBean> permissionBeans = permissionDao.findAll(example);
        List<PermissionTreeVo> treeVos = permissionComponent.convertPermissionTreeVoList(permissionBeans);
        return ApiResult.success(treeVos);
    }

    public ApiResult<List<PermissionBean>> getMenuListByUserId(Long uid) {
        List<PermissionBean> permissionBeans = permissionDao.findPermissionBeansByUserId(uid);
        return ApiResult.success(permissionBeans);
    }

    public ApiResult<List<PermissionTreeVo>> getMenuTreeByUserId(Long uid) {
        List<PermissionBean> permissionBeans = permissionDao.findPermissionBeansByUserId(uid);
        // 转换成树形菜单列表
        List<PermissionTreeVo> treeVos = permissionComponent.convertPermissionTreeVoList(permissionBeans);
        return ApiResult.success(treeVos);
    }


    public ApiResult<List<Long>> getThreeLevelPermissionIdsByRoleId(Long roleId) {
        List<Long> permissionIds = permissionDao.findThreeLevelPermissionIdsByRoleId(roleId);
        return ApiResult.success(permissionIds);
    }

    public ApiResult<List<PermissionTreeVo>> getNavMenuTree() {
        List<Integer> levels = Arrays.asList(MenuLevelEnum.ONE.getCode(), MenuLevelEnum.TWO.getCode());
        List<PermissionBean> permissions = permissionDao.findAllByLevelInAndStateEquals(levels, 1);
        List<PermissionTreeVo> treeVos = permissionComponent.convertPermissionTreeVoList(permissions);
        return ApiResult.success(treeVos);
    }
}
