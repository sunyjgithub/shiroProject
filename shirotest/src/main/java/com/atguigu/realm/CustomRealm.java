package com.atguigu.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    private Map<String,String> userMap=new HashMap<>(16);

    {
        userMap.put("Mark","123456");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Set<String> roles=new HashSet<>();
        roles.add("admin");
        roles.add("user");

        Set<String> permissions=new HashSet<>();
        permissions.add("user:select");
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

       //获取请求主体传来的用户名
        String principal = (String) token.getPrincipal();

        //从数据库内获取password根据用户名
        String password= getPasswordByUsername(principal);

        if (password==null){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo= new SimpleAuthenticationInfo("Mark","123456",this.getName());

        return simpleAuthenticationInfo;
    }


    //模拟从数据库中获取密码的方法

    private String getPasswordByUsername(String username){
   return this.userMap.get(username);
    }
}
