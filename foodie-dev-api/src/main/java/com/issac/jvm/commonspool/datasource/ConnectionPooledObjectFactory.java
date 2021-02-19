package com.issac.jvm.commonspool.datasource;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.*;

/**
 * @author: ywy
 * @date: 2021-02-04
 * @desc:
 */
public class ConnectionPooledObjectFactory implements
        PooledObjectFactory<MyConnection> {

    private ObjectPool<MyConnection> objectPool;

    public ObjectPool<MyConnection> getObjectPool() {
        return objectPool;
    }

    public void setObjectPool(ObjectPool<MyConnection> objectPool) {
        this.objectPool = objectPool;
    }

    @Override
    public PooledObject<MyConnection> makeObject() throws Exception {
        Class<?> jdbcClass = Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/foodie-shop-dev?useUnicode=true&&characterEncoding=utf8",
                "root",
                "rootroot"
        );
        MyConnection myConnection = new MyConnection();
        myConnection.setConnection(connection);
        myConnection.setObjectPool(objectPool);
        return new DefaultPooledObject<>(myConnection);
    }

    @Override
    public void destroyObject(PooledObject<MyConnection> pooledObject) throws Exception {
        pooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<MyConnection> pooledObject) {
        Connection connection = pooledObject.getObject();
        try {
            PreparedStatement statement = connection.prepareStatement("select 1");
            ResultSet resultSet = statement.executeQuery();
            int i = resultSet.getInt(1);
            return i == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<MyConnection> pooledObject) throws Exception {
        // 可以把 connection 额外配置放到这里
    }

    @Override
    public void passivateObject(PooledObject<MyConnection> pooledObject) throws Exception {
        // 钝化
        MyConnection myConnection = pooledObject.getObject();
        Statement statement = myConnection.getStatement();
        if (statement != null) {
            statement.close();
        }
    }
}
