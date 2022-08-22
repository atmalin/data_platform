package cn.iecas.springboot.service;

import cn.iecas.springboot.dao.RoleDao;
import cn.iecas.springboot.dao.RolePermissionDao;
import cn.iecas.springboot.dao.UserRoleDao;
import cn.iecas.springboot.entity.RoleBean;
import cn.iecas.springboot.entity.RolePermissionBean;
import cn.iecas.springboot.entity.UserBean;
import cn.iecas.springboot.entity.UserRoleBean;
import cn.iecas.springboot.entity.dataBean.RoleAdd;
import cn.iecas.springboot.framework.config.properties.OauthProperties;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.exception.DaoException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private OauthProperties oauthProperties;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<RoleBean> add(RoleAdd roleAdd){
        if (roleDao.findByRoleName(roleAdd.getRoleName())!=null){
            throw new BusinessException("角色名称重复");
        }
        //将传来的role对象转化为添加到表tb_role对象
        RoleBean roleBean = new RoleBean().setName(roleAdd.getRoleName())
                .setEn_name(roleAdd.getRoleEnName())
                .setDescription(roleAdd.getDescription())
                .setParent_id("admin")
                .setClient_id("1");
        roleDao.save(roleBean);
        //添加角色应当维护角色权限表
        List<List<String>> roleMenu = roleAdd.getRoleMenu();
        for (int i = 0; i < roleMenu.size(); i++) {
            List<String> list = roleMenu.get(i);
            RolePermissionBean rolePermissionBean = new RolePermissionBean().
                    setRoleId(roleBean.getId())
                    .setMenuId(list.get(0))
                    .setButton_id(list.get(1));
            rolePermissionDao.save(rolePermissionBean);
        }
        return ApiResult.success(roleBean);
    }


//    @Transactional(rollbackFor = Exception.class)
//    public ApiResult<String> find(){
//        roleDao.findAll();
//        return ApiResult.success("查询成果");
//    }
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(String id){  //id为tb_role 的主键id
        if (!roleDao.existsById(id)){
            throw new BusinessException("该角色不存在");
        }
        // 判断该角色是否有用户在关联，如果有，则不能删除
        UserRoleBean userRoleBean = new UserRoleBean().setRoleId(id);
        Example<UserRoleBean> example = Example.of(userRoleBean);
        if (userRoleDao.exists(example)){
            throw new DaoException("该角色被用户使用，不能删除");
        }

        //若可以删除角色，则应该把角色与权限关联表中对应数据删除
        RolePermissionBean rolePermissionBean = new RolePermissionBean().setRoleId(id);
        Example<RolePermissionBean> rolePermissionBeanExample = Example.of(rolePermissionBean);
        if (rolePermissionDao.exists(rolePermissionBeanExample)){
            rolePermissionDao.deleteInBatch(rolePermissionDao.findAll(rolePermissionBeanExample));
        }
        roleDao.deleteById(id);

        return ApiResult.success("角色删除成功");
    }

    //修改角色
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<RoleBean> modify(RoleAdd roleAdd){

        //前端控制用户名重复问题
        //获取用户 userBean为传来的修改后的对象(userADD)，无ID,需要封装待修改的用户ID,然后进行修改操作
        RoleBean roleBean;
        RoleBean exampleBean = new RoleBean().setId(roleAdd.getRoleId());
        Example<RoleBean> example = Example.of(exampleBean);
        if (roleDao.exists(example)){
            roleBean = roleDao.getOne(roleAdd.getRoleId());
        }else {
            throw new BusinessException("用户不存在");
        }
        // 默认角色父id为admin 客户端id为1
        Date now = new Date();
        roleBean.setName(roleAdd.getRoleName())
                .setEn_name(roleAdd.getRoleEnName())
                .setDescription(roleAdd.getDescription())
                .setParent_id("admin")
                .setClient_id("1")
                .setId(roleAdd.getRoleId())
                .setLastUpdateTime(now);
        //修改角色
        roleDao.saveAndFlush(roleBean);
        //同时 修改角色权限关联表
        //先删除
        RolePermissionBean rolePermissionBean = new RolePermissionBean().setRoleId(roleAdd.getRoleId());
        Example<RolePermissionBean> example2 = Example.of(rolePermissionBean);
        rolePermissionDao.deleteInBatch(rolePermissionDao.findAll(example2));
        //再添加
        List<List<String>> roleMenu = roleAdd.getRoleMenu();
        for (int i = 0; i < roleMenu.size(); i++) {
            List<String> list = roleMenu.get(i);
            RolePermissionBean tempBean = new RolePermissionBean().
                    setRoleId(roleBean.getId())
                    .setMenuId(list.get(0))
                    .setButton_id(list.get(1));
            rolePermissionDao.save(tempBean);
        }


        return ApiResult.success(roleBean);
    }

    public ApiResult<List<RoleBean>> list(){
        List<RoleBean> all = roleDao.findAll();
        return ApiResult.success(all);
    }

//    public ApiResult<PageResult<RoleBean>> list(SearchParam param) {
//        Example<RoleBean> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), RoleBean.class);
//        Pageable pageable = PageableUtil.generatePageable(param);
//        Page<RoleBean> roleBeans;
//        if (example == null) {
//            roleBeans = roleDao.findAll(pageable);
//        } else {
//            roleBeans = roleDao.findAll(example, pageable);
//        }
//        return ApiResult.success(new PageResult<>(roleBeans));
//    }

//
//    //更新角色权限
//    //同时维护角色权限表
//    //权限分配方法是分开的吗？？？？
//    public ApiResult<String> updateRolePermission(UpdateRolePermissionParam param){
//        Long roleId = param.getRoleId();
//        List<Long> permissionIds = param.getPermissionIds();
//        //校验角色是否存在
//        if (!roleDao.existsById(roleId)){
//            throw new BusinessException("该角色不存在");
//        }
//        return ApiResult.success("权限更新成功");
//
//    }


}
