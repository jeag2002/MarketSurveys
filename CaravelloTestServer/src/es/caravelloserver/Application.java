package es.caravelloserver;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = CaravelloController.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /**
     * Launch Swagger2 portal (url access: http://localhost:8080/swagger-ui.html)
     * @return
     */
    
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("es.caravelloserver"))
                .paths(regex("/(asyncget|get)marketsurvey(slist|slistcache|)"))
                .build()
                .apiInfo(apiInfo());
    }
     
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Market surveys API")
                .description("Spring REST Market surveys API with Swagger")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact("J.E. Alcaraz")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
    
    

}
