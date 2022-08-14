package cn.iecas.springboot.service;

import cn.iecas.springboot.bean.UserBean;
import cn.iecas.springboot.bean.UserRoleBean;
import cn.iecas.springboot.dao.UserDao;
import cn.iecas.springboot.dao.UserRoleDao;
import cn.iecas.springboot.framework.common.enums.StatusEnum;
import cn.iecas.springboot.framework.config.properties.OauthProperties;
import cn.iecas.springboot.framework.core.pagination.PageResult;
import cn.iecas.springboot.framework.core.pagination.SearchParam;
import cn.iecas.springboot.framework.core.util.SaltUtil;
import cn.iecas.springboot.framework.exception.BusinessException;
import cn.iecas.springboot.framework.result.ApiResult;
import cn.iecas.springboot.framework.util.ExampleUtil;
import cn.iecas.springboot.framework.util.PageableUtil;
import cn.iecas.springboot.framework.util.PasswordUtil;
import cn.iecas.springboot.param.user.ResetPasswordParam;
import cn.iecas.springboot.param.user.UpdatePasswordParam;
import cn.iecas.springboot.param.user.UploadAvatarParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 系统用户 服务实现类
 * </pre>
 *
 * @author ch
 * @since 2021-09-27
 */
@Slf4j
@Service
public class UserService {

    @Lazy
    @Autowired
    private UserDao userDao;

    @Autowired
    private OauthProperties oauthProperties;

    @Autowired
    private UserRoleDao userRoleDao;

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<UserBean> addUser(UserBean user) {
        // 校验用户名是否存在
        UserBean exampleBean = new UserBean().setUsername(user.getUsername());
        Example<UserBean> example = Example.of(exampleBean);
        if (userDao.exists(example)) {
            throw new BusinessException("用户已存在");
        }

        // 生成盐值
        String salt = SaltUtil.generateSalt();
        String password = user.getPassword();

        // 密码加密
        user.setSalt(salt);
        user.setPassword(PasswordUtil.encrypt(password, salt));

        // 如果头像为空，则设置默认头像
        if (StringUtils.isBlank(user.getAvatar())) {
            user.setAvatar(oauthProperties.getInitAvatar());
        }

        userDao.save(user);

        // 设置默认角色
        UserRoleBean userRoleBean = new UserRoleBean().setUserId(user.getId()).setRoleId(oauthProperties.getInitRoleId());
        userRoleDao.save(userRoleBean);
        return ApiResult.success(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<String> remove(Long id) {
        if (userDao.existsById(id)) {
            List<Long> ridList = userRoleDao.findRidListByUid(id);
            ridList.forEach(rid -> userRoleDao.deleteById(rid));
            userDao.deleteById(id);
             return ApiResult.success("用户删除成功");
        } else {
            return ApiResult.fail("该用户不存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResult<UserBean> modify(UserBean userBean) {
        // 获取用户
        UserBean oldUser;
        if (userDao.existsById(userBean.getId())) {
            oldUser = userDao.getOne(userBean.getId());
        } else {
            throw new BusinessException("用户不存在");
        }

        // 修改用户
        oldUser.setNickname(userBean.getNickname())
                .setState(userBean.getState())
                .setRemark(userBean.getRemark())
                .setDepartmentId(userBean.getDepartmentId());
        userDao.saveAndFlush(oldUser);
        return ApiResult.success(oldUser);
    }

    public ApiResult<UserBean> getOne(Long id) {
        if (userDao.existsById(id)) {
            return ApiResult.success(userDao.getOne(id));
        } else {
            throw new BusinessException("该用户不存在");
        }
    }


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

    public ApiResult<String> updatePassword(UpdatePasswordParam param) {
        // 旧密码
        String oldPassword = param.getOldPassword();
        // 新密码
        String newPassword = param.getNewPassword();
        // 确认密码
        String confirmPassword = param.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException("两次输入的密码不一致");
        }

        // 判断原密码是否正确
        if (!userDao.existsById(param.getUserId())) {
            throw new BusinessException("用户不存在");
        }
        UserBean user = userDao.getOne(param.getUserId());
        if (StatusEnum.DISABLE.getCode().equals(user.getState())) {
            throw new BusinessException("用户已禁用");
        }

        // 密码加密处理
        String salt = user.getSalt();
        String encryptOldPassword = PasswordUtil.encrypt(oldPassword, salt);
        if (!user.getPassword().equals(encryptOldPassword)) {
            throw new BusinessException("原密码错误");
        }
        // 新密码加密
        String encryptNewPassword = PasswordUtil.encrypt(newPassword, salt);

        // 修改密码
        UserBean newUser = (UserBean) new UserBean()
                .setId(user.getId())
                .setPassword(encryptNewPassword)
                .setLastUpdateTime(new Date());
        userDao.saveAndFlush(newUser);
        return ApiResult.success("密码修改成功");
    }

    public ApiResult<String> resetPassword(ResetPasswordParam param) {
        String newPassword = param.getNewPassword();
        String confirmPassword = param.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException("两次输入的密码不一致");
        }
        // 判断用户是否可修改
        if (!userDao.existsById(param.getUserId())) {
            throw new BusinessException("用户不存在");
        }
        UserBean user = userDao.getOne(param.getUserId());
        if (StatusEnum.DISABLE.getCode().equals(user.getState())) {
            throw new BusinessException("用户已禁用");
        }
        // 密码加密处理
        String salt = user.getSalt();
        // 新密码加密
        String encryptNewPassword = PasswordUtil.encrypt(newPassword, salt);

        // 修改密码
        UserBean newUser = (UserBean) new UserBean()
                .setId(user.getId())
                .setPassword(encryptNewPassword)
                .setLastUpdateTime(new Date());
        userDao.saveAndFlush(newUser);
        return ApiResult.success("密码重置成功");
    }

    public ApiResult<String> updateAvatar(UploadAvatarParam param) {
        UserBean newUser = new UserBean()
                .setId(param.getId())
                .setAvatar(param.getAvatar());
        userDao.saveAndFlush(newUser);
        return ApiResult.success("头像修改成功");
    }
}
