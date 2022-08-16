package cn.iecas.springboot.dao;

import cn.iecas.springboot.bean.DiFetchTypeBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dragon
 * @since 2022/8/15 14:58
 */
public interface TypeDao extends JpaRepository<DiFetchTypeBean, Integer> {
}
