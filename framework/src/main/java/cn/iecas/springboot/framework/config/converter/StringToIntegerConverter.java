package cn.iecas.springboot.framework.config.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <code>
 *
 * </code>
 * @author ch
 * @date 2021-10-20
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String source) {
		return StringToIntegerUtil.convert(source);
	}
}
