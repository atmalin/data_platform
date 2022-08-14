package cn.iecas.springboot.framework.config.jackson.deserializer;

import cn.iecas.springboot.framework.common.constant.DatePattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *     Jackson LocalDateTime反序列化器
 * </p>
 * @author ch
 * @date 2021-10-20
 */
public class JacksonLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String string = jp.getText();
        if (StringUtils.isBlank(string)){
            return null;
        }
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS));
    }

}
