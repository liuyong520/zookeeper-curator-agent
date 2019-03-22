package com.sentisc.zk.base.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.*;

import java.util.Properties;

public class RetryPolicyFactory {
    public static final String EXPONENTIALBACKOFFRETRY = "exponentialBackoffRetry";
    public static final String RETRYNTIMES = "retryNTimes";
    public static final String RETRYONETIME ="retryOneTime";
    public static final String RETRYFOREVER="retryForever";
    public static final String RETRYUNTILELAPSED = "retryUntilElapsed";
    public static enum RetryPolicyType{
        ExponentialBackoffRetry(EXPONENTIALBACKOFFRETRY),RetryNTimes(RETRYNTIMES),RetryOneTime(RETRYONETIME),
        RetryForever(RETRYFOREVER),RetryUntilElapsed(RETRYUNTILELAPSED);
        private RetryPolicyType(String name){
            this.name = name;
        }
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static RetryPolicyType valueof(String name){
        if(EXPONENTIALBACKOFFRETRY.equals(name)){
            return RetryPolicyType.ExponentialBackoffRetry;
        }else if(RETRYNTIMES.equals(name)){
            return RetryPolicyType.RetryNTimes;
        }else if(RETRYONETIME.equals(name)){
            return RetryPolicyType.RetryOneTime;
        }else if(RETRYFOREVER.equals(name)){
            return RetryPolicyType.RetryForever;
        }else if(RETRYUNTILELAPSED.equals(name)){
            return RetryPolicyType.RetryUntilElapsed;
        }
        return null;
    }
    public static RetryPolicy getRetryProlicy(RetryPolicyType type, Properties props){
        switch (type){
            case ExponentialBackoffRetry: {
                int baseSleepTimeMs = 1000;
                if(props.containsKey("zookeeper.cluster.exponentialBackoffRetry.baseSleepTimeMs")) {
                     baseSleepTimeMs = Integer.parseInt(props.getProperty("zookeeper.cluster.exponentialBackoffRetry.baseSleepTimeMs"));
                }
                int maxRetries = 5;
                if(props.containsKey("zookeeper.cluster.exponentialBackoffRetry.maxRetries")) {
                    maxRetries = Integer.parseInt(props.getProperty("zookeeper.cluster.exponentialBackoffRetry.maxRetries"));
                }
                return new ExponentialBackoffRetry(baseSleepTimeMs,maxRetries);
            }
            case RetryForever:{
                int retryIntervalMs = 1000;
                if(props.containsKey("zookeeper.cluster.retryForever.retryIntervalMs")) {
                    retryIntervalMs = Integer.parseInt(props.getProperty("zookeeper.cluster.retryForever.retryIntervalMs"));
                }

                return new RetryForever(retryIntervalMs);
            }
            case RetryNTimes:{
                int sleepMsBetweenRetries = 1000;
                if(props.containsKey("zookeeper.cluster.retryNTimes.sleepMsBetweenRetries")) {
                    sleepMsBetweenRetries = Integer.parseInt(props.getProperty("zookeeper.cluster.retryNTimes.sleepMsBetweenRetries"));
                }
                int maxRetries = 5;
                if(props.containsKey("zookeeper.cluster.retryNTimes.maxRetries")) {
                    maxRetries = Integer.parseInt(props.getProperty("zookeeper.cluster.retryNTimes.maxRetries"));
                }
                return new RetryNTimes(maxRetries,sleepMsBetweenRetries);
            }
            case RetryOneTime:{
                int sleepMsBetweenRetries = 1000;
                if(props.containsKey("zookeeper.cluster.retryOneTime.sleepMsBetweenRetries")) {
                    sleepMsBetweenRetries = Integer.parseInt(props.getProperty("zookeeper.cluster.retryOneTime.sleepMsBetweenRetries"));
                }
                return new RetryOneTime(sleepMsBetweenRetries);
            }
            case RetryUntilElapsed:{
                int sleepMsBetweenRetries = 3000;
                if(props.containsKey("zookeeper.cluster.retryUntilElapsed.sleepMsBetweenRetries")) {
                    sleepMsBetweenRetries = Integer.parseInt(props.getProperty("zookeeper.cluster.retryUntilElapsed.sleepMsBetweenRetries"));
                }
                int maxElapsedTimeMs = 300000;
                if(props.containsKey("zookeeper.cluster.retryUntilElapsed.maxElapsedTimeMs")) {
                    maxElapsedTimeMs = Integer.parseInt(props.getProperty("zookeeper.cluster.retryUntilElapsed.maxElapsedTimeMs"));
                }
                return new RetryUntilElapsed(maxElapsedTimeMs,sleepMsBetweenRetries);
            }
            default:{
                return new ExponentialBackoffRetry(1000,5);
            }
        }
    }
}
