package cn.iecas.springboot.framework.config.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * <code>
 * 日期转换器,将请求参数的日期字符串转换成 java.util.Date 类型
 * </code>
 * @author ch
 * @date 2021-10-20
 */
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		return StringToDateUtil.convert(source);
	}
}
