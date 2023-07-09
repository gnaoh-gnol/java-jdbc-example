package com.longhd.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfig {

    @Bean
    @Primary
    @Qualifier("hikariDataSourceMysql")
    @ConfigurationProperties("app.datasource.mysql")
    public HikariDataSource hikariDataSourceMysql() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    @Qualifier("mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(@Qualifier("hikariDataSourceMysql")
                                              HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

    @Bean
    @Qualifier("hikariDataSourcePostgresql")
    @ConfigurationProperties("app.datasource.postgresql")
    public HikariDataSource hikariDataSourcePostgresql() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    @Bean
    @Qualifier("postgresqlJdbcTemplate")
    public JdbcTemplate postgresqlJdbcTemplate(@Qualifier("hikariDataSourcePostgresql")
                                                   HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }
}
