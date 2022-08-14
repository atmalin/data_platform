package cn.iecas.springboot.framework.common.enums.config;

import cn.iecas.springboot.framework.common.enums.BaseEnum;
import com.fasterxml.classmate.ResolvedType;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.schema.Annotations;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig2 implements ModelPropertyBuilderPlugin {

    @Value("${knife4j.enable}")
    private Boolean product;

    @Override
    public void apply(ModelPropertyContext context) {
        if (!product) {
            return;
        }

        // 为枚举字段设置注释
        Optional<ApiModelProperty> annotation = Optional.empty();
        if (context.getAnnotatedElement().isPresent()) {
            annotation = Optional.ofNullable(annotation.orElseGet(
                    ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get())::get));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = Optional.ofNullable(annotation.orElseGet(Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class)::get
            ));
        }

        // 按照业务调整 ".enums"的内容
        String enumPackage = (annotation.get()).notes();
        if (StringUtils.isBlank(enumPackage) || !enumPackage.contains(".enums.")) {
            return;
        }

        Class rawPrimaryType;
        try {
            rawPrimaryType = Class.forName(enumPackage);
        } catch (ClassNotFoundException e) {
            return;
        }
        Object[] subItemRecords = null;
        SwaggerDisplayEnum swaggerDisplayEnum = AnnotationUtils.findAnnotation(rawPrimaryType, SwaggerDisplayEnum.class);
        if (null != swaggerDisplayEnum && Enum.class.isAssignableFrom(rawPrimaryType)) {
            subItemRecords = rawPrimaryType.getEnumConstants();
        }
        if (null == subItemRecords) {
            return;
        }

        List<String> displayValues = new ArrayList<>();

        for (Object subItemRecord : subItemRecords) {
            BaseEnum subItemRecordEnum = (BaseEnum) subItemRecord;
            Integer code = subItemRecordEnum.getCode();
            String desc = subItemRecordEnum.getDesc();
            displayValues.add(code + ":" + desc);
        }

        String joinText = "(" + String.join("; ", displayValues) + ")";
        try {
            joinText = annotation.get().name() + joinText;
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        final Class fieldType = context.getBeanPropertyDefinition().get().getField().getRawType();
        final ResolvedType resolvableType = context.getResolver().resolve(fieldType);
        context.getBuilder().description(joinText).type(resolvableType);
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
