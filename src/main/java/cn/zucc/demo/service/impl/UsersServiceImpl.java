package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.bean.Users;
import cn.zucc.demo.dao.UsersDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 14:14
 **/
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;

    @Override
    public boolean addUsers(String uName, String pwd) throws Exception {
        Users old = usersDao.findByUName(uName);
        if (old == null) {
            Users users = new Users();
            users.setUName(uName);
            users.setPwd(pwd);
            usersDao.save(users);
            return false;
        }else {
            throw new TheaterException(ResultMapping.REPEAT_USERNAME);
        }
    }
    @Override
    public Users findByUName(String uName) {
        return usersDao.findByUName(uName);
    }
}
