package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.dao.*;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddMovieRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.MovieService;
import cn.zucc.demo.service.ScreenService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.vo.MovieListVo;
import cn.zucc.demo.vo.MovieOptionVo;
import cn.zucc.demo.vo.ScreenListVo;
import cn.zucc.demo.vo.TheaterOptionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private ScreenDao screenDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private TheaterDao theaterDao;

    @Autowired
    private CatergoryDao catergoryDao;

    @Autowired
    private ScreenService screenService;

    @Override
    public List<MovieListVo> findList(Long tId, Integer showState, Long cId,String sortBy,String mName) {
        List<Movie> movies =new ArrayList<>();
        List<MovieListVo> list=new ArrayList<>();

        if(tId==null){
            movies = movieDao.findUserMovieList(mName);//用户账号登录
            List<String> mNames=new ArrayList<>();
            for (Movie movie:movies){
                if (!mNames.contains(movie.getMName())){
                    mNames.add(movie.getMName());
                    if (cId==null||movie.getFicId()==cId||movie.getSecId()==cId||movie.getTId()==cId) {//判断电影类别筛选
                        MovieListVo listVo = new MovieListVo();
                        BeanUtils.copyProperties(movie, listVo);

                        String catergory=catergoryDao.findOne(movie.getFicId()).getCName();//类别转成字符串
                        if(movie.getSecId()!=null){
                            catergory=catergory+"/"+catergoryDao.findOne(movie.getSecId()).getCName();
                            if(movie.getThcId()!=null){
                                catergory=catergory+"/"+catergoryDao.findOne(movie.getThcId()).getCName();
                            }
                        }
                        listVo.setCatergory(catergory);
                        listVo.setShowState(ShowStateEnum.getContentByValue(movie.getShowState()));//上映状态
                        List<Movie> tIdList=movieDao.tIdList(movie.getMName());
                        List<TheaterOptionVo> theaters=new ArrayList<>();
                        for(Movie id:tIdList){
                            TheaterOptionVo vo=new TheaterOptionVo();
                            Theater theater=theaterDao.findOne(id.getTId());
                            vo.setTId(id.getTId());
                            vo.setTName(theater.getTName());
                            vo.setMId(movie.getMId());
                            theaters.add(vo);
                        }
                        listVo.setTheaters(theaters);
                        list.add(listVo);
                    }
                }
            }
        }else {
            movies = movieDao.findList(tId, showState, mName);
            for (Movie movie : movies) {
                if (cId == null || movie.getFicId() == cId || movie.getSecId() == cId || movie.getTId() == cId) {//判断电影类别筛选
                    MovieListVo listVo = new MovieListVo();
                    BeanUtils.copyProperties(movie, listVo);

                    String catergory = catergoryDao.findOne(movie.getFicId()).getCName();//类别转成字符串
                    if (movie.getSecId() != null) {
                        catergory = catergory + "/" + catergoryDao.findOne(movie.getSecId()).getCName();
                        if (movie.getThcId() != null) {
                            catergory = catergory + "/" + catergoryDao.findOne(movie.getThcId()).getCName();
                        }
                    }

                    Long tickets = screenDao.countTicket(movie.getMId(), movie.getTId());//记录票房
                    listVo.setTickets(tickets == null ? 0 : tickets);

                    Long seats = screenDao.countSeat(movie.getMId(), movie.getTId());//上座率
                    if (seats == null) {
                        listVo.setAttendence(BigDecimal.ZERO);
                    } else {
                        listVo.setAttendence(BigDecimal.valueOf((tickets / seats)));
                    }

                    listVo.setCatergory(catergory);
                    listVo.setShowState(ShowStateEnum.getContentByValue(movie.getShowState()));//上映状态
                    list.add(listVo);
                }
            }
            if(sortBy==null||sortBy.equals("票房")) {//排序方式
                list.sort(new Comparator<MovieListVo>() {
                    @Override
                    public int compare(MovieListVo o1, MovieListVo o2) {
                        Long tickets1 = o1.getTickets();
                        Long tickets2 = o2.getTickets();
                        return tickets1.compareTo(tickets2);
                    }
                });
            }else {
                list.sort(new Comparator<MovieListVo>() {
                    @Override
                    public int compare(MovieListVo o1, MovieListVo o2) {
                        BigDecimal attendences1=o1.getAttendence();
                        BigDecimal attendences2=o2.getAttendence();
                        return attendences1.compareTo(attendences2);
                    }
                });
            }

        }
        return list;
    }

    @Override
    public List<MovieOptionVo> optionList(Long tId) {
        List<Movie> list=movieDao.findByTIdAndDeleteFlag(tId,DeleteFlagEnum.UN_DELETE.getValue());
        List<MovieOptionVo> optionVos=new ArrayList<>();

        for (Movie movie:list){
            MovieOptionVo optionVo=new MovieOptionVo();
            optionVo.setMId(movie.getMId());
            optionVo.setMName(movie.getMName());
            optionVos.add(optionVo);
        }
        return optionVos;
    }

    @Override
    @Transactional
    public Movie addMovie(AddMovieRequest request, Long tId) throws ParseException {
        Movie movie=new Movie();
        BeanUtils.copyProperties(request,movie);
        movie.setEndTime(DateUtil.toDate(request.getEndTime()));//字符串转时间
        movie.setShowTime(DateUtil.toDate(request.getShowTime()));
        movie.setTId(tId);
        String[] cIds=request.getCatergory().split(",");//将拼接字符串转化为id
        for(int i=0;i<cIds.length;i++) {
            if (i == 0) {
                movie.setFicId(Long.valueOf(cIds[i]));
            } else if (i == 1) {
                movie.setSecId(Long.valueOf(cIds[i]));
            } else if (i == 2) {
                movie.setThcId(Long.valueOf(cIds[i]));
            }
        }

        if (!movie.getShowTime().after(new Date())){//判断上映时间是否正确
            throw new TheaterException(ResultMapping.FALUT_SHOWTIME);
        }
        movie.setShowState(ShowStateEnum.IN_SHOW.getValue());
        movie.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        movieDao.save(movie);
        return movie;
    }

    @Override
    public Movie movieDetail(Long mId, Long tId) {
        return movieDao.findOne(mId);
    }

    @Override
    @Transactional
    public boolean editMovie(AddMovieRequest request, Long tId) throws ParseException {
        Movie movie=movieDao.findOne(request.getMId());
        if(!DateUtil.toDate(request.getEndTime()).after(DateUtil.getStartTime(new Date(),24*60*3))){
            throw new TheaterException(ResultMapping.FALUT_SHOWTIME);
        }
        BeanUtils.copyProperties(request,movie);
        movie.setEndTime(DateUtil.toDate(request.getEndTime()));
        movie.setShowTime(DateUtil.toDate(request.getShowTime()));
        movie.setTId(tId);
        String[] cIds=request.getCatergory().split(",");
        for(int i=0;i<cIds.length;i++) {
            if (i == 0) {
                movie.setFicId(Long.valueOf(cIds[i]));
            } else if (i == 1) {
                movie.setSecId(Long.valueOf(cIds[i]));
            } else if (i == 2) {
                movie.setThcId(Long.valueOf(cIds[i]));
            }
        }

        if (!movie.getShowTime().after(new Date())){
            throw new TheaterException(ResultMapping.FALUT_SHOWTIME);
        }
        movieDao.save(movie);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteMovie(Long mId, Long tId) {
        Movie movie=movieDao.findOne(mId);
        List<ScreenListVo> list=screenService.screenList(mId,null,null,null,null,tId);
        if (list.size()==0){//没有播放场次
            movie.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
            movieDao.save(movie);
            return true;
        }else {
            throw new TheaterException(ResultMapping.HAVE_SCREEN);
        }

    }

    @Override
    public void movieCheckTask() {
        List<Movie> movies=movieDao.findByShowStateNotAndDeleteFlag(ShowStateEnum.SOLD_OUT.getValue(),DeleteFlagEnum.UN_DELETE.getValue());
        for (Movie movie:movies){
            if (movie.getEndTime().after(new Date())){//改变下架上映状态
                movie.setShowState(ShowStateEnum.SOLD_OUT.getValue());
                movieDao.save(movie);
            }else if (movie.getShowTime().after(new Date())) {//改变上映上映状态
                movie.setShowState(ShowStateEnum.IN_SHOW.getValue());
                movieDao.save(movie);
            }
        }
    }
}
