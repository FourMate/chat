package com.brokepal.nighty.login.security;

import com.brokepal.nighty.login.core.cache.Cache;
import com.brokepal.nighty.login.core.util.MD5Util;
import com.brokepal.nighty.login.security.constant.SecurityConstant;
import com.brokepal.nighty.login.security.dto.IsLoginResult;
import com.brokepal.nighty.login.security.storage_object.LoginFailInfo;

import java.util.Date;

/**
 * Created by chenchao on 17/4/16.
 */
public class LoginHelper {

    public static void putPrivateKey(String sessionId, String privateKey){
        Cache.put(SecurityConstant.PRIVATE_KEY_PRE, sessionId, privateKey);
    }

    public static String getPrivateKey(String sessionId){
        return Cache.get(SecurityConstant.PRIVATE_KEY_PRE, sessionId);
    }

    public static boolean isLocked(String username){
        LoginFailInfo info = Cache.get(SecurityConstant.LOGIN_FAIL_PRE, username);
        if (info == null){
            info = new LoginFailInfo();
            Cache.put(SecurityConstant.LOGIN_FAIL_PRE, username, info);
        }
        if (info.getFailCount() <= SecurityConstant.TRY_COUNT)
            return false;

        if (new Date().getTime() - info.getLastLockTime().getTime() > SecurityConstant.LOCK_TIME * 60 * 1000){ //已经过了锁定时间
            info.setFailCount(0);
            return false;
        }

        return true;
    }

    public static void addFailCount(String username){
        LoginFailInfo info = Cache.get(SecurityConstant.LOGIN_FAIL_PRE, username);
        if (info == null){
            info = new LoginFailInfo();
            Cache.put(SecurityConstant.LOGIN_FAIL_PRE, username, info);
        }
        int failCount = info.getFailCount() + 1;
        info.setFailCount(failCount);
        if (failCount == SecurityConstant.TRY_COUNT){
            info.setLastLockTime(new Date());
        }
    }

    public static void clearFailInfo(String username){
        LoginFailInfo info = Cache.get(SecurityConstant.LOGIN_FAIL_PRE, username);
        info.setFailCount(0);
        info.setLastLockTime(null);
        Cache.remove(SecurityConstant.LOGIN_FAIL_PRE, username);
    }


    public static void login(String username, String sessionId, String token, boolean keepPassword){
        Cache.longPut(SecurityConstant.LAST_LOGIN_PRE, token, sessionId); //将<token,sessionId>存入缓存，作为最近一次sessionId
        if (keepPassword)
            Cache.longPut(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId, token);
        else
            Cache.put(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId, token);
    }

    public static void logout(String sessionId){
        Cache.remove(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId);
    }

    public static IsLoginResult isLogin(String sessionId, String token){
        String lastSessionId = Cache.get(SecurityConstant.LAST_LOGIN_PRE, token);
        if (sessionId.equals(lastSessionId)){
            if (token.equals(Cache.get(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId))){
                resetLoginExpires(sessionId, token);
                return IsLoginResult.buildTrueInstance();
            }
            else
                return IsLoginResult.buildFalseInstance("登录信息已过期，请重新登录");
        }
        else{
            if (Cache.has(SecurityConstant.LOGIN_SUCCESS_PRE, lastSessionId))
                return IsLoginResult.buildFalseInstance("该账号在其他地方登录，请重新登录");
            else if (token.equals(Cache.get(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId))){
                resetLoginExpires(sessionId, token);
                return IsLoginResult.buildTrueInstance();
            }
            else
                return IsLoginResult.buildFalseInstance("还未登录，请登录");
        }
    }

    public static void resetLoginExpires(String sessionId, String token){
        Cache.longPut(SecurityConstant.LAST_LOGIN_PRE, token, (String) Cache.get(SecurityConstant.LAST_LOGIN_PRE, token));
        String tokenInCache = Cache.get(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId);
        if (tokenInCache == null){ // 说明token存在longCache中
            Cache.longPut(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId, (String) Cache.get(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId));
        }
        else {
            Cache.put(SecurityConstant.LOGIN_SUCCESS_PRE, sessionId, tokenInCache);
        }
    }

    public static String MD5Encrypt(String password, String salt){
        return MD5Util.string2MD5(MD5Util.string2MD5(password) + salt);
    }
}
