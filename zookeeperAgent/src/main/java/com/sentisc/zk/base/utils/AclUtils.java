package com.sentisc.zk.base.utils;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

public class AclUtils {
    public static String getDigestUserPwd(String id) throws NoSuchAlgorithmException {
        // 加密明文密码
        return DigestAuthenticationProvider.generateDigest(id);
    }
}
