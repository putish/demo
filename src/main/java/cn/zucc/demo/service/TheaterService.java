package cn.zucc.demo.service;

import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.form.AddTheaterRequest;

/**
 * @description: 影院服务
 * @author: hjj
 * @create: 2020-03-06 17:43
 */
public interface TheaterService {
    /**
     * 添加影院
     * @param addTheaterRequest
     * @return
     */
    boolean addTheater(AddTheaterRequest addTheaterRequest) throws Exception;

    /**
     * 删除影院
     * @param tId 影院id
     * @return
     */
    boolean deleteTheater(Long tId);

    boolean creatSchedule();

    Theater findByTName(String tName);
//    List<>
}
