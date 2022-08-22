package cn.iecas.springboot.dao;

import cn.iecas.springboot.entity.ButtonBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ButtonDao extends JpaRepository<ButtonBean,Integer> {

    @Query(nativeQuery = true, value = "select * from tb_button where id = :menu_id ")
    List<ButtonBean> findButtonBeansByMenu_id(@Param("menu_id") String menu_id);

    

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from tb_button where menu_id = :menu_id")
    void deleteByMenu_id(@Param("menu_id") String menu_id);
}
