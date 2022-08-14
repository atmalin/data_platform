package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.UserSelfBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSelfDao extends JpaRepository<UserSelfBean, String> {

    UserSelfBean findUserSelfBeanById(String id);


}
