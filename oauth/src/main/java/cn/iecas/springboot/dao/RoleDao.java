package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.RoleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleBean, Long> {
}
