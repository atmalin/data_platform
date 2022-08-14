package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.RoleDataSourceBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDataSourceDao extends JpaRepository<RoleDataSourceBean, Long> {

}
