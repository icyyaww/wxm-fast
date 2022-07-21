/*
package com.wxm.msfast.base.common.config;

*/
/**
 * @program: makefriends
 * @description:
 * @author: WangLei
 * @create: 2020-08-06 15:10
 **//*


import com.wxm.msfast.base.common.annotation.Swagger;
import org.springframework.context.annotation.Bean;


import java.util.Collections;

public class BaseSwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Swagger.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(
                        Collections.singletonList(new RequestParameterBuilder()
                                .name("Authorization")
                                .description("token信息")
                                .in(ParameterType.HEADER)
                                .required(false)
                                .build()));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("利用swagger2构建的API文档")
                .description("用restful风格写接口")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
*/
