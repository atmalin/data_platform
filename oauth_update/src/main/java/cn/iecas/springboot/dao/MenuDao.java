package cn.iecas.springboot.dao;

import cn.iecas.springboot.entity.MenuBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<MenuBean,String> {

    @Query(nativeQuery = true, value = "select * from tb_menu where parent_id = :id ")
    List<MenuBean> findMenuBeansByParent_id(@Param("id") String id);

    MenuBean findMenuBeanById(String id);
}
