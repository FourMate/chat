package com.brokepal.nighty.login.sys.service;

import com.brokepal.nighty.login.security.LoginHelper;
import com.brokepal.nighty.login.security.dto.IsLoginResult;
import com.brokepal.nighty.login.sys.persist.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by chenchao on 2018/5/9.
 */
@Service
@Transactional
public class LoginService {
    @Autowired
    private UserDao userDao;
    

    public void putPrivateKey(String sessionId, String privateKey){
        LoginHelper.putPrivateKey(sessionId, privateKey);
    }

    public String getPrivateKey(String sessionId){
        return LoginHelper.getPrivateKey(sessionId);
    }

    public boolean isLocked(String username){
        return LoginHelper.isLocked(username);
    }

    public void addFailCount(String username){
        LoginHelper.addFailCount(username);
    }

    public void clearFailInfo(String username){
        LoginHelper.clearFailInfo(username);
    }


    public void login(String username, String sessionId, String token, boolean keepPassword){
        LoginHelper.login(username, sessionId, token, keepPassword);
        //修改lastLoginTime
        userDao.updateLastLoginTime(username, new Date());
    }

    public void logout(String sessionId){
        LoginHelper.logout(sessionId);
    }

    public IsLoginResult isLogin(String sessionId, String token){
        return LoginHelper.isLogin(sessionId, token);
    }

    public void resetLoginExpires(String sessionId, String token){
        LoginHelper.resetLoginExpires(sessionId, token);
    }

    public String MD5Encrypt(String password, String salt){
        return LoginHelper.MD5Encrypt(password, salt);
    }
}
