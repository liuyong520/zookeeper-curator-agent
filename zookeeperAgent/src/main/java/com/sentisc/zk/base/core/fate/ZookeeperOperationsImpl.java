package com.sentisc.zk.base.core.fate;

import com.alibaba.fastjson.JSONObject;
import com.sentisc.zk.base.core.ZookeeperOperations;
import com.sentisc.zk.base.core.listener.BaseNodeCacheListener;
import com.sentisc.zk.base.exception.*;
import com.sentisc.zk.base.factory.RetryPolicyFactory;
import com.sentisc.zk.base.model.CommonJsonBase;
import com.sentisc.zk.base.utils.ResourceUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ZookeeperOperationsImpl implements ZookeeperOperations {
    private static final String ZOOKEEPER_PROPERTIES = "classpath*:properties/zookeeper.properties";
    private static final String DEAFAUT_PATH_PROPERTIES = "file:properties/zookeeper.properties";
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperOperationsImpl.class);
    private static CuratorFramework client;
    private static CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();

    public void createPermanentNodeWithACLs(String nodePath, String data, List<ACL> acls) {
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(acls)
                    .forPath(nodePath,data.getBytes());
        } catch (Exception e) {
            throw new CreateNodeException("创建节点异常",e.getCause());
        }
    }

    public void createTemporaryNodeWithACLs(String nodePath, String data, List<ACL> acls) {
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(acls)
                    .forPath(nodePath,data.getBytes());
        } catch (Exception e) {
            throw new CreateNodeException("创建节点异常",e.getCause());
        }
    }

    public void createPermanentSequentialNodeWithACLs(String nodePath, String data, List<ACL> acls) {
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .withACL(acls)
                    .forPath(nodePath,data.getBytes());
        } catch (Exception e) {
            throw new CreateNodeException("创建节点异常",e.getCause());
        }
    }

    public void createTemporarySequentialNodeWithACLs(String nodePath, String data, List<ACL> acls) {
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .withACL(acls)
                    .forPath(nodePath,data.getBytes());
        } catch (Exception e) {
            throw new CreateNodeException("创建节点异常",e.getCause());
        }
    }

    public void createPermanentNodeWithACLs(String nodePath, CommonJsonBase data, List<ACL> acls) {
        this.createPermanentSequentialNodeWithACLs(nodePath,data.convertToString(),acls);
    }

    public void createTemporaryNodeWithACLs(String nodePath, CommonJsonBase data, List<ACL> acls) {
        this.createTemporaryNodeWithACLs(nodePath,data.convertToString(),acls);
    }

    public void createPermanentSequentialNodeWithACLs(String nodePath, CommonJsonBase data, List<ACL> acls) {
        this.createPermanentSequentialNodeWithACLs(nodePath,data.convertToString(),acls);
    }

    public void createTemporarySequentialNodeWithACLs(String nodePath, CommonJsonBase data, List<ACL> acls) {
        this.createTemporarySequentialNodeWithACLs(nodePath,data.convertToString(),acls);
    }

    public void createPermanentNode(String nodePath, String data) {
        this.createPermanentNodeWithACLs(nodePath,data,ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    public void createTemporaryNode(String nodePath, String data) {
       this.createTemporaryNodeWithACLs(nodePath,data,ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    public void createPermanentSequentialNode(String nodePath, String data) {
        this.createPermanentSequentialNodeWithACLs(nodePath,data,ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    public void createTemporarySequentialNode(String nodePath, String data) {
        this.createTemporarySequentialNodeWithACLs(nodePath,data,ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    public void createPermanentNode(String nodePath, CommonJsonBase data) {
        this.createPermanentNode(nodePath,data.convertToString());
    }

    public void createTemporaryNode(String nodePath, CommonJsonBase data) {
        this.createTemporaryNode(nodePath,data.convertToString());
    }

    public void createPermanentSequentialNode(String nodePath, CommonJsonBase data) {
        this.createPermanentSequentialNode(nodePath,data.convertToString());
    }

    public void createTemporarySequentialNode(String nodePath, CommonJsonBase data) {
        this.createTemporarySequentialNode(nodePath,data.convertToString());
    }

    public void deleteNode(String nodePath) {
        try {
            Stat stat = this.existNode(nodePath);
            client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(nodePath);
        }catch (Exception e){
            throw new DeleteNodeException("删除节点异常",e.getCause());
        }

    }

    public Stat setNodeData(String nodePath, String data) {
        try {
            Stat stat = this.existNode(nodePath);
            return client.setData().withVersion(stat.getVersion()).forPath(nodePath,data.getBytes());
        }catch (NotExistNodeException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new UpdateNodeException("更新节点失败",e.getCause());
        }
        return null;
    }

    public Stat setNodeACL(String nodePath, List<ACL> acls) {
        try {
            Stat stat = this.existNode(nodePath);
            stat = client.setACL().withVersion(stat.getVersion()).withACL(acls).forPath(nodePath);
            return stat;
        }catch (Exception e){
            throw new RuntimeException("设置ACL权限异常",e.getCause());
        }

    }

    public List<ACL> getNodeACL(String node) {
        try {
            Stat stat = this.existNode(node);
            return client.getACL().storingStatIn(stat).forPath(node);
        }catch (Exception e){
            throw new RuntimeException("获取ACL权限异常",e.getCause());
        }
    }

    public Stat setNodeData(String nodePath, CommonJsonBase data) {
        return this.setNodeData(nodePath,data.convertToString());
    }

    public void setNodeDataAsyn(String node, byte[] playload, CuratorListener curatorListener) {
        client.getCuratorListenable().addListener(curatorListener);
        try {
            client.setData().inBackground().forPath(node,playload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNodeDataAsyn(String node, CommonJsonBase data, CuratorListener curatorListener) {
        this.setNodeDataAsyn(node,data.convertToString().getBytes(),curatorListener);
    }


    public void setNodeDataAsynWithCallback(String node, BackgroundCallback callback, byte[] playload) {
        try {
            client.setData().inBackground(callback).forPath(node, playload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNodeDataAsynWithCallback(String node, BackgroundCallback callback, CommonJsonBase data) {
        this.setNodeDataAsynWithCallback(node,callback,data.convertToString().getBytes());
    }

    public List<String> getChildrenNodes(String nodePath) {
        try {
            this.existNode(nodePath);
            return client.getChildren().forPath(nodePath);
        }catch (NotExistNodeException e){
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    public Stat existNode(String node) {
        try {
            return client.checkExists().forPath(node);
        }catch (Exception e){
        }
        return null;
    }

    public byte[] getNodeData(String node) {
        Stat stat = existNode(node);
        try {
          return client.getData().storingStatIn(stat).forPath(node);
        } catch (Exception e) {
            throw new RuntimeException("获取节点数据异常");
        }
    }

    public <T> T getNodeData(String node, Class<T> tClass) {
        String ret = new String(this.getNodeData(node));
        return JSONObject.parseObject(ret,tClass);
    }

    public void usingWatcher(String node, CuratorWatcher watcher) {
        existNode(node);
        try {
            client.getData().usingWatcher(watcher).forPath(node);
        }catch (Exception e){
            throw new RuntimeException("添加watcher异常");
        }
    }

    public void addNodeCacheListener(String node, NodeCacheListener nodeCacheListener) {
        try {
            final NodeCache nodeCache = new NodeCache(client,node);
            nodeCache.start(true);
            if(nodeCacheListener instanceof BaseNodeCacheListener){
                BaseNodeCacheListener baseNodeCacheListener = (BaseNodeCacheListener) nodeCacheListener;
                baseNodeCacheListener.setNodeCache(nodeCache);
            }
            nodeCache.getListenable().addListener(nodeCacheListener);
        }catch (Exception e){
            throw new NodeCacheException("创建NodeCache异常",e.getCause());
        }
    }

    public void addPathChildrenCacheListener(String node, PathChildrenCacheListener childrenCacheListener, PathChildrenCache.StartMode startMode) {
       try {
           final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, node, true);
           pathChildrenCache.start(startMode);
           pathChildrenCache.getListenable().addListener(childrenCacheListener);
       }catch (Exception e){
           throw new RuntimeException("创建PathChildrenCache异常",e.getCause());
       }

    }

    public void addSharedCounter(String node, SharedCountListener sharedCountListener) {
        try {
            SharedCount sharedCount = new SharedCount(client,node,0);
            sharedCount.start();
            sharedCount.addListener(sharedCountListener);
        }catch (Exception e){
            throw new RuntimeException("ShareCountListener 异常",e.getCause());
        }
    }

    public Collection<CuratorTransactionResult> transaction(Collection<CuratorOp> oops) {
        List<CuratorOp> list = new LinkedList<CuratorOp>(oops);
        try {
            return client.transaction().forOperations(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CuratorFramework getClient() {
        return client;
    }


    private static final int DEFAULT_CONNECTIONTIMEOUTMS = 20000;

    private static final int DEFAULT_SESSIONTIMEOUTMS = 15000;

    private static final String DEAFAUT_NAMESPACES = "zkClient";

    static{
        Properties props = null;
        try {
            Resource resource = null;
            try{
                resource = ResourceUtils.getLastResource(ZOOKEEPER_PROPERTIES);
            }catch (Exception e){

            }
            if(resource == null){
                resource = ResourceUtils.getLastResource(DEAFAUT_PATH_PROPERTIES);
            }
            props = PropertiesLoaderUtils.loadProperties(resource);
            String zkServerIps = props.getProperty("zookeeper.cluster.zkserverips");
            if (props.containsKey("zookeeper.cluster.connectiontimeoutms")){
                builder.connectionTimeoutMs(Integer.parseInt(props.getProperty("zookeeper.cluster.connectiontimeoutms")));
            } else{
                builder.connectionTimeoutMs(DEFAULT_CONNECTIONTIMEOUTMS);
            }

            if (props.containsKey("zookeeper.cluster.sessiontimeoutms")){
                builder.sessionTimeoutMs(Integer.parseInt(props.getProperty("zookeeper.cluster.sessiontimeoutms")));
            } else{
                builder.sessionTimeoutMs(DEFAULT_SESSIONTIMEOUTMS);
            }

            RetryPolicy retryPolicy = null;
            if (props.containsKey("zookeeper.cluster.retryPolicy")){
                RetryPolicyFactory.RetryPolicyType  retryPolicyType = RetryPolicyFactory.valueof(props.getProperty("zookeeper.cluster.retryPolicy"));
                retryPolicy = RetryPolicyFactory.getRetryProlicy(retryPolicyType,props);
            }
            if(retryPolicy==null){
                builder.retryPolicy(new ExponentialBackoffRetry(1000,5));
            }else {
                builder.retryPolicy(retryPolicy);
            }
            if (props.containsKey("zookeeper.cluster.namespace")){
                builder.namespace(props.getProperty("zookeeper.cluster.namespace"));
            }else {
                builder.namespace(DEAFAUT_NAMESPACES);
            }
            client = builder.connectString(zkServerIps).build();
            client.start();
        } catch (IOException e) {
            throw new LoadZookeeperException(e);
        }
    }

}
