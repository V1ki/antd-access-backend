package antd_access.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableOpenApi
public class SwaggerConfig {


    @Bean
    public Docket restApi(){
        return new Docket(DocumentationType.OAS_30)

                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("antd_access.controller"))
                .build()
                .securitySchemes(schemeList())
                .securityContexts(securityContexts())

                ;
    }


        private List<SecurityScheme> schemeList(){
            return Collections.singletonList(
                    // http basic
                    HttpAuthenticationScheme.BASIC_AUTH_BUILDER.name("basic").build()
            );
        }

        private List<SecurityContext> securityContexts(){
            return Collections.singletonList(
                    SecurityContext.builder().securityReferences(
                            Collections.singletonList(new SecurityReference("basic",new AuthorizationScope[0]))
                    )
                            .build()
            );
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
