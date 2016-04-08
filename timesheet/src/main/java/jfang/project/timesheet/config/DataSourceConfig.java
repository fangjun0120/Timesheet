package jfang.project.timesheet.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Autowired
    private Environment env;

    @Bean
    @Profile("default")
    public DataSource dataSource() {
    	logger.info("Using product dataSource.");
    	
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(env.getRequiredProperty("db.url"));
        dataSourceConfig.setUsername(env.getRequiredProperty("db.username"));
        dataSourceConfig.setPassword(env.getRequiredProperty("db.password"));
        dataSourceConfig.addDataSourceProperty("poolName", "hikariCP");
        dataSourceConfig.addDataSourceProperty("connectionTestQuery", "SELECT 1");
        dataSourceConfig.addDataSourceProperty("dataSourceClassName", env.getRequiredProperty("hikari.dataSourceClassName"));
        dataSourceConfig.addDataSourceProperty("maximumPoolSize", env.getRequiredProperty("hikari.maximumPoolSize"));
        dataSourceConfig.addDataSourceProperty("idleTimeout", env.getRequiredProperty("hikari.idleTimeout"));

        return new HikariDataSource(dataSourceConfig);
    }
    
    @Bean
    @Profile("test")
    public DataSource embeddedTestDataSource() {
    	logger.info("Using embedded dataSource.");
    	
    	EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.setType(EmbeddedDatabaseType.H2)
			.addScript("classpath:sql/create_table.sql")
			.addScript("classpath:sql/insert_data.sql")
			.build();
		return db;
    }

    @Bean
    @Profile("demo")
    public DataSource embeddedDataSource() {
        logger.info("Using demo embedded dataSource.");

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/create_table.sql")
                .addScript("classpath:sql/insert_data.sql")
                .build();
        return db;
    }
}
