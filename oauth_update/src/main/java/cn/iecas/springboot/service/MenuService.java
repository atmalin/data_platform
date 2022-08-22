package cn.iecas.springboot.service;

import cn.iecas.springboot.dao.ButtonDao;
import cn.iecas.springboot.dao.MenuDao;
import cn.iecas.springboot.entity.ButtonBean;
import cn.iecas.springboot.entity.MenuBean;
import cn.iecas.springboot.entity.dataBean.MenuVo;
import cn.iecas.springboot.entity.dataBean.UserAdd;
import cn.iecas.springboot.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<MenuBean> add(MenuBean menuBean){
        menuDao.save(menuBean);
        return ApiResult.success(menuBean);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<List<MenuBean>> list(){
        List<MenuBean> menuBeans = menuDao.findAll();
        List<MenuBean> ResMenuBeans = new ArrayList<>();
        for (MenuBean menuBean:
             menuBeans) {
            List<MenuBean> childrenIds = menuDao.findMenuBeansByParent_id(menuBean.getId());
            List<ButtonBean> buttonIds = buttonDao.findButtonBeansByMenu_id(menuBean.getId());
            menuBean.setChildren(childrenIds).setButtonBeans(buttonIds);
            ResMenuBeans.add(menuBean);

        }
        return ApiResult.success(ResMenuBeans);

    }
}
