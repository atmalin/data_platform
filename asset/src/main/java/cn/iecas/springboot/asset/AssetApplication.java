package cn.iecas.springboot.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableScheduling
@EnableJpaAuditing
@EnableTransactionManagement
@EnableConfigurationProperties

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })
//扫描其他模块中的包，从而使配置类扫描成功
//@ComponentScan(basePackages = "cn.iecas.springboot.config")
public class AssetApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AssetApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }

}
