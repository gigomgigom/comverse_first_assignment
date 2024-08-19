package com.comverse.firstsubject.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceConfig {
	
	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/firstproject");
		config.setUsername("root");
		config.setPassword("1234");
		config.setMaximumPoolSize(12);
		HikariDataSource hikariDataSource = new HikariDataSource(config);
		return hikariDataSource;
	}
}
