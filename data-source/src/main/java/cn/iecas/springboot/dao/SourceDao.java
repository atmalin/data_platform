package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.SourceBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceDao extends JpaRepository<SourceBean, String> {
    @Query(nativeQuery = true, value = "select * from source where id = :id")
    SourceBean findOneSourceById(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from source where id in (select source_id from db_info where name = :name)")
    List<SourceBean> findSourceDaoById(@Param("name") String name);
}
