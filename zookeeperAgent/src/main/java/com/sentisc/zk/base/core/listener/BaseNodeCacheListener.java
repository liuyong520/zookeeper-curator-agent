package com.sentisc.zk.base.core.listener;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseNodeCacheListener implements NodeCacheListener {
    public static final Logger log = LoggerFactory.getLogger(BaseNodeCacheListener.class);
    protected NodeCache nodeCache;

    public BaseNodeCacheListener(NodeCache nodeCache) {
        this.nodeCache = nodeCache;
    }

    public void nodeChanged() throws Exception {
        this.onNodeChanged();
    }
    abstract public void onNodeChanged();

    public NodeCache getNodeCache() {
        return nodeCache;
    }

    public void setNodeCache(NodeCache nodeCache) {
        this.nodeCache = nodeCache;
    }

    public BaseNodeCacheListener() {
    }
}
