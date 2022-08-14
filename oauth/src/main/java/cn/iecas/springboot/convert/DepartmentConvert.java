package cn.iecas.springboot.convert;

import cn.iecas.springboot.bean.DepartmentBean;
import cn.iecas.springboot.vo.DepartmentTreeVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 部门对象属性转换器
 *
 * @author ch
 * @date 2021-10-13
 **/
@Mapper
public interface DepartmentConvert {

    DepartmentConvert INSTANCE = Mappers.getMapper(DepartmentConvert.class);

    /**
     * Department转换成DepartmentTreeVo对象
     *
     * @param department
     * @return
     */
    DepartmentTreeVo entityToTreeVo(DepartmentBean department);

    /**
     * Department列表转换成DepartmentTreeVo列表
     *
     * @param list
     * @return
     */
    List<DepartmentTreeVo> listToTreeVoList(List<DepartmentBean> list);

}
