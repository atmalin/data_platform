package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.*;
import cn.iecas.springboot.dao.*;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.exception.DaoException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import cn.iecas.springboot.param.role.UpdateRolePermissionParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.SetUtils.SetView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 系统用户 服务实现类
 * </pre>
 *
 * @author ch
 * @since 2021-10-14
 */
@Slf4j
@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private RoleDataSourceDao roleDataSourceDao;

    @Autowired
    private PermissionDao permissionDao;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<RoleBean> add(RoleBean data) {
        RoleBean exampleBean = new RoleBean();
        exampleBean.setCode(data.getCode());
        Example<RoleBean> example = Example.of(exampleBean);
        if (roleDao.exists(example)) {
            throw new BusinessException("角色编码重复");
        }
        roleDao.save(data);
        return ApiResult.success(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(Long id) {
        if (!roleDao.existsById(id)) {
            throw new BusinessException("该角色不存在");
        }
        // 判断该角色是否有可用用户，如果有，则不能删除
        UserRoleBean userRoleBean = new UserRoleBean()
                .setRoleId(id);
        Example<UserRoleBean> example = Example.of(userRoleBean);
        if (userRoleDao.exists(example)) {
            throw new DaoException("该角色被用户使用，不能删除");
        }
        RoleBean role = roleDao.getOne(id);
        if (0 == role.getType()) {
            RolePermissionBean rolePermissionBean = new RolePermissionBean()
                    .setRoleId(id);
            Example<RolePermissionBean> rolePermissionExample = Example.of(rolePermissionBean);
            if (rolePermissionDao.exists(rolePermissionExample)) {
                rolePermissionDao.deleteInBatch(rolePermissionDao.findAll(rolePermissionExample));
            }
        } else {
            RoleDataSourceBean roleDataSourceBean = new RoleDataSourceBean()
                    .setRoleId(id);
            Example<RoleDataSourceBean> roleDataSourceExample = Example.of(roleDataSourceBean);
            if (roleDataSourceDao.exists(roleDataSourceExample)) {
                roleDataSourceDao.deleteInBatch(roleDataSourceDao.findAll(roleDataSourceExample));
            }
        }
        roleDao.deleteById(id);
        return ApiResult.success("角色删除成功");
    }

    public ApiResult<RoleBean> modify(RoleBean data) {
         // 校验角色是否存在
        if (!roleDao.existsById(data.getId())) {
            throw new BusinessException("该角色不存在");
        }

        // 修改角色
        roleDao.saveAndFlush(data);
        return ApiResult.success(data);
    }


    public ApiResult<RoleBean> getOne(Long id) {
        if (!roleDao.existsById(id)) {
            throw new BusinessException("该角色不存在");
        }
        RoleBean role = roleDao.getOne(id);
        return ApiResult.success(role);
    }

    public ApiResult<PageResult<RoleBean>> getList(SearchParam param) {
        Example<RoleBean> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), RoleBean.class);
        Pageable pageable = PageableUtil.generatePageable(param);
        Page<RoleBean> roleBeans;
        if (example == null) {
            roleBeans = roleDao.findAll(pageable);
        } else {
            roleBeans = roleDao.findAll(example, pageable);
        }
        return ApiResult.success(new PageResult<>(roleBeans));
    }

    public ApiResult<String> updateRolePermission(UpdateRolePermissionParam param) {
        Long roleId = param.getRoleId();
        List<Long> permissionIds = param.getPermissionIds();
        // 校验角色是否存在
        if (!roleDao.existsById(roleId)) {
            throw new BusinessException("该角色不存在");
        }
        RoleBean role = roleDao.getOne(roleId);
        // 校验权限列表是否存在
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            if (!(permissionDao.findAllById(permissionIds).size() == permissionIds.size())) {
                throw new BusinessException("权限列表 id 匹配失败");
            }
        }

        // 获取之前的权限id集合
        RolePermissionBean exampleBean = new RolePermissionBean().setRoleId(roleId);
        Example<RolePermissionBean> example = Example.of(exampleBean);
        List<Long> beforeList = new ArrayList<>();
        rolePermissionDao.findAll(example).forEach(rolePermissionBean -> {
            beforeList.add(rolePermissionBean.getPermissionId());
        });
        // 差集计算
        // before：1,2,3,4,5,6
        // after： 1,2,3,4,7,8
        // 删除5,6 新增7,8

        Set<Long> beforeSet = new HashSet<>(beforeList);
        Set<Long> afterSet = new HashSet<>(permissionIds);
        SetView<Long> deleteSet = SetUtils.difference(beforeSet, afterSet);
        SetView<Long> addSet = SetUtils.difference(afterSet, beforeSet);
        log.debug("deleteSet = " + deleteSet);
        log.debug("addSet = " + addSet);

        if (CollectionUtils.isNotEmpty(deleteSet)) {
            // 删除角色权限映射
             rolePermissionDao.deleteByRoleIdAndPermissionIds(roleId, deleteSet);
        }
        if (CollectionUtils.isNotEmpty(addSet)) {
            // 新增角色权限映射
            addSet.forEach(id -> {
                RolePermissionBean rolePermissionBean = new RolePermissionBean()
                        .setRoleId(roleId)
                        .setPermissionId(id);
                rolePermissionDao.save(rolePermissionBean);
            });
        }
        return ApiResult.success("权限更新成功");
    }
}
