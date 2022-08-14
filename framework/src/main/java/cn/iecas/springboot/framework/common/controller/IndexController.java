package cn.iecas.springboot.framework.common.controller;

import cn.iecas.springboot.framework.log.annotation.OperationLogIgnore;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 * 项目根路径提示信息
 * </p>
 *
 * @author ch
 * @date 2021-09-29
 */
@Slf4j
@Controller
@OperationLogIgnore
@Api(value = "Index API", tags = {"Index"})
public class IndexController {

    /**
     * SwaggerUI
     */
    @GetMapping("/docs")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

}
