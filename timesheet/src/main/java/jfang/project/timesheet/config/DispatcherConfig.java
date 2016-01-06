package jfang.project.timesheet.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by jfang on 7/5/15.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "jfang.project.timesheet.web")
@Import({ThymeleafConfig.class})
@PropertySource("classpath:ValidationMessages.properties")
public class DispatcherConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/image/**").addResourceLocations("/image/");
        registry.addResourceHandler("/json/**").addResourceLocations("/json/");
    }
}
