package com.pablos.spring.mvc.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration // заменяет applicationContext.xml
@ComponentScan(basePackages = "com.pablos.spring.mvc.rest") // заменяет <context:component-scan base-package="com.pablos.spring.mvc" />
@EnableWebMvc // заменяет <mvc:annotation-driven/>
@EnableTransactionManagement //вместо <tx:annotation-driven transaction-manager=...
public class MyConfig {
    @Bean
    public DataSource dataSource() { // вместо <bean id="dataSource".../bean>
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver"); //вместо <property name="driverClass" value="com.mysql.cj.jdbc.Driver" />
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC"); // вместо <property name="jdbcUrl"
            dataSource.setUser("bestuser");
            dataSource.setPassword("bestuser");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() { // вместо <bean id="sessionFactory"
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource()); // вместо <property name="dataSource" ref="dataSource" />
        sessionFactory.setPackagesToScan("com.pablos.spring.mvc.rest.entity"); // вместо <property name="packagesToScan"

        Properties hibernateproperties = new Properties(); //вместо <property name="hibernateProperties"> <props>
        hibernateproperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateproperties.setProperty("hibernate.show_sql", "true");
        sessionFactory.setHibernateProperties(hibernateproperties);

        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() { // вместо <bean id="transactionManager"
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactory().getObject()); // вместо <property name="sessionFactory"
        return manager;
    }

}
