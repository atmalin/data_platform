package cn.iecas.springboot.component;

import cn.iecas.springboot.bean.PermissionBean;
import cn.iecas.springboot.convert.PermissionConvert;
import cn.iecas.springboot.enums.MenuLevelEnum;
import cn.iecas.springboot.vo.PermissionTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限列表转换组件
 *
 * @author ch
 * @since 2021-10-14
 */
@Slf4j
@Component
public class PermissionComponent {

    public List<PermissionTreeVo> convertPermissionTreeVoList(List<PermissionBean> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("SysPermission列表不能为空");
        }
        // 按level分组获取map
        Map<Integer, List<PermissionBean>> map = list.stream().collect(Collectors.groupingBy(PermissionBean::getLevel));
        List<PermissionTreeVo> treeVos = new ArrayList<>();
        // 循环获取三级菜单树形集合
        for (PermissionBean one : map.get(MenuLevelEnum.ONE.getCode())) {
            PermissionTreeVo oneVo = PermissionConvert.INSTANCE.entityToTreeVo(one);
            Long oneParentId = oneVo.getParentId();
            if (oneParentId == null || oneParentId == 0) {
                treeVos.add(oneVo);
            }
            List<PermissionBean> twoList = map.get(MenuLevelEnum.TWO.getCode());
            if (CollectionUtils.isNotEmpty(twoList)) {
                for (PermissionBean two : twoList) {
                    PermissionTreeVo twoVo = PermissionConvert.INSTANCE.entityToTreeVo(two);
                    if (two.getPid().equals(one.getId())) {
                        if (oneVo.getChildren() == null) {
                            oneVo.setChildren(new ArrayList<>());
                        }
                        oneVo.getChildren().add(twoVo);
                    }
                    List<PermissionBean> threeList = map.get(MenuLevelEnum.THREE.getCode());
                    if (CollectionUtils.isNotEmpty(threeList)) {
                        for (PermissionBean three : threeList) {
                            if (three.getPid().equals(two.getId())) {
                                PermissionTreeVo threeVo = PermissionConvert.INSTANCE.entityToTreeVo(three);
                                if (twoVo.getChildren() == null) {
                                    twoVo.setChildren(new ArrayList<>());
                                }
                                twoVo.getChildren().add(threeVo);
                            }
                        }
                    }
                }
            }

        }
        return treeVos;
    }
}
