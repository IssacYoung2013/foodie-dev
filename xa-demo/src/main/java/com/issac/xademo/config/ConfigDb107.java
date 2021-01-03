package com.issac.xademo.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author: ywy
 * @date: 2020-12-28
 * @desc:
 */
@Configuration
@MapperScan(value = "com.issac.xademo.db107.dao",sqlSessionFactoryRef = "sqlSessionFactoryBean107")
public class ConfigDb107 {

    @Bean("db107")
    public DataSource db107() {
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setUser("root");
        xaDataSource.setPassword("root");
        xaDataSource.setUrl("jdbc:mysql://192.168.1.107:3306/xa107?useSSL=false");

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setUniqueResourceName("db107");

        return atomikosDataSourceBean;
    }

    @Bean("sqlSessionFactoryBean107")
    public SqlSessionFactoryBean sqlSessionFactory107(@Qualifier("db107") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("mybatis/db107/*.xml"));
        return sqlSessionFactoryBean;
    }
}
