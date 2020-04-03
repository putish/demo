package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.dao.TheaterDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddTheaterRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.util.ValueUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @description: 影院服务
 * @author: hjj
 * @create: 2020-03-06 17:36
 */
@Service
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    private TheaterDao theaterDao;

    @Override
    public boolean addTheater(AddTheaterRequest addTheaterRequest) throws Exception {
        Theater theater=new Theater();
        BeanUtils.copyProperties(addTheaterRequest,theater);
        String contactphone=addTheaterRequest.getTContactPhone();
        String legalphone=addTheaterRequest.getTLegalPhone();
        if(!ValueUtil.checkTel(legalphone)||!ValueUtil.checkTel(contactphone)){
            throw new TheaterException(ResultMapping.PHONE_FALUT);
        }
        theater.setCreateTime(new Date());
        theater.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        theaterDao.save(theater);
        return true;
    }

    @Override
    public boolean deleteTheater(Long tId) {
        return false;
    }
}

