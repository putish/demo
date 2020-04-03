package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.form.AddMovieRequest;
import cn.zucc.demo.service.MovieService;
import cn.zucc.demo.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


@RestController
public class MovieController {
    @Resource
    private MovieService movieService;

    @PostMapping("/addmovie")
    public RootData addMovie(AddMovieRequest request,Long tId) {
        movieService.addMovie(request,tId);
        return ResultUtil.success("success");
    }

    @GetMapping("/bookmovie")
    public String bookMovie(){
        return "book";
    }

}
