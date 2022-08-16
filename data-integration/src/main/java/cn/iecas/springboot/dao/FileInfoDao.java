package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.FileInfoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dragon
 * @since 2022/8/16 10:46
 */
public interface FileInfoDao extends JpaRepository<FileInfoBean, String> {
    @Query("select (count(f) > 0) from FileInfoBean f where f.name = ?1 and f.type = ?2")
    boolean existsByNameAndType(String name, Integer type);


}
