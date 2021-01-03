package com.issac.xademo.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.jta.JtaTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.UserTransaction;
import java.io.IOException;

/**
 * @author: ywy
 * @date: 2020-12-28
 * @desc:
 */
@Configuration
@MapperScan(value = "com.issac.xademo.db106.dao",sqlSessionFactoryRef = "sqlSessionFactoryBean106")
public class ConfigDb106 {

    @Bean("db106")
    public DataSource db106() {
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setUser("root");
        xaDataSource.setPassword("root");
        xaDataSource.setUrl("jdbc:mysql://192.168.1.106:3306/xa106?useSSL=false");

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setUniqueResourceName("db106");
        return atomikosDataSourceBean;
    }

    @Bean("sqlSessionFactoryBean106")
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("db106") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("mybatis/db106/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean("xaTransaction")
    public JtaTransactionManager jtaTransactionManager() {
        UserTransaction userTransaction = new UserTransactionImp();
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        return new JtaTransactionManager(userTransaction,userTransactionManager);
    }
}
