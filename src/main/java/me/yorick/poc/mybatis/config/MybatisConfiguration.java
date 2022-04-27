package me.yorick.poc.mybatis.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class MybatisConfiguration {

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource datasource) {
	    return new DataSourceTransactionManager(datasource);
	}
}
