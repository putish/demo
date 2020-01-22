package com.test.demo.service;

import com.test.demo.bean.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();
    Movie save(Movie movie);
}
