package com.test.demo.service;

import com.test.demo.bean.Movie;

import java.util.List;

public interface MovieService {
    /**
     * @Description TODO
     * @param
     * @return java.util.List<com.test.demo.bean.Movie>
     * @author hjj 
     * @Date 2020/1/22 16:51 
     **/
    List<Movie> findAll();
    /**
     * @Description TODO
     * @param movie
     * @return com.test.demo.bean.Movie
     * @author hjj
     * @Date 2020/1/22 17:02
     **/
    Movie add(Movie movie);
}
