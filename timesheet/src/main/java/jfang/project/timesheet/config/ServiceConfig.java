package jfang.project.timesheet.config;

import java.io.IOException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@EnableCaching
@ComponentScan(basePackages = {"jfang.project.timesheet.service"})
public class ServiceConfig {

    private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    // hibernate validator
    @Bean(name="validator")
    public Validator validatorFactory() {
        ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
    
    // dozer mapping
    @Bean(name="mappingFactory")
    public DozerBeanMapperFactoryBean mapper() throws IOException {
        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        Resource[] mappingFiles = resolver.getResources("classpath*:dozer/*.xml");
        mapper.setMappingFiles(mappingFiles);
        return mapper;
    }
    
    // ehcache
    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }
}
