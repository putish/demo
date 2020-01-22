package com.test.demo.service.impl;

import com.test.demo.bean.Movie;
import com.test.demo.dao.MovieDao;
import com.test.demo.service.MovieService;

import java.util.List;

public class MovieServiceImpl implements MovieService {
    private MovieDao movieDao;
    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public Movie save(Movie movie) {
        return movieDao.save(movie);
    }
}
