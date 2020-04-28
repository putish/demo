package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.dao.TheaterDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddTheaterRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.TheaterService;
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
    public boolean addTheater(AddTheaterRequest request) throws Exception {
        Theater old=theaterDao.findByTName(request.getTName());
        if(old==null || old.getDeleteFlag()==DeleteFlagEnum.IS_DELETE.getValue()) {
            Theater theater = new Theater();
            BeanUtils.copyProperties(request, theater);
            String contactphone = request.getTContactPhone();
            String legalphone = request.getTLegalPhone();
            if (!ValueUtil.checkTel(legalphone) || !ValueUtil.checkTel(contactphone)) {//判断电话格式
                throw new TheaterException(ResultMapping.PHONE_FALUT);//格式错误
            }
            theater.setCreateTime(new Date());
            theater.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
            theaterDao.save(theater);
            return true;
        }else {
            throw new TheaterException(ResultMapping.REPEAT_USERNAME);
        }
    }

    @Override
    public boolean deleteTheater(Long tId) {
        return false;
    }

    @Override
    public boolean creatSchedule() {
        Date now =new Date();

        return false;
    }

    @Override
    public Theater findByTName(String tName) {
        return theaterDao.findByTName(tName);
    }
}

