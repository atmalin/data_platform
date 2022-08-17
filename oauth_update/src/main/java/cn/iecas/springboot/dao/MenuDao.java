package cn.iecas.springboot.dao;

import cn.iecas.springboot.entity.MenuBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<MenuBean,String> {

    @Query(nativeQuery = true, value = "select * from tb_menu where id = :parent_id ")
    List<MenuBean> findMenuBeansByParent_id(@Param("parent_id") String parent_id);
}
