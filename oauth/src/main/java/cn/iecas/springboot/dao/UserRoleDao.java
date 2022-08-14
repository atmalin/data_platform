package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.UserRoleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao extends JpaRepository<UserRoleBean, Long> {
    @Query(nativeQuery = true, value = "select distinct rid from user_role where user_id =:userId")
    List<Long> findRidListByUid(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select distinct r.code from role r " +
            "inner join user_role ur " +
            "on r.id = ur.role_id " +
            "where ur.user_id =:userId")
    List<String> findRoleCodesByUid(@Param("userId") Long userId);
}
