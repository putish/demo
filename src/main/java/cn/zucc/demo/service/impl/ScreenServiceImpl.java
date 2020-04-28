package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.*;
import cn.zucc.demo.dao.*;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.HolidayEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.MovieSort;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.ScreenService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.vo.MovieListVo;
import cn.zucc.demo.vo.ScreenListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private CatergoryDao catergoryDao;
    
    @Override
    @Transactional
    public Screen addScreen(Long mId, Long hId, Date startTime,BigDecimal price, String screenCate,Long tId) {
        if(!startTime.after(new Date())) {
            Movie movie=movieDao.findOne(mId);
            if(movie.getShowState()!=3){
                Theater theater=theaterDao.findOne(tId);
                Date endTime=DateUtil.getEndTime(startTime,movie.getDuration());//影片时长加上间隔时间
                Date spareTime=DateUtil.getSpareTime(endTime,theater.getIntervalTime());

                if (screenDao.findList(null,hId,tId,ShowStateEnum.WILL_SHOW.getValue(),startTime,spareTime).size()==0) {//该时间段播放厅无放映场次
                    Screen screen = new Screen();
                    screen.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
                    screen.setStartTime(startTime);
                    screen.setHId(hId);
                    screen.setMId(mId);
                    screen.setShowState(ShowStateEnum.WILL_SHOW.getValue());//即将上映
                    screen.setTicketCount(0);//已经售出票数
                    screen.setScreenCate(screenCate);
                    screen.setPrice(price);
                    screen.setTId(tId);
                    screen.setEndTime(endTime);
                    screenDao.save(screen);
                    return screen;
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
    public boolean editScreen(Long sId,Long mId, Long hId,BigDecimal price, String screenCate, Date startTime, Long tId) {
        Screen screen = screenDao.findOne(sId);
        if (screen.getDeleteFlag() == DeleteFlagEnum.IS_DELETE.getValue()) {//播放场次不存在
            throw new TheaterException(ResultMapping.NO_SCREEN);
        }
        if (screen.getTicketCount() == 0) {
            if (!startTime.after(new Date())) {
                screen.setHId(hId);
                screen.setMId(mId);
                Movie movie = movieDao.findOne(mId);
                if(movie.getShowState()!=3){
                    Theater theater=theaterDao.findOne(tId);
                    Date endTime=DateUtil.getEndTime(startTime,movie.getDuration());//影片时长加上间隔时间
                    Date spareTime=DateUtil.getSpareTime(endTime,theater.getIntervalTime());

                    if (screenDao.findList(null,hId,tId,ShowStateEnum.WILL_SHOW.getValue(),startTime,spareTime).size()==0) {//该时间段播放厅无放映场次
                        screen.setStartTime(startTime);
                        screen.setHId(hId);
                        screen.setMId(mId);
                        screen.setScreenCate(screenCate);
                        screen.setPrice(price);
                        screen.setTId(tId);
                        screen.setEndTime(endTime);
                        screenDao.save(screen);
                        return true;
                    }else {
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
            ScreenListVo listVo=ScreenListVo.builder()
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
        List<Screen> screens=screenDao.findList(mId, hId, tId, showState, startTime, endTime);
        List<ScreenListVo> list=new ArrayList<>();
        for (Screen screen:screens){
            ScreenListVo vo=new ScreenListVo();
            BeanUtils.copyProperties(screen,vo);
            Movie movie=movieDao.findOne(screen.getMId());
            vo.setMName(movie.getMName());
            vo.setHName(hallDao.findOne(screen.getHId()).getHName());
            list.add(vo);
        }
        return list;
    }

    @Override
    public boolean screenSchedule(Long tId) {
        Date today=DateUtil.initDateByDay();//今日零点
        Date tommorrow= DateUtil.getEndTime(today,60*24);

        List<Hall> halls=hallDao.findByTIdAndDeleteFlagAndUseStateOrderBySeatCount(tId,DeleteFlagEnum.UN_DELETE.getValue(), UseStateEnum.IN_USE.getValue());//所有播放厅
        List<Movie> movies=movieDao.findByShowStateAndTId(ShowStateEnum.WILL_SHOW.getValue(),tId);//正在上映
        List<Movie> willMovies=movieDao.findByShowStateAndTId(ShowStateEnum.WILL_SHOW.getValue(),tId);//即将上映

        List<MovieSort> movieSorts=getSort(movies,tommorrow);
        List<MovieSort> willMovieSorts=getSort(willMovies,tommorrow);

        createGoldScreen(movieSorts,halls,tId);//生成黄金时间段播放场次
        createUnGoldScreen(movieSorts,halls,tId);//生成非黄金时间段播放场次


        return false;
    }

    @Override
    public List<MovieSort> getSort(List<Movie> movies,Date date) {
        List<MovieSort> list=new ArrayList<>();
        for(Movie movie:movies){
            List<Schedule> schedules=scheduleDao.findByMIdAndDeleteFlag(movie.getMId(),DeleteFlagEnum.UN_DELETE.getValue());
            Integer totalTickets = null;
            for(Schedule schedule:schedules){//总排片量
                totalTickets+=schedule.getFCount();
            }
            for(Schedule schedule:schedules){

                MovieSort vo = new MovieSort();
                vo.setMId(movie.getMId());
                vo.setScreenCate(schedule.getScreenCate());
                vo.setPrice(schedule.getPrice());

                Long tickets = screenDao.countTicket(movie.getMId(), movie.getTId());
                vo.setTickets(tickets == null ? 0 : tickets);
                Long seats = screenDao.countSeat(movie.getMId(), movie.getTId());
                if (seats == null) {
                    vo.setAttendence(BigDecimal.ZERO);
                } else {
                    vo.setAttendence(BigDecimal.valueOf((tickets / seats)));
                }

                Float categoryIndex = getCategoryIndex(movie.getFicId(), movie.getSecId(), movie.getThcId(), date);
                vo.setCategoryIndex(categoryIndex);
                Float totalSort = categoryIndex * 0.2f + tickets * 0.3f + Float.valueOf(tickets / seats) * 0.5f+schedule.getFCount()/totalTickets*10;
                vo.setTotalSort(totalSort);//优先度

                vo.setDuration(movie.getDuration());
                int goldSeat = (int) (schedule.getFCount() * DateUtil.GOLDRATE * totalSort);//黄金时间排片量
                vo.setGoldSeatCount(goldSeat);
                vo.setUnGoldSeatCount(schedule.getFCount() - goldSeat);//非黄金时间排片量

                list.add(vo);
            }
        }
        list.sort(new Comparator<MovieSort>() {
            @Override
            public int compare(MovieSort o1, MovieSort o2) {
                Float totalSort1 = o1.getTotalSort();
                Float totalSort2 = o2.getTotalSort();
                return totalSort1.compareTo(totalSort2);
            }
        });
        return list;
    }

    @Override
    public Float getCategoryIndex(Long ficId, Long secId, Long thcId,Date date) {
        String ficName=catergoryDao.findOne(ficId).getCName();
        String secName=catergoryDao.findOne(secId).getCName();
        String thcName=catergoryDao.findOne(thcId).getCName();
        Float result= turnIndex(ficName,date)+turnIndex(secName,date)*0.9f+turnIndex(thcName,date)*0.4f;
        return result;
    }

    @Override
    public Float turnIndex(String cName,Date date) {
        Float result= Float.valueOf(1);
        if (cName=="爱情") {
            result = result * 0.95f;
            if (DateUtil.judgeDate(date) == HolidayEnum.VALENTINE) {
                result = result * 1.1f;
            }
        }
        else if (cName=="喜剧"){
            result = result * 0.9f;
            if (DateUtil.judgeDate(date) == HolidayEnum.SPRING_FESTIVAL) {
                result = result * 1.1f;
            }
        }
        else if (cName=="恐怖"&&cName=="惊悚"){
            result = result * 0.85f;
            if (DateUtil.judgeDate(date) == HolidayEnum.All_SAINTS) {
                result = result * 1.1f;
            }
        }
        else if (cName=="科幻"&&cName=="魔幻"){
            result = result * 0.8f;
            if (DateUtil.judgeDate(date) == HolidayEnum.VALENTINE) {
                result = result * 1.1f;
            }
        }
        else if (cName=="警匪"&&cName=="悬疑"&&cName=="动作"){
            result = result * 0.75f;

        }
        else if (cName=="动画"&&cName=="武侠"&&cName=="犯罪"){
            result = result * 0.7f;
            if (DateUtil.judgeDate(date) == HolidayEnum.CHILDREN &&cName=="动画") {
                result = result * 1.15f;
            }
        }
        else{
            result = result * 0.5f;
            if (DateUtil.judgeDate(date) == HolidayEnum.WEEKEND){
                result = result * 0.9f;
            } else if (DateUtil.judgeDate(date) == HolidayEnum.WORKDAY){
                result = result * 1.05f;
            }
            result = result * 0.85f;

        }
        return result;
    }

    @Override
    public void createGoldScreen(List<MovieSort> movieSorts, List<Hall> halls,Long tId) {
        Date startGold = DateUtil.startGold();
        Date endGold = DateUtil.endGold();
        List<Date> endHallDate = new ArrayList<>();//记录播放厅散场时刻
        for(int i=0;i<halls.size();i++){
            endHallDate.add(startGold);
        }
        Theater theater=theaterDao.findOne(tId);
        List<Integer> GoldSeats = new ArrayList<>();//黄金时间剩余排片量；
        while (movieSorts.size() != 0) {
            int hallIndex = 0;
            int movieIndex = 0;
            for (MovieSort movieSort : movieSorts) {
                Screen screen = null;
                while (DateUtil.getEndTime( endHallDate.get(hallIndex), movieSort.getDuration()+theater.getIntervalTime()).before(endGold)) {//散场时间不能超过营业时间截止
                   screen = addScreen(movieSort.getMId(), halls.get(hallIndex).getHId(), endHallDate.get(hallIndex),
                           movieSort.getPrice().add(BigDecimal.valueOf(5)),movieSort.getScreenCate(), movieSort.getTId());//黄金时间段票价上涨五元
                   hallIndex++;
                }
                if (GoldSeats.get(movieIndex) == null) {
                    GoldSeats.add(movieSort.getGoldSeatCount() - halls.get(hallIndex).getSeatCount());
                } else {
                    if (GoldSeats.get(movieIndex) - halls.get(hallIndex).getSeatCount()<0) {
                        GoldSeats.set(movieIndex, 0);
                        movieSorts.remove(movieIndex);//黄金时间段排片量完成
                        movieIndex++;
                    }else {
                        GoldSeats.set(movieIndex,GoldSeats.get(movieIndex) - halls.get(hallIndex).getSeatCount());
                        movieIndex++;
                    }
                }
                endHallDate.set(hallIndex,DateUtil.getSpareTime(screen.getEndTime(),theater.getIntervalTime()));
                if(hallIndex<halls.size()-1) {
                    hallIndex++;
                }else {
                    hallIndex=0;
                }

            }
        }
    }

    @Override
    public void createUnGoldScreen(List<MovieSort> movieSorts, List<Hall> halls,Long tId) {
        Theater theater=theaterDao.findOne(tId);

        Date startGold = DateUtil.startGold();
        Date startTime=DateUtil.getOpenHours(theater.getStartTime());
        List<Date> endStartHallDate = new ArrayList<>();//记录播放厅开场时刻
        for(int i=0;i<halls.size();i++){
            endStartHallDate.add(DateUtil.getSpareTime(startGold,theater.getIntervalTime()));//黄金时间减去间隔时间即位播放厅散场时间
        }
        List<Integer> UnGoldSeats = new ArrayList<>();//非黄金时间剩余排片量；
        while (movieSorts.size() != 0) {
            int hallIndex = 0;
            int movieIndex = 0;
            for (MovieSort movieSort : movieSorts) {
                Screen screen = null;
                while (DateUtil.getEndTime( endStartHallDate.get(hallIndex), movieSort.getDuration()).after(startTime)) {//开场时间不能超过营业开始时间
                    screen = addScreen(movieSort.getMId(), halls.get(hallIndex).getHId(), DateUtil.getEndTime( endStartHallDate.get(hallIndex), movieSort.getDuration()),
                            movieSort.getPrice(),movieSort.getScreenCate(), movieSort.getTId());
                    hallIndex++;
                }
                if (UnGoldSeats.get(movieIndex) == null) {
                    UnGoldSeats.add(movieSort.getUnGoldSeatCount() - halls.get(hallIndex).getSeatCount());
                } else {
                    if (UnGoldSeats.get(movieIndex) - halls.get(hallIndex).getSeatCount()<0) {
                        UnGoldSeats.set(movieIndex, 0);
                        movieSorts.remove(movieIndex);//非黄金时间段排片量完成
                        movieIndex++;
                    }else {
                        UnGoldSeats.set(movieIndex,UnGoldSeats.get(movieIndex) - halls.get(hallIndex).getSeatCount());
                        movieIndex++;
                    }
                }
                endStartHallDate.set(hallIndex,DateUtil.getStartTime(screen.getStartTime(),theater.getIntervalTime()));
                if(hallIndex<halls.size()-1) {
                    hallIndex++;
                }else {
                    hallIndex=0;
                }

            }
        }

    }

    @Override
    public void screenCheckTask() {
        List<Screen> screens=screenDao.findByShowStateNotAndAndDeleteFlag(ShowStateEnum.SOLD_OUT.getValue(),DeleteFlagEnum.UN_DELETE.getValue());
        for(Screen screen:screens){
            if (screen.getEndTime().after(new Date())){//改变下架上映状态
                screen.setShowState(ShowStateEnum.SOLD_OUT.getValue());
                screenDao.save(screen);
            }else if (screen.getStartTime().after(new Date())){//改变上映上映状态
                screen.setShowState(ShowStateEnum.IN_SHOW.getValue());
                screenDao.save(screen);
            }
        }
    }


}
