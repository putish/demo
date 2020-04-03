package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.dao.HallDao;
import cn.zucc.demo.dao.MovieDao;
import cn.zucc.demo.dao.ScreenDao;
import cn.zucc.demo.dao.TheaterDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.ScreenService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.vo.ScreenListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 14:12
 **/
@Service
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private ScreenDao screenDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private HallDao hallDao;

    @Autowired
    private TheaterDao theaterDao;

    @Override
    @Transactional
    public boolean addScreen(Long mId, Long hId, Date startTime, Long tId) {
        if(startTime.after(new Date())) {
            Screen screen = new Screen();
            screen.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
            screen.setStartTime(startTime);
            screen.setHId(hId);
            screen.setMId(mId);
            screen.setShowState(ShowStateEnum.IN_SHOW.getValue());
            screen.setTicketCount(0);//已经售出票数
            screen.setTId(tId);
            Movie movie=movieDao.findOne(mId);
            if(movie.getShowState()!=3){
                Theater theater=theaterDao.findOne(tId);
                Date endTime=DateUtil.getEndTime(startTime,movie.getDuration());
                Date spareTime=DateUtil.getSpareTime(endTime,theater.getIntervalTime());
                if (hallDao.findhallTimeTable(hId,startTime,spareTime,tId).size()==0) {
                    screen.setEndTime(endTime);
                    screenDao.save(screen);
                    return true;
                }else {
                    throw new TheaterException(ResultMapping.HAVE_SCREEN);
                }
            }
            else {
                throw new TheaterException(ResultMapping.NO_MOVIE);
            }

        }
        else {
            throw new TheaterException(ResultMapping.FALUT_SHOWTIME);
        }

    }

    @Override
    @Transactional
    public boolean deleteScreen(Long sId, Long tId) {
        Screen screen=screenDao.findOne(sId);
        if(screen.getDeleteFlag()==DeleteFlagEnum.IS_DELETE.getValue()){
            throw new TheaterException(ResultMapping.NO_SCREEN);
        }
        if (screen.getTicketCount()==0){
            screen.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
            screenDao.save(screen);
            return true;
        }
        else {
            throw new TheaterException(ResultMapping.HAVE_ORDER);
        }
    }

    @Override
    @Transactional
    public boolean editScreen(Long sId,Long mId, Long hId, Date startTime, Long tId) {
        Screen screen = screenDao.findOne(sId);
        if (screen.getDeleteFlag() == DeleteFlagEnum.IS_DELETE.getValue()) {
            throw new TheaterException(ResultMapping.NO_SCREEN);
        }
        if (screen.getTicketCount() == 0) {
            if (startTime.after(new Date())) {
                screen.setHId(hId);
                screen.setMId(mId);
                Movie movie = movieDao.findOne(mId);
                if (movie.getShowState() != 3) {
                    Theater theater = theaterDao.findOne(tId);
                    Date endTime = DateUtil.getEndTime(startTime, movie.getDuration());
                    Date spareTime = DateUtil.getSpareTime(endTime, theater.getIntervalTime());
                    if (hallDao.findhallTimeTable(hId, startTime, spareTime, tId).size() == 0) {
                        screen.setEndTime(endTime);
                        screenDao.save(screen);
                        return true;
                    } else {
                        throw new TheaterException(ResultMapping.HAVE_SCREEN);
                    }
                } else {
                    throw new TheaterException(ResultMapping.NO_MOVIE);
                }
            } else {
                throw new TheaterException(ResultMapping.FALUT_SHOWTIME);
            }
        } else {
            throw new TheaterException(ResultMapping.HAVE_ORDER);
        }
    }

    @Override
    public ScreenListVo screenDetail(Long sId, Long tId) {
        Screen screen=screenDao.findOne(sId);
        if (screen.getDeleteFlag()==DeleteFlagEnum.UN_DELETE.getValue()){
            Movie movie=movieDao.findOne(screen.getMId());
            Hall hall=hallDao.findOne(screen.getHId());
            ScreenListVo listVo=ScreenListVo.builder().mName(movie.getMName())
                    .hName(hall.getHName())
                    .ticketCount(screen.getTicketCount())
                    .startTime(screen.getStartTime())
                    .endTime(screen.getEndTime())
                    .showState(screen.getShowState())
                    .build();
            return listVo;
        }
        else{
            throw new TheaterException(ResultMapping.NO_SCREEN);
        }
    }

    @Override
    public List<ScreenListVo> screenList(Long mId, Long hId, Integer showState, Date startTime, Date endTime, Long tId) {
        return screenDao.findList(mId, hId, tId, showState, startTime, endTime);
    }
}
