package com.test.demo.dao;

import com.test.demo.bean.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDao extends JpaRepository<Movie, String> {

}
