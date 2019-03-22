package com.sentisc.zk.base.core.fate;

import com.sentisc.zk.base.core.ZookeeperOperations;
import com.sentisc.zk.base.core.fate.model.RedisConfig;
import com.sentisc.zk.base.proxy.ZookeeperProxyFactroy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;

import java.util.UUID;

import static org.junit.Assert.*;

public class ZookeeperOperationsImplTest {
    ZookeeperOperations proxy;
    RedisConfig redisConfig;
    @org.junit.Before
    public void setUp() throws Exception {
        proxy = ZookeeperProxyFactroy.getInstance();
        redisConfig = new RedisConfig("172.1.1.0", UUID.randomUUID()+"");
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void createPermanentNodeWithACLs() {
    }

    @org.junit.Test
    public void createTemporaryNodeWithACLs() {
    }

    @org.junit.Test
    public void createPermanentSequentialNodeWithACLs() {
    }

    @org.junit.Test
    public void createTemporarySequentialNodeWithACLs() {
    }

    @org.junit.Test
    public void createPermanentNode() {
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.createPermanentNode("/node1",redisConfig);
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.getClient().close();

    }

    @org.junit.Test
    public void createTemporaryNode() {
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.createPermanentNode("/node1",redisConfig);
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.getClient().close();
    }

    @org.junit.Test
    public void createPermanentSequentialNode() {
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.createPermanentSequentialNode("/node1",redisConfig);
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.getClient().close();
    }

    @org.junit.Test
    public void createTemporarySequentialNode() {
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.createTemporarySequentialNode("/node1",redisConfig);
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.getClient().close();
    }

    @org.junit.Test
    public void deleteNode() {
    }

    @org.junit.Test
    public void setNodeACL() {
    }

    @org.junit.Test
    public void getNodeACL() {
    }

    @org.junit.Test
    public void setNodeData() {
    }

    @org.junit.Test
    public void setNodeDataAsyn() {
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.createTemporarySequentialNode("/node1",redisConfig);
        proxy.setNodeDataAsyn("/node1", redisConfig, new CuratorListener() {
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                if(curatorEvent.getType() == CuratorEventType.SET_DATA){
                    System.out.println("设置成功");
                }
            }
        });
        if(proxy.existNode("/node1")!=null){
            proxy.deleteNode("/node1");
        }
        proxy.getClient().close();
    }

    @org.junit.Test
    public void setNodeDataAsynWithCallback() {
    }

    @org.junit.Test
    public void setNodeDataAsynWithCallback1() {
    }

    @org.junit.Test
    public void getChildrenNodes() {
    }

    @org.junit.Test
    public void existNode() {
        proxy.existNode("/node1");
    }

    @org.junit.Test
    public void getNodeData() {
    }

    @org.junit.Test
    public void usingWatcher() {
    }

    @org.junit.Test
    public void addNodeCacheListener() {
    }

    @org.junit.Test
    public void addPathChildrenCacheListener() {
    }

    @org.junit.Test
    public void addSharedCounter() {
    }

    @org.junit.Test
    public void transaction() {
    }
}