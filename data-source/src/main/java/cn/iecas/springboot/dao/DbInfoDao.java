package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.DbInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbInfoDao extends JpaRepository<DbInfoBean, String> {
    @Query(nativeQuery = true,
            value = "select * from db_info where name like %:name%")
//    address, connnect_pass, connect_user, create_time, db_type, description, id, name, source_id, sys_type, update_time from db_info where name = :name
    List<DbInfoBean> findDbInfoBeanByNameLike(@Param("name") String name);

    Boolean existsByName(String name);

}
