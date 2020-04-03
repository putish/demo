package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDao extends JpaRepository<Users,Long> {

}
