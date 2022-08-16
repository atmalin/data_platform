package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.DiSchedulerBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author dragon
 * @since 2022/8/15 15:05
 */
public interface SchedulerDao extends JpaRepository<DiSchedulerBean, Long> {
    @Transactional
    @Modifying
    @Query("delete from DiSchedulerBean d where d.id in ?1")
    int deleteByIdIn(Collection<Long> ids);

}
