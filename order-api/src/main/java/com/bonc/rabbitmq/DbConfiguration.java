package com.bonc.rabbitmq;

import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//配置多数据源
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class DbConfiguration {

	
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db2.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db2.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db2.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db2.username";
	
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    private static final String PROPERTY_NAME_HIBERNATE_AUTO = "hibernate.hbm2ddl.auto";

	private static final String PROPERTY_NAME_HIBERNATE_FORMATS_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS = "hibernate.use_sql_comments";


	@Resource
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

//	@Bean
//	@Qualifier("ds1")
//	public DataSource dataSource1() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		
//		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
//		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
//		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
//		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
//
//		return dataSource;
//	}

	@Bean
	@Qualifier("jt1")
	public JdbcTemplate jdbcTemplate() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("net.sf.log4jdbc.DriverSpy");
		/*130.76.1.206:3306
		root/bonc1q2w3e
		mysql -uroot -pbonc1q2w3e -h130.76.1.206*/
		dataSource.setUrl("jdbc:log4jdbc:mysql://130.76.1.206:3306/hibnatedb?useUnicode=true&characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("bonc1q2w3e");
		return new JdbcTemplate(dataSource);
	}
	
//无效
//	@Value("classpath:channel/mysql.sql")
//	private org.springframework.core.io.Resource schemaScript;
//
//	@Value("classpath:security/mysql.sql")
//	private org.springframework.core.io.Resource dataScript;
//
//	@Bean
//	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
//		final DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(databasePopulator());
//		return initializer;
//	}
//
//	private DatabasePopulator databasePopulator() {
//		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		populator.addScript(schemaScript);
//		populator.addScript(dataScript);
//		return populator;
//	}


	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
				
		entityManagerFactoryBean.setJpaProperties(hibProperties());
		
		return entityManagerFactoryBean;
	}
	
	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_AUTO));
//		properties.put("hibernate.use_sql_comments", true);
//		properties.put("hibernate.format_sql", true);
//
		properties.put(PROPERTY_NAME_HIBERNATE_FORMATS_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMATS_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS));


		System.out.print(env.toString());

		return properties;	
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
    
	public static void main(String[] args) {
		SpringApplication.run(DbConfiguration.class, args);
	}
}
