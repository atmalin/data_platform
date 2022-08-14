package cn.iecas.springboot.framework.config.jackson.deserializer;

import cn.iecas.springboot.framework.config.converter.StringToDateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 *     Jackson Date序列化器
 * </p>
 * @author ch
 * @date 2021-10-20
 */
public class JacksonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String date = jp.getText();
        return StringToDateUtil.convert(date);
    }

}
