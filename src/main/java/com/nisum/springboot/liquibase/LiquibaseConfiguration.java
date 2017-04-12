package com.nisum.springboot.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {
    private ApplicationContext ctx = null;

    private static final String CHANGELOG_LOCATION = "classpath:db/changelog/db.changelog-master.yaml";
//    @Autowired
//    @Qualifier("dataSourceMysql")
//    private DataSource dataSource;
//    @Value("${transactions.liquibase.should.run}")
//    private boolean liquiBaseShouldRun;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();

//        liquibase.setDataSource(dataSource);
//        liquibase.setChangeLog(CHANGELOG_LOCATION);
//        liquibase.setShouldRun(liquiBaseShouldRun);
        return liquibase;
    }
}
