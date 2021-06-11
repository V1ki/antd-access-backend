package antd_access.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableOpenApi
public class SwaggerConfig {


    @Bean
    public Docket restApi(){
        return new Docket(DocumentationType.OAS_30)

                .apiInfo(apiInfo())
                ;
    }

    public ApiInfo apiInfo(){
        return new ApiInfo(
                "AntD Access","权限管理系统","v1.0.0",
                "https://localhost:10086/",
                new Contact("V1ki","https://github.com/V1ki", "727755316@qq.com"),

                "","" ,new ArrayList<>()
        );
    }

}
