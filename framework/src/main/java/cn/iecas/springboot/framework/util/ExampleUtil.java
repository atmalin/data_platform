package cn.iecas.springboot.framework.util;

import cn.iecas.springboot.framework.core.pagination.QueryParam;
import cn.iecas.springboot.framework.core.pagination.enums.MatchTypeEnum;
import cn.iecas.springboot.framework.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

/**
 * Example 生成工具
 * @author ch
 * @date 2021-09-29
 */
public class ExampleUtil {

    public static  <T> Example<T> generateExample(List<QueryParam> queryCondition, Class<T> clazz, String... ignoreProperties) {
        if (queryCondition == null || queryCondition.isEmpty()) {
            return null;
        }
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (ignoreProperties != null) {
            matcher = matcher.withIgnorePaths(ignoreProperties);
        }
        JSONObject jsonObject = new JSONObject();
        for (QueryParam queryParam : queryCondition) {
            jsonObject.put(queryParam.getKey(), queryParam.getValue());
            if (MatchTypeEnum.ACCURATE.equals(queryParam.getMatchType())) {
                matcher = matcher.withMatcher(queryParam.getKey(), ExampleMatcher.GenericPropertyMatcher::exact);
            } else if (MatchTypeEnum.FUZZY.equals(queryParam.getMatchType())) {
                matcher = matcher.withMatcher(queryParam.getKey(), ExampleMatcher.GenericPropertyMatcher::contains);
            } else if (MatchTypeEnum.EQUAL.equals(queryParam.getMatchType())) {
                matcher = matcher.withMatcher(queryParam.getKey(), ExampleMatcher.GenericPropertyMatcher::exact);
            }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            T o = mapper.readValue(jsonObject.toString(), clazz);
            return Example.of(o, matcher);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new BusinessException("解析错误");
    }

    public static  <T> Example<T> generateExampleWithDefaultIgnoreProps(List<QueryParam> queryCondition, Class<T> clazz) {
        String[] ignoreProperties = {"createTime", "lastUpdateTime"};
        return generateExample(queryCondition, clazz, ignoreProperties);
    }

    public static  <T> Example<T> generateExampleWithOutIgnoreProps(List<QueryParam> queryCondition, Class<T> clazz) {
        return generateExample(queryCondition, clazz, null);
    }
}
