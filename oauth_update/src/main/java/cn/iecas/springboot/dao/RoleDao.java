package cn.iecas.springboot.dao;

import cn.iecas.springboot.entity.RoleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleBean,String> {
    @Query(nativeQuery = true,value = "select distinct * from tb_role where tb_role.name=:roleName")
    RoleBean findByRoleName(@Param("roleName") String roleName);
}
