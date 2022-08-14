package cn.iecas.springboot.framework.common.constant;

import cn.iecas.springboot.framework.core.pagination.enums.SortStateEnum;

/**
 * 公共常量
 *
 * @author ch
 * @date 2021-09-29
 */
public interface CommonConstant {
    /**
     * 默认页码为1
     */
    Integer DEFAULT_CUR_PAGE = 1;

    /**
     * 默认页大小为10
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 分页总行数名称
     */
    String PAGE_TOTAL_NAME = "total";

    /**
     * 分页数据列表名称
     */
    String PAGE_RECORDS_NAME = "records";

    /**
     * 默认排序字段为最后更新时间
     */
    String SORTED_FIELD = "lastUpdateTime";

    /**
     * 默认排序方式为 DESC
     */
    SortStateEnum SORTED_TYPE = SortStateEnum.DESC;

    /**
     * 登录token
     */
    String JWT_DEFAULT_TOKEN_NAME = "token";

    /**
     * JWT用户名
     */
    String JWT_USERNAME = "username";

    /**
     * JWT Token默认密钥
     */
    String JWT_DEFAULT_SECRET = "iecas";

    /**
     * JWT 默认过期时间，3600L，单位秒
     */
    Long JWT_DEFAULT_EXPIRE_SECOND = 3600L;

    /**
     * ..
     */
    String SPOT_SPOT = "..";

    /**
     * ../
     */
    String SPOT_SPOT_BACKSLASH = "../";

    /**
     * 用户浏览器代理
     */
    String USER_AGENT = "User-Agent";

    /**
     * JWT刷新新token响应状态码
     */
    int JWT_REFRESH_TOKEN_CODE = 460;

    /**
     * JWT刷新新token响应状态码，
     * Redis中不存在，但jwt未过期，不生成新的token，返回361状态码
     */
    int JWT_INVALID_TOKEN_CODE = 461;

    // cron 默认表达式
    String DEFAULT_CRON = "*******";
}
