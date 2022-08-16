package cn.iecas.springboot.asset.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "cn.iecas.springboot.asset.controller";
    private static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
//        public Docket createRestApi(Environment environment){

//        //设置要显示的swagger环境
//        Profiles profiles = Profiles.of("dev","test");
//
//        //获取项目环境
//        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("malin")
                .apiInfo(apiInfo())
//                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                //paths():过滤什么路径
//                .paths(PathSelectors.ant("/ml/**"))
                .build();
    }

    private ApiInfo apiInfo() {

        //作者信息
        Contact contact = new Contact("malin", "", "ml2907957301@163.com");

//        return new ApiInfo(
//                "ml的Swagger文档",
//                "Api Documentation",
//                "v1.0", "urn:tos",
//                contact, "Apache 2.0",
//                "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());

        return new ApiInfoBuilder()
                .title("数据中台服务") //设置文档的标题
                .description("数据中台服务 API 接口文档") // 设置文档的描述
                .version(VERSION) // 设置文档的版本信息-> 1.0.0 Version information
                .contact(contact)
                .build();
    }
}
