package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.dao.*;
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
        List<Movie> movies=movieDao.findList(tId,showState,mName);

        List<MovieListVo> list=new ArrayList<>();
        for (Movie movie:movies){
            if (cId==null||movie.getFicId()==cId||movie.getSecId()==cId||movie.getTId()==cId) {//判断电影类别筛选
                MovieListVo listVo = new MovieListVo();
                BeanUtils.copyProperties(movie, listVo);
                String catergory=catergoryDao.findOne(movie.getFicId()).getCName();
                Long tickets = screenDao.countTicket(movie.getMId(), movie.getTId());
                listVo.setTickets(tickets==null?0:tickets);

                Long seats = screenDao.countSeat(movie.getMId(), movie.getTId());
                if(seats==null){
                    listVo.setAttendence(BigDecimal.ZERO);
                }
                else {
                    listVo.setAttendence(BigDecimal.valueOf((tickets / seats)));
                }
                if(movie.getSecId()!=null){
                    catergory=catergory+"/"+catergoryDao.findOne(movie.getSecId()).getCName();
                    if(movie.getThcId()!=null){
                        catergory=catergory+"/"+catergoryDao.findOne(movie.getThcId()).getCName();
                    }
                }
                listVo.setCatergory(catergory);
                listVo.setShowState(ShowStateEnum.getContentByValue(movie.getShowState()));
                Theater theater=theaterDao.findOne(tId);
                listVo.setTName(theater.getTName());
                list.add(listVo);
            }
        }
        if(sortBy==null||sortBy.equals("票房")) {
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
        return list;
    }

    @Override
    public List<MovieOptionVo> optionList(Long tId) {
        List<Movie> list=movieDao.findByTId(tId);
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
    public Movie addMovie(AddMovieRequest addMovieRequest, Long tId) throws ParseException {
        Movie movie=new Movie();
        BeanUtils.copyProperties(addMovieRequest,movie);
        movie.setEndTime(DateUtil.toDate(addMovieRequest.getEndTime()));//字符串转时间
        movie.setShowTime(DateUtil.toDate(addMovieRequest.getShowTime()));
        movie.setTId(tId);
        String[] cIds=addMovieRequest.getCatergory().split(",");//将拼接字符串转化为id
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
        movieDao.save(movie);
        return movie;
    }

    @Override
    public MovieListVo movieDetail(Long mId, Long tId) {
        Movie movie= movieDao.findOne(mId);
        MovieListVo listVo = new MovieListVo();
        BeanUtils.copyProperties(movie, listVo);
        String catergory=catergoryDao.findOne(movie.getFicId()).getCName();
        Long tickets = screenDao.countTicket(movie.getMId(), movie.getTId());
        listVo.setTickets(tickets==null?0:tickets);

        Long seats = screenDao.countSeat(movie.getMId(), movie.getTId());
        if(seats==null){
            listVo.setAttendence(BigDecimal.ZERO);
        }
        else {
            listVo.setAttendence(BigDecimal.valueOf((tickets / seats)));
        }
        if(movie.getSecId()!=null){
            catergory=catergory+"/"+catergoryDao.findOne(movie.getSecId()).getCName();
            if(movie.getThcId()!=null){
                catergory=catergory+"/"+catergoryDao.findOne(movie.getThcId()).getCName();
            }
        }
        listVo.setCatergory(catergory);
        listVo.setShowState(ShowStateEnum.getContentByValue(movie.getShowState()));
        return listVo;
    }

    @Override
    @Transactional
    public boolean editMovie(AddMovieRequest addMovieRequest, Long tId) throws ParseException {
        Movie movie=new Movie();
        BeanUtils.copyProperties(addMovieRequest,movie);
        movie.setEndTime(DateUtil.toDate(addMovieRequest.getEndTime()));
        movie.setShowTime(DateUtil.toDate(addMovieRequest.getShowTime()));
        movie.setTId(tId);
        String[] cIds=addMovieRequest.getCatergory().split(",");
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
        movie.setShowState(ShowStateEnum.IN_SHOW.getValue());
        movieDao.save(movie);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteMovie(Long mId, Long tId) {
        Movie movie=movieDao.findOne(mId);
        List<ScreenListVo> list=screenService.screenList(mId,null,null,null,null,tId);
        if (list.size()==0){
            movie.setShowState(ShowStateEnum.SOLD_OUT.getValue());
            movie.setEndTime(new Date());
            movieDao.save(movie);
            return true;
        }else {
            throw new TheaterException(ResultMapping.HAVE_SCREEN);
        }

    }
}
