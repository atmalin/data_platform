package cn.iecas.springboot.framework.config.jackson.serializer;

import cn.iecas.springboot.framework.util.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * Jackson Date反序列化器
 * </p>
 *
 * @author ch
 * @date 2021-10-20
 */
public class JacksonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String string = null;
        if (date != null) {
            string = DateUtil.getDateTimeString(date);
        }
        jsonGenerator.writeString(string);
    }
}
