package cn.iecas.springboot.convert;

import cn.iecas.springboot.bean.UserBean;
import cn.iecas.springboot.framework.shiro.vo.LoginUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户对象属性转换器
 *
 * @author ch
 * @date 2021-10-15
 **/
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * 系统用户实体对象转换成登录用户VO对象
     *
     * @param user
     * @return
     */
    LoginUserVo userToLoginUserVo(UserBean user);
}
