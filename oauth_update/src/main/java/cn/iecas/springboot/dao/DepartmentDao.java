package cn.iecas.springboot.dao;


import cn.iecas.springboot.entity.DepartmentBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<DepartmentBean, Long> {

//    @Query(nativeQuery = true, value = "select distinct name from tb_dept where id = :id")
//    String findBy


}
