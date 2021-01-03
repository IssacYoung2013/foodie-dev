package com.issac.tccdemo.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author: ywy
 * @date: 2020-12-28
 * @desc:
 */
@Configuration
@MapperScan(value = "com.issac.tccdemo.db106.dao",sqlSessionFactoryRef = "sqlSessionFactoryBean106")
public class ConfigDb106 {

    @Bean("db106")
    public DataSource db106() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://192.168.1.106:3306/xa106?useSSL=false&characterEncoding=UTF-8");

       return dataSource;
    }

    @Bean("sqlSessionFactoryBean106")
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("db106") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("mybatis/db106/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean("tm106")
    public PlatformTransactionManager transactionManager(@Qualifier("db106") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
