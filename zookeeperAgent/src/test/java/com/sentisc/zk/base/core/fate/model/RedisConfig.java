package com.sentisc.zk.base.core.fate.model;

import com.sentisc.zk.base.model.CommonJsonBase;

public class RedisConfig extends CommonJsonBase {
    private String ips;
    private String connectionTimeOut;

    public RedisConfig(String ips, String connectionTimeOut) {
        this.ips = ips;
        this.connectionTimeOut = connectionTimeOut;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(String connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }
}
