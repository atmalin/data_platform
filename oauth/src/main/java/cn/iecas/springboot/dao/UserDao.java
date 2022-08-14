package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Date;

/**
 * 系统用户 dao 层
 *
 * @author ch
 * @date 2021-09-26
 */
@Repository
public interface UserDao extends JpaRepository<UserBean, Long> {

    UserBean findUserBeanByUsername(String username);
}
