package com.test.demo.controller;

import com.test.demo.bean.Movie;
import com.test.demo.data.RootDate;
import com.test.demo.service.MovieService;
import com.test.demo.service.impl.MovieServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller


public class MovieController {
    private MovieService movieService;
    @GetMapping("/showmovie")
    public String showmovie() {

        ModelAndView modelAndView = new ModelAndView();

//        modelAndView.setViewName("test");

//        modelAndView.addObject("key", 12345);

        //System.out.println("test");

        return "movie";
    }
    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String save(@ModelAttribute(value="movie") Movie movie) {
        movieService.save(movie);
        return "";
    }

    @GetMapping("/bookmovie")
    public String bookMovie(){
        return "book";
    }

}
