package cn.iecas.springboot.framework.config;

import cn.iecas.springboot.framework.common.constant.DatePattern;
import cn.iecas.springboot.framework.config.jackson.deserializer.JacksonDateDeserializer;
import cn.iecas.springboot.framework.config.jackson.deserializer.JacksonDoubleDeserializer;
import cn.iecas.springboot.framework.config.jackson.serializer.JacksonDateSerializer;
import cn.iecas.springboot.framework.config.jackson.serializer.JacksonIntegerDeserializer;
import cn.iecas.springboot.framework.core.xss.XssJacksonDeserializer;
import cn.iecas.springboot.framework.core.xss.XssJacksonSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author ch
 * @author 2021-10-20
 */
@Configuration
public class JacksonConfig implements WebMvcConfigurer {

    @Value("${spring-boot-master.filter.xss.enable}")
    private boolean enableXss;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        SimpleModule simpleModule = new SimpleModule();
        // Long????????????????????????????????????Long????????????
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // XSS?????????
        if (enableXss){
            simpleModule.addSerializer(String.class, new XssJacksonSerializer());
            simpleModule.addDeserializer(String.class, new XssJacksonDeserializer());
        }

        // Date
        simpleModule.addSerializer(Date.class, new JacksonDateSerializer());
        simpleModule.addDeserializer(Date.class, new JacksonDateDeserializer());

        simpleModule.addDeserializer(Integer.class, new JacksonIntegerDeserializer());
        simpleModule.addDeserializer(Double.class, new JacksonDoubleDeserializer());

        // jdk8????????????????????????????????????
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD_HH_MM_SS)));

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.YYYY_MM_DD)));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.HH_MM_SS)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.HH_MM_SS)));

        objectMapper.registerModule(simpleModule).registerModule(javaTimeModule).registerModule(new ParameterNamesModule());

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        //???????????????
        converters.add(0, jackson2HttpMessageConverter);
    }
}