package cn.iecas.springboot.dao;


import cn.iecas.springboot.entity.PermissionBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao extends JpaRepository<PermissionBean, Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT tm.name FROM ((tb_user u INNER JOIN tb_user_role ur ON u.id=ur.user_id) INNER JOIN tb_role r ON ur.role_id=r.id) INNER JOIN tb_role_menu trm ON r.id=trm.role_id INNER JOIN tb_menu tm ON trm.menu_id=tm.id WHERE u.id=:user_id")
    List<String> findPermissionByUserId(@Param("user_id") String user_id);

//    @Query(nativeQuery = true, value = "select distinct p.id from user u inner join user_role ur on u.id = ur.user_id " +
//            "inner join role r on ur.role_id = r.id " +
//            "inner join role_permission rp on r.id = rp.role_id " +
//            "inner join permission p on rp.permission_id = p.id " +
//            "where u.state = 1 " +
//            "and r.state = 1 " +
//            "and p.state = 1 " +
//            "and u.id = :userId")
//    List<Long> findPermissionIdsByUserId(@Param("userId") Long userId);
//
//    @Query(nativeQuery = true, value = "select distinct p.code from user u inner join user_role ur on u.id = ur.user_id " +
//            "inner join role r on ur.role_id = r.id " +
//            "inner join role_permission rp on r.id = rp.role_id " +
//            "inner join permission p on rp.permission_id = p.id " +
//            "where u.state = 1 " +
//            "and r.state = 1 " +
//            "and p.state = 1 " +
//            "and u.id = :userId")
//    List<String> findPermissionCodesByUserId(@Param("userId") Long userId);
//
//    @Query(nativeQuery = true, value = "select distinct p.id " +
//            "from role r " +
//            "inner join role_permission rp on r.id = rp.role_id " +
//            "inner join permission p on rp.permission_id = p.id " +
//            "where r.id = :roleId and p.level = 3 and r.state = 1 and rp.state = 1 and p.state = 1")
//    List<Long> findThreeLevelPermissionIdsByRoleId(Long roleId);
//
//    List<PermissionBean> findAllByLevelInAndStateEquals(List<Integer> levels, Integer state);
}
