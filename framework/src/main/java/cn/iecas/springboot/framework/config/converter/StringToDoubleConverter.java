package cn.iecas.springboot.framework.config.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <code>
 *
 * </code>
 * @author ch
 * @date 2021-10-20
 */
public class StringToDoubleConverter implements Converter<String, Double> {

	@Override
	public Double convert(String source) {
		return StringToDoubleUtil.convert(source);
	}
}
