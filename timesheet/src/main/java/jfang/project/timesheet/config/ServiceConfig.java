package jfang.project.timesheet.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"jfang.project.timesheet.service"})
public class ServiceConfig {

    @Bean(name="validator")
    public Validator validatorFactory() {
        ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
