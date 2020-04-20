package cn.zucc.demo.service;

import cn.zucc.demo.bean.Users;

public interface UsersService {
    /**
     * 添加影院
     * @param uName
     * @param pwd
     * @return
     * @throws Exception
     */
    boolean addUsers( String uName, String pwd) throws Exception;

    /**
     * 根据用户名获得用户
     * @param uName
     * @return
     */
    Users findByUName(String uName);

}
