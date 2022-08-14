package cn.iecas.springboot.framework.shiro.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * JwtToken Redis 缓存对象
 *
 * @author ch
 * @date 2021-10-15
 */
@Data
@Accessors(chain = true)
public class JwtTokenRedisVo implements Serializable {

    private static final long serialVersionUID = -1767981683232201663L;

    /**
     * 登录ip
     */
    private String host;
    /**
     * 登录用户名称
     */
    private String username;
    /**
     * 登录盐值
     */
    private String salt;
    /**
     * 登录token
     */
    private String token;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 多长时间过期，默认一小时
     */
    private long expireSecond;
    /**
     * 过期日期
     */
    private Date expireDate;
}
