package cn.zucc.demo.service;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.form.AddMovieRequest;
import cn.zucc.demo.vo.MovieDetailVo;
import cn.zucc.demo.vo.MovieListVo;
import cn.zucc.demo.vo.MovieOptionVo;

import java.text.ParseException;
import java.util.List;

public interface MovieService {
    /**
     *
     * @param tId 影院id 影院id
     * @param showState 上映状态
     * @param cId 影片状态
     * @param sortBy 排序依据
     * @return
     */
    List<MovieListVo> findList(Long tId, Integer showState, Long cId,String sortBy,String mName);

    /**
     * 影片下拉列表
     * @param tId 影院id
     * @return
     */
    List<MovieOptionVo> optionList(Long tId);
    /**
     * @Description TODO
     * @param addMovieRequest
     * @return cn.zucc.demo.bean.Movie
     * @author hjj
     * @Date 2020/1/22 17:02
     **/
    Movie addMovie(AddMovieRequest addMovieRequest,Long tId) throws ParseException;

    /**
     * 影片详情
     * @param mId 影院id 电影id
     * @param tId 影院id 影院id
     * @return
     */
    MovieDetailVo movieDetail(Long mId, Long tId) throws ParseException;

    /**
     * 编辑电影
     * @param addMovieRequest
     * @param tId 影院id 影片id
     * @return
     */
    boolean editMovie(AddMovieRequest addMovieRequest,Long tId) throws ParseException;

    /**
     * 删除影片
     * @param mId 影院id 影片id
     * @param tId 影院id 影院id
     * @return
     */
    boolean deleteMovie(Long mId,Long tId);

    /**
     * 新增播放厅影片下拉框
     * @param tId
     * @return
     */
    List<MovieOptionVo> ScreenMovieOption(Long tId);
    /**
     * 影片定时检查
     */
    void movieCheckTask();

}
