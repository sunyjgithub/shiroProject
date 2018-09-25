package com.atguigu.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

  DruidDataSource dataSource=new DruidDataSource();

    {dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }


    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        //自定义认证sql查询语句：
        String sql="select password from users_test where username=?";
        jdbcRealm.setAuthenticationQuery(sql);

        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();


        UsernamePasswordToken token=new UsernamePasswordToken("mingming","123456");
        subject.login(token);

        System.out.println("isAuthenticated: "+ subject.isAuthenticated());

//        subject.logout();

//        System.out.println("isAuthenticated: "+ subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:delete");

    }
}
