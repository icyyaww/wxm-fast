package com.wxm.msfast.demo.config;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.any;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-07-19 16:21
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig  {

    protected Set<String> jsonProduces = Sets.newHashSet(new String[]{"application/json"});

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(any())
                .build()
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false)
                .produces(jsonProduces);
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
