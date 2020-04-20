package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDao extends JpaRepository<Users,Long> {
    /**
     * 根据用户名得到用户
     * @param UName
     * @return
     */
    Users findByUName(String UName);
}
