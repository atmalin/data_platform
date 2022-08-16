package cn.iecas.springboot.asset.dao;


import cn.iecas.springboot.asset.entity.AssetLabel;
import cn.iecas.springboot.asset.entity.AssetLabelSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 * 标签集  在这个接口中使用JPA的技术完成数据库的增删改查操作
 * JpaRepository<T, ID>解释说明
 *      T：表示操作的实体类是谁（在实体类上配置了和表的映射关系）
 *      ID：表示操作的实体类中对应哪个主键的数据类型
 * </p>
 *
 * @author malin
 * @since 2022-08-01
 * nativeQuery = true :设置sql语句是否是原生sql
 */
@Repository
public interface AssetLabelDao extends JpaRepository<AssetLabel, String> {
//    //@Query增、删、改、查操作
//    @Query(value = "select label_id from asset_label_rel where label_set_id =:labelId", nativeQuery = true)
//    List<String> getAssetLabelSetById(@Param("labelId") String labelId);
//
//    @Transactional
//    @Modifying //删除和修改必须添加此注解
//    @Query(value = "update asset_label_set c set c.hot=?1 where c.id = ?2", nativeQuery = true)
//    AssetLabelSet getAssetLabelSetById(Integer hot, String id);
//
//    @Transactional
//    @Modifying
//    @Query("delete from asset_label_set c where c.id=?1")
//    int deleteAssetLabelSetById(String id);

    //增加的方法用自带的方法save

}
