package cn.iecas.springboot.dao;

import cn.iecas.springboot.entity.RolePermissionBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface RolePermissionDao extends JpaRepository<RolePermissionBean, Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from role_permission where role_id =:roleId and permission_id in (:permissionIds)")
    void deleteByRoleIdAndPermissionIds(@Param("roleId") Long roleId, @Param("permissionIds") Set<Long> permissionIds);

    RolePermissionBean findRolePermissionBeanByMenuId(String menuId);
}
