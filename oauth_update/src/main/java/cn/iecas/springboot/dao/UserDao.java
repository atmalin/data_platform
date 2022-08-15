package cn.iecas.springboot.dao;


import cn.iecas.springboot.entity.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<UserBean,String> {

    UserBean findUserBeanByUsername(String username);

    void deleteAllById(String Id);

    @Query(nativeQuery = true, value = "select * from tb_user where name like %:name% ")
    List<UserBean> findUserBeanByName(@Param("name") String name);

//    UserBean findUserBeanByNickname(String nickname);
}
