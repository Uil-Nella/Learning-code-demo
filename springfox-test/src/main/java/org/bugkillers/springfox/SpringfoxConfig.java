package org.bugkillers.springfox;


import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
* 使用注解的方式来扫描API
* 无需在Spring的xml配置文件来配置，由 @see @EnableWebMvc 代替
* <p/>
* <p> @author 刘新宇
*
* <p> @date 2015年1月30日 下午1:18:48
* <p> @version 0.0.1
*/
@Configuration
@EnableWebMvc
@EnableSwagger
@ComponentScan("org.bugkillers")
public class SpringfoxConfig extends WebMvcConfigurerAdapter {

   private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }
    /**
     * 链式编程 来定制API样式
     * 后续会加上分组信息
     * @return
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(".*")
                .apiVersion("0.0.1")
                .swaggerGroup("user");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "bugkillers-back API",
                "bugkillers 后台API文档",
                "http://127.0.0.1:9081/api",
                "bugkillers@163.com",
                "My License",
                "My Apps API License URL"
        );
        return apiInfo;
    }



}