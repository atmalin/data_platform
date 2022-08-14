package cn.iecas.springboot.framework.config.converter;

import org.apache.commons.lang3.StringUtils;

/**
 * <code>
 * <pre>
 * 空字符串("")转换成Integer的null
 *
 * </pre>
 * </code>
 * @author ch
 * @date 2021-10-20
 */
public class StringToIntegerUtil {

	public static Integer convert(String source) {
		if (StringUtils.isBlank(source)){
			return null;
		}
		Integer i = Integer.parseInt(source);
		return i;
	}
}
