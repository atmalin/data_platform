package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.DepartmentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<DepartmentBean, Long> {

}
