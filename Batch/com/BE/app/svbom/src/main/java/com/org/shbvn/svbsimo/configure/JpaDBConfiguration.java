package com.org.shbvn.svbsimo.configure;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.zaxxer.hikari.HikariDataSource;



@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.org.shbvn.svbsimo.repository.dao",
		entityManagerFactoryRef = "databaseEntityManagerFactory",
		transactionManagerRef = "databaseTransactionManager")

@PropertySource("classpath:application.properties")
public class JpaDBConfiguration {

	@Autowired
	private Environment environment;

	@Value("${datasource.database.simo.maxPoolSize}")
	private int maxPoolSize;
	
	@Value("${datasource.database.simo.minIdlePoolSize}")
	private int minIdleSize;
	
	@Value("${datasource.database.simo.connection-timeout}")
	private int connectionTimeout;
	

	@Bean(name = "databaseDataSourceProperties")
	@Primary
	@ConfigurationProperties(prefix = "datasource.database.simo")
	public DataSourceProperties databaseDataSourceProperties(){
		return new DataSourceProperties();
	}

	/*
	 * Configure HikariCP pooled DataSource.
	 */
	@Bean(name = "databaseDataSource")
	@Primary 
	public DataSource databaseDataSource() {
		DataSourceProperties dataSourceProperties = databaseDataSourceProperties();
			HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
					.create(dataSourceProperties.getClassLoader())
					.driverClassName(dataSourceProperties.getDriverClassName())
					.url(dataSourceProperties.getUrl())
					.username(dataSourceProperties.getUsername())
					.password(dataSourceProperties.getPassword())
					.type(HikariDataSource.class)
					.build();
			dataSource.setMaximumPoolSize(maxPoolSize);
			dataSource.setMinimumIdle(minIdleSize);
			dataSource.setConnectionTimeout(connectionTimeout);
			return dataSource;
	}

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean(name = "databaseEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean databaseEntityManagerFactory() throws NamingException {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(databaseDataSource());
		factoryBean.setPackagesToScan(new String[] { "com.org.shbvn.svbsimo.repository.entities" });
		factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		factoryBean.setJpaProperties(jpaProperties());
		factoryBean.setPersistenceUnitName("entityManagerSimo");
		return factoryBean;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.database.simo.hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.database.simo.hibernate.hbm2ddl.method"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.database.simo.hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.database.simo.hibernate.format_sql"));
		if(!CommonUtils.isBlank(environment.getRequiredProperty("datasource.database.simo.defaultSchema"))){
			properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.database.simo.defaultSchema"));
		}
		return properties;
	}

	@Bean(name = "databaseTransactionManager")
	@Primary
	public PlatformTransactionManager databaseTransactionManager(@Qualifier("databaseEntityManagerFactory") EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
