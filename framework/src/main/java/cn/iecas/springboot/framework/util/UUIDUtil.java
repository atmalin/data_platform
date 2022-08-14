package cn.iecas.springboot.framework.util;

import java.util.UUID;

/**
 * UUID 生成工具
 * @author ch
 * @date 2021-09-29
 */
public class UUIDUtil {

    public static String getUuid(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
    
}
