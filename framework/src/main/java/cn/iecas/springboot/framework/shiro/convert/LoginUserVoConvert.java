package cn.iecas.springboot.framework.shiro.convert;

import cn.iecas.springboot.framework.shiro.vo.LoginUserRedisVo;
import cn.iecas.springboot.framework.shiro.vo.LoginUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 登录系统用户 VO 对象属性复制转换器
 *
 * @author ch
 * @date 2021-10-15
 **/
@Mapper
public interface LoginUserVoConvert {

    LoginUserVoConvert INSTANCE = Mappers.getMapper(LoginUserVoConvert.class);

    /**
     * LoginSysUserVo对象转换成LoginSysUserRedisVo
     *
     * @param loginSysUserVo
     * @return
     */
    LoginUserRedisVo voToRedisVo(LoginUserVo loginSysUserVo);
}
