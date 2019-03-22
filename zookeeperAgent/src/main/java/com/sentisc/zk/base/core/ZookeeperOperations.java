package com.sentisc.zk.base.core;

import com.sentisc.zk.base.model.CommonJsonBase;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.List;

public interface ZookeeperOperations {
    /**
     * 创建永久节点，带上权限
     * @param nodePath
     * @param data
     */
    void createPermanentNodeWithACLs(String nodePath, String data, List<ACL> acls);

    /**
     * 创建临时节点，带上访问权限
     * @param nodePath
     * @param data
     */
    void createTemporaryNodeWithACLs(String nodePath,String data,List<ACL> acls);

    /**
     * 创建永久顺序节点，带上访问权限
     * @param nodePath
     * @param data
     */
    void createPermanentSequentialNodeWithACLs(String nodePath,String data,List<ACL> acls);



    /**
     * 创建临时顺序节点，带上访问权限
     * @param nodePath
     * @param data
     */
    void createTemporarySequentialNodeWithACLs(String nodePath,String data,List<ACL> acls);

    /**
     * 创建永久节点，带上权限
     * @param nodePath
     * @param data
     */
    void createPermanentNodeWithACLs(String nodePath, CommonJsonBase data, List<ACL> acls);

    /**
     * 创建临时节点，带上访问权限
     * @param nodePath
     * @param data
     */
    void createTemporaryNodeWithACLs(String nodePath,CommonJsonBase data,List<ACL> acls);

    /**
     * 创建永久顺序节点，带上访问权限
     * @param nodePath
     * @param data
     */
    void createPermanentSequentialNodeWithACLs(String nodePath,CommonJsonBase data,List<ACL> acls);



    /**
     * 创建临时顺序节点，带上访问权限
     * @param nodePath
     * @param data
     */
    void createTemporarySequentialNodeWithACLs(String nodePath,CommonJsonBase data,List<ACL> acls);
    /**
     * 创建永久节点，任何访问权限都能够
     * @param nodePath
     * @param data
     */
    void createPermanentNode(String nodePath,String data);

    /**
     * 创建临时节点，任何访问权限
     * @param nodePath
     * @param data
     */
    void createTemporaryNode(String nodePath,String data);

    /**
     * 创建永久顺序节点，任何访问权限
     * @param nodePath
     * @param data
     */
    void createPermanentSequentialNode(String nodePath,String data);



    /**
     * 创建临时顺序节点，任何访问权限
     * @param nodePath
     * @param data
     */
    void createTemporarySequentialNode(String nodePath,String data);

    /**
     * 创建永久节点，任何访问权限都能够
     * @param nodePath
     * @param data
     */
    void createPermanentNode(String nodePath,CommonJsonBase data);

    /**
     * 创建临时节点，任何访问权限
     * @param nodePath
     * @param data
     */
    void createTemporaryNode(String nodePath,CommonJsonBase data);

    /**
     * 创建永久顺序节点，任何访问权限
     * @param nodePath
     * @param data
     */
    void createPermanentSequentialNode(String nodePath,CommonJsonBase data);

    /**
     * 创建临时顺序节点，任何访问权限
     * @param nodePath
     * @param data
     */
    void createTemporarySequentialNode(String nodePath,CommonJsonBase data);
    /**
     * 删除节点
     * @param nodePath
     */
    void deleteNode(String nodePath);

    /**
     * 修改节点数据
     * @param nodePath
     * @param data
     */
    Stat setNodeData(String nodePath, String data);

    /**
     * 设置指定节点ACL权限
     * @param nodePath
     * @param acls
     * @return
     */
    Stat setNodeACL(String nodePath, List<ACL> acls);

    /**
     * 获取指定节点的ACL权限
     * @param node
     * @return
     */
    List<ACL> getNodeACL(String node);
    /**
     * 修改节点数据
     * @param nodePath
     * @param object
     */
    Stat setNodeData(String nodePath, CommonJsonBase object);

    /**
     * 异步设置数据
     * @param node
     * @param playload
     * @param curatorListener
     */
    void setNodeDataAsyn(String node, byte[] playload, CuratorListener curatorListener);

    /**
     * 异步设置数据
     * @param node
     * @param data
     * @param curatorListener
     */
    void setNodeDataAsyn(String node, CommonJsonBase data, CuratorListener curatorListener);

    /**
     * 异步设置数据
     * @param node
     * @param callback
     * @param playload
     */
    void setNodeDataAsynWithCallback(String node, BackgroundCallback callback,byte[] playload);

    /**
     * 异步设置数据
     * @param node
     * @param callback
     * @param data
     */
    void setNodeDataAsynWithCallback(String node, BackgroundCallback callback,CommonJsonBase data);
    /**
     * 获取所有子节点
     * @param nodePath
     * @return
     */
    List<String> getChildrenNodes(String nodePath);

    /**
     * 检查节点是否存在
     * @param node
     * @return
     */
    Stat existNode(String node);

    /**
     * 获取节点数据
     * @param node
     * @return
     */
    byte[] getNodeData(String node);

    /**
     * 获取节点数据
     * @param node
     * @param <T>
     * @return
     */
    <T> T getNodeData(String node,Class<T> tClass);
    /**
     * 给节点添加watcher 只会监听一次
     * @param node
     * @param watcher
     */
    void usingWatcher(String node, CuratorWatcher watcher);

    /**
     * 添加NodeCacheListerner
     * @param node
     * @param nodeCacheListener
     */
    void addNodeCacheListener(String node, NodeCacheListener nodeCacheListener);

    /**
     * 添加PathChildrenCacheListener
     * @param node
     * @param childrenCache
     */
    void addPathChildrenCacheListener(String node, PathChildrenCacheListener childrenCache, PathChildrenCache.StartMode startMode);

    /**
     *
     * @param node
     * @param sharedCountListener
     */
    void addSharedCounter(String node, SharedCountListener sharedCountListener);

    /**
     * 事务处理函数
     * @param oops
     * @return
     */
    Collection<CuratorTransactionResult> transaction(Collection<CuratorOp> oops);


    CuratorFramework getClient();
}
