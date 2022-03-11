package com.itheima.app.interceptor;

import com.itheima.domain.db.User;

public class UserHolder {
    private static final ThreadLocal<User> TLUSER= new ThreadLocal<>();

    //绑定
    public static void set(User user){
        TLUSER.set(user);
    }
    //解绑
    public static void remove(){
        TLUSER.remove();
    }
    //获取
    public static User get(){
      return  TLUSER.get();
    }

}
