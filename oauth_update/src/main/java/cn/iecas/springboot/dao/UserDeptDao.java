package cn.iecas.springboot.dao;

import cn.iecas.springboot.entity.UserDeptBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeptDao extends JpaRepository<UserDeptBean, Long> {
    @Query(nativeQuery = true, value = "select distinct dept_id from tb_user_dept where user_id =:userId")
    Long findDidByUid(@Param("userId") String userId);

    void deleteAllByUserId(String userId);

//    @Query(nativeQuery = true, value = "select distinct r.code from role r " +
//            "inner join user_role ur " +
//            "on r.id = ur.role_id " +
//            "where ur.user_id =:userId")
//    List<String> findRoleCodesByUid(@Param("userId") Long userId);
}
