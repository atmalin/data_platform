package cn.iecas.springboot.service;

import cn.iecas.springboot.dao.UserDao;
import cn.iecas.springboot.dao.UserDeptDao;
import cn.iecas.springboot.dao.UserRoleDao;
import cn.iecas.springboot.entity.UserBean;
import cn.iecas.springboot.entity.UserDeptBean;
import cn.iecas.springboot.entity.UserRoleBean;
import cn.iecas.springboot.entity.dataBean.UserAdd;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.util.SaltUtil;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import cn.iecas.springboot.framework.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserDeptDao userDeptDao;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<UserAdd> add(UserAdd user){
        //检验用户名是否存在(思路二 根据username查表？)
        UserBean exampleBean = new UserBean().setUsername(user.getUsername());
        Example<UserBean> example = Example.of(exampleBean);
        if (userDao.exists(example)){
            throw new BusinessException("用户已存在");
        }
        UserBean userBean = new UserBean().setUsername(user.getUsername())
                .setPhone(user.getPhone())
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setCompany(user.getDeptId());
        //生成盐值
        String salt = SaltUtil.generateSalt();  //随机生成一个字符串
        String password = user.getPassword();

        userBean.setSalt(salt);
        userBean.setPassword(PasswordUtil.encrypt(password,salt));//DigestUtils.sha256Hex加密算法
        userDao.save(userBean);

        //用户添加成功后需要维护 user_role表
        List<String> roleList = user.getRoleList();
        for (String roleID :
                roleList) {
            UserRoleBean userRoleBeanTemp = new UserRoleBean().setUserId(userBean.getId())
                    .setRoleId(roleID);
            userRoleDao.save(userRoleBeanTemp);
        }
        //一个用户只存在一个部门 同时维护用户部门表
        UserDeptBean userDeptBean = new UserDeptBean().setUserId(userBean.getId())
                .setDeptId(user.getDeptId());
        userDeptDao.save(userDeptBean);
        return ApiResult.success(user);
    }

//    public ApiResult<String> find(){
//        roleDao.findAll();
//        return ApiResult.success("查询成果");
//    }
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(String userId){
        UserBean exampleBean = new UserBean().setId(userId);
        Example<UserBean> example = Example.of(exampleBean);
        if (!userDao.exists(example)){
            throw new BusinessException("用户不存在");
        }
        //若存在 删除用户得同时需要把用户关联的角色表同时删除
        userRoleDao.deleteAllByUserId(userId);
        //同时删除用户部门关联表
        userDeptDao.deleteAllByUserId(userId);
        //最后删除用户信息
        userDao.deleteAllById(userId);

        return ApiResult.success("删除成功");
    }
//
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<UserAdd> modify(UserAdd userAddBean){
        //前端控制用户名重复问题
        //获取用户 userBean为传来的修改后的对象(userADD)，无ID,需要封装待修改的用户ID,然后进行修改操作
        UserBean oldUser;
        UserBean exampleBean = new UserBean().setId(userAddBean.getUserId());
        Example<UserBean> example = Example.of(exampleBean);
        if (userDao.exists(example)){
            oldUser = userDao.getOne(userAddBean.getUserId());
        }else {
            throw new BusinessException("用户不存在");
        }
//        修改用户 前端控制对应字段不能是空值
        oldUser.setUsername(userAddBean.getUsername())
                .setName(userAddBean.getName())
                .setPhone(userAddBean.getPhone())
                .setEmail(userAddBean.getEmail())
                .setSex(userAddBean.getSex())
                .setId(userAddBean.getUserId());
        //需要对修改的密码加密保存
        String password = userAddBean.getPassword();
        String salt = oldUser.getSalt();  //获取数据对应的盐值
        oldUser.setPassword(PasswordUtil.encrypt(password,salt));//DigestUtils.sha256Hex加密算法

        userDao.saveAndFlush(oldUser);
        //修改完毕后，应维护user_role表 （先对原来uid删除，然后对新的rid进行添加）
        //删除
        userRoleDao.deleteAllByUserId(userAddBean.getUserId());
        //添加
        List<String> roleList = userAddBean.getRoleList();
        for (String roleID :
                roleList) {
            UserRoleBean userRoleBeanTemp = new UserRoleBean().setUserId(userAddBean.getUserId())
                    .setRoleId(roleID);
            userRoleDao.save(userRoleBeanTemp);
        }
        //维护用户部门表
        //删除
        userDeptDao.deleteAllByUserId(userAddBean.getUserId());
        //添加
        userDeptDao.save(new UserDeptBean().setDeptId(userAddBean.getDeptId())
                            .setUserId(userAddBean.getUserId()));


        return ApiResult.success(userAddBean);
    }
//
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<List<UserBean>> find(String nickname){
        //如果查询为空值 则查询所有用户
        if (nickname.length()==0){
            userDao.findAll();
        }

        if (userDao.findUserBeanByName(nickname)==null){
            throw new BusinessException("该用户不存在");
        }else{
            //模糊查询

            return ApiResult.success(userDao.findUserBeanByName(nickname));
        }

    }
//
    //分页查询
    public ApiResult<PageResult<UserBean>> list(SearchParam param) {
        Example<UserBean> example = ExampleUtil.generateExampleWithOutIgnoreProps(param.getQueryCondition(), UserBean.class);
        Pageable pageable = PageableUtil.generatePageable(param);
        Page<UserBean> userBeanPage;
        if (example == null) {
            userBeanPage = userDao.findAll(pageable);
        } else {
            userBeanPage = userDao.findAll(example, pageable);
        }
        return ApiResult.success(new PageResult<>(userBeanPage));
    }


}
