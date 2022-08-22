package cn.iecas.springboot.service;

import cn.iecas.springboot.dao.ButtonDao;
import cn.iecas.springboot.dao.MenuDao;
import cn.iecas.springboot.dao.RolePermissionDao;
import cn.iecas.springboot.entity.ButtonBean;
import cn.iecas.springboot.entity.MenuBean;
import cn.iecas.springboot.entity.RolePermissionBean;
import cn.iecas.springboot.entity.dataBean.MenuVo;
import cn.iecas.springboot.entity.dataBean.UserAdd;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MenuService {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private ButtonDao buttonDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<MenuBean> add(MenuBean menuBean){
        menuDao.save(menuBean);
        return ApiResult.success(menuBean);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<List<MenuVo>> list(){
        List<MenuBean> menuBeans = menuDao.findAll();
        List<MenuVo> ResMenuBeans = new ArrayList<>();
        for (MenuBean menuBean:
             menuBeans) {
            if (menuBean.getParent_id()!=null) continue;
            List<MenuBean> childrenIds = menuDao.findMenuBeansByParent_id(menuBean.getId());
            List<ButtonBean> buttonIds = buttonDao.findButtonBeansByMenu_id(menuBean.getId());
            MenuVo menuVo = new MenuVo().setId(menuBean.getId())
                    .setName(menuBean.getName())
                    .setEn_name(menuBean.getEn_name())
                    .setClient_id(menuBean.getClient_id())
                    .setDescription(menuBean.getDescription())
                    .setIcon(menuBean.getIcon())
                    .setRoute_url(menuBean.getRoute_url())
                    .setParent_id(menuBean.getParent_id())
                    .setSort(menuBean.getSort())
                    .setUrl(menuBean.getUrl())
                    .setChildren(childrenIds)
                    .setButtonBeans(buttonIds);
            ResMenuBeans.add(menuVo);
        }
        return ApiResult.success(ResMenuBeans);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(String id){
        if (menuDao.findMenuBeanById(id) == null){
            throw new BusinessException("菜单不存在");
        }
        //若菜单被角色占用 则不能删除
        if (rolePermissionDao.findRolePermissionBeanByMenuId(id) != null){
            throw new BusinessException("菜单被角色占用，无法删除");
        }

        menuDao.deleteById(id);

        //同时删除菜单关联的button表
        buttonDao.deleteByMenu_id(id);
        return ApiResult.success("删除成功");
    }
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<MenuBean> update(MenuBean menuBean){
        //更改时 需要set 更改时间
        menuDao.saveAndFlush(menuBean);
        return ApiResult.success(menuBean);
    }

}
