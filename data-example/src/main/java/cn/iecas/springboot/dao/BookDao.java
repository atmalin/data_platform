package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.BookBean;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends JpaRepository<BookBean, String> {

    BookBean findBookBeanById(String id);

    @Query(nativeQuery = true, value = "select name_cn from book where id =:id and name_cn = :name")
    String findNameCnById(@Param("id") String id, @Param("name") String name);
}
