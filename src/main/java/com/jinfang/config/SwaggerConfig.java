package com.jinfang.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * swagger configuration
 *
 * @author gjm
 * @since 2020-01-02
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Every Docket bean is picked up by the swagger-mvc framework - allowing for multiple
     * swagger groups i.e. same code base multiple swagger resource listings.
     */
    private List<Parameter> needParameters() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(true).build();

        parameters.add(parameterBuilder.build());

        return parameters;
    }

    @Bean
    public Docket createRestApiForSystem() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.jinfang.controller.login"))
                .paths(PathSelectors.any()).build().groupName("系统登陆接口")
                .globalOperationParameters(needParameters());
    }
    @Bean
    public Docket createRestApiForMajorLeaer() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.jinfang.controller.majorLeaer"))
                .paths(PathSelectors.any()).build().groupName("专业负责人接口")
                .globalOperationParameters(needParameters());
    }
    @Bean
    public Docket createRestApiForinAdviser() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.jinfang.controller.inAdviser"))
                .paths(PathSelectors.any()).build().groupName("校内指导老师接口")
                .globalOperationParameters(needParameters());
    }
    @Bean
    public Docket createRestApiForoutAdviser() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.jinfang.controller.outAdviser"))
                .paths(PathSelectors.any()).build().groupName("校外指导老师接口")
                .globalOperationParameters(needParameters());
    }
    @Bean
    public Docket createRestApiForStudent() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.jinfang.controller.student"))
                .paths(PathSelectors.any()).build().groupName("学生接口")
                .globalOperationParameters(needParameters());
    }
    @Bean
    public Docket createRestApiForFile() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.jinfang.controller.system"))
                .paths(PathSelectors.any()).build().groupName("实习报告文件接口")
                .globalOperationParameters(needParameters());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("工程实习系统 API Doc")
                //创建人
                .contact(new Contact("Gjm", "", ""))
                //版本号
                .version("1.0")
                //描述
                .description("在相应文档里找到需要得参数")
                .build();
    }

}

