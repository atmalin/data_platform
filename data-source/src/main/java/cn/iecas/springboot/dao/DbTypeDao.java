package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.DbTypeBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DbTypeDao extends JpaRepository<DbTypeBean, String> {
    @Query(nativeQuery = true, value = "select * from db_type where type = :type")
    DbTypeBean findOneDbTypeBeanByType(@Param("type") String type);

    @Query(nativeQuery = true,
            value = "select * from db_type where type in (select db_type from db_info where name = :name)")
    List<DbTypeBean> findDbTypeBeanByDbType(@Param("name") String name);
}
