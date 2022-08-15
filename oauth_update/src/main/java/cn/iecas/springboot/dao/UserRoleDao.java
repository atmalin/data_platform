package cn.iecas.springboot.dao;

import cn.iecas.springboot.entity.UserRoleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao extends JpaRepository <UserRoleBean, Long>{

    @Query(nativeQuery = true, value = "select distinct role_id from tb_user_role where user_id =:userId ")
    List<String> findU_R_idListByUid(@Param("userId") String userId);


    void deleteAllByUserId(@Param("userId") String userId);
}
