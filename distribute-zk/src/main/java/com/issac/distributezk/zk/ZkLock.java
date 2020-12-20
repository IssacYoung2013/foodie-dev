package com.issac.distributezk.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-12-20
 * @desc:
 */
@Slf4j
public class ZkLock implements AutoCloseable, Watcher {

    private ZooKeeper zooKeeper;
    private String zNode;

    public ZkLock() throws IOException {
        zooKeeper = new ZooKeeper("localhost:2181", 10000, this);
    }

    public boolean getLock(String businessCode) {
        try {
            // 创建业务根节点
            Stat stat = zooKeeper.exists("/" + businessCode, false);
            if (stat == null) {
                zooKeeper.create("/" + businessCode, businessCode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            // 创建瞬时有序节点 /order/order_0000001
            zNode = zooKeeper.create("/" + businessCode + "/" + businessCode + "_", businessCode.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 获取业务节点所有的子节点
            List<String> childrenNodes = zooKeeper.getChildren("/" + businessCode, false);
            // 排序
            Collections.sort(childrenNodes);
            String firstNode = childrenNodes.get(0);
            if (zNode.endsWith(firstNode)) {
                return true;
            }
            // 不是第一个子节点，则监听前一节点
            String lastNode = firstNode;
            for (String childrenNode : childrenNodes) {
                if (zNode.endsWith(childrenNode)) {
                    zooKeeper.exists("/" + businessCode + "/" + lastNode, true);
                    break;
                } else {
                    lastNode = childrenNode;
                }
            }

            // 阻塞
            synchronized (this) {
                wait();
            }
            return true;

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        zooKeeper.delete(zNode,-1);
        zooKeeper.close();
        log.info("我已经释放了锁");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType() == Event.EventType.NodeDeleted) {
            synchronized (this) {
                // 唤起
                notify();
            }
        }
    }
}
