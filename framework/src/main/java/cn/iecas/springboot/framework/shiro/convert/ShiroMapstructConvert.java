package cn.iecas.springboot.framework.shiro.convert;

import cn.iecas.springboot.framework.shiro.jwt.JwtToken;
import cn.iecas.springboot.framework.shiro.vo.JwtTokenRedisVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Shiro 包下使用 mapstruct 对象属性复制转换器
 *
 * @author ch
 * @date 2021-10-15
 */
@Mapper
public interface ShiroMapstructConvert {

    ShiroMapstructConvert INSTANCE = Mappers.getMapper(ShiroMapstructConvert.class);

    /**
     * JwtToken对象转换成JwtTokenRedisVo
     *
     * @param jwtToken
     * @return
     */
    JwtTokenRedisVo jwtTokenToJwtTokenRedisVo(JwtToken jwtToken);
}
