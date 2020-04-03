package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.dao.MovieDao;
import cn.zucc.demo.dao.OrderDetailDao;
import cn.zucc.demo.dao.ScreenDao;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddMovieRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.MovieService;
import cn.zucc.demo.vo.MovieListVo;
import cn.zucc.demo.vo.MovieOptionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    @Override
    public List<MovieListVo> findList(Long tId, Integer showState, Long cId,String sortBy) {
        List<Movie> movies=movieDao.findByShowStateAndTId(showState,tId);
        List<MovieListVo> list=new ArrayList<>();
        for (Movie movie:movies){
            if (cId==null||movie.getFicId()==cId||movie.getSecId()==cId||movie.getTId()==cId) {//判断电影类别筛选
                MovieListVo listVo = new MovieListVo();
                BeanUtils.copyProperties(movie, listVo);

                Long tickets = screenDao.countTicket(movie.getMId(), movie.getTId());
                listVo.setTickets(tickets);

                Long seats = screenDao.countSeat(movie.getMId(), movie.getTId());
                listVo.setAttendence(BigDecimal.valueOf((tickets / seats)));

                list.add(listVo);
            }
        }
        if(sortBy.equals("票房")) {
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
    public Movie addMovie(AddMovieRequest addMovieRequest, Long tId) {
        Movie movie=new Movie();
        BeanUtils.copyProperties(addMovieRequest,movie);
        movie.setTId(tId);
        if (movie.getShowTime().after(new Date())){
            throw new TheaterException(ResultMapping.FALUT_SHOWTIME);
        }
        movieDao.save(movie);
        return movie;
    }

    @Override
    public MovieListVo movieDetail(Long mId, Long tId) {
        Movie movie= movieDao.findOne(mId);
        MovieListVo listVo = new MovieListVo();
        BeanUtils.copyProperties(movie, listVo);
        Long tickets = screenDao.countTicket(movie.getMId(), movie.getTId());
        listVo.setTickets(tickets);

        Long seats = screenDao.countSeat(movie.getMId(), movie.getTId());
        listVo.setAttendence(BigDecimal.valueOf((float) (tickets / seats)));
        return listVo;
    }

    @Override
    @Transactional
    public boolean editMovie(AddMovieRequest addMovieRequest, Long tId) {
        Movie movie=new Movie();
        BeanUtils.copyProperties(addMovieRequest,movie);
        movie.setTId(tId);
        if (movie.getShowTime().after(new Date())){
            throw new TheaterException(ResultMapping.FALUT_SHOWTIME);
        }
        movieDao.save(movie);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteMovie(Long mId, Long tId) {
        return false;
    }
}
