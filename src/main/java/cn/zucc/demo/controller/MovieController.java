package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Catergory;
import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.form.AddMovieRequest;
import cn.zucc.demo.service.CatergoryService;
import cn.zucc.demo.service.MovieService;
import cn.zucc.demo.util.ResultUtil;
import cn.zucc.demo.vo.MovieListVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {
    @Resource
    private MovieService movieService;

    @Resource
    private CatergoryService catergoryService;

    @GetMapping("/")
    public String index(Model model, String mName,HttpSession session){
        Long tId= (Long) session.getAttribute("tId");
        List<MovieListVo> nowshowlist=movieService.findList(tId, ShowStateEnum.WILL_SHOW.getValue(), null, null,mName==null?null:"%"+mName+"%");
        List<MovieListVo> noslist=movieService.findList(tId, ShowStateEnum.IN_SHOW.getValue(), null, null,mName==null?null:"%"+mName+"%");
        model.addAttribute("nowshowlist",nowshowlist);//即将上映
        model.addAttribute("noslist",noslist);//上映
        return "index";
    }
    @ResponseBody
    @PostMapping("/add")
    public RootData  addMovie(@RequestBody AddMovieRequest request, HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        movieService.addMovie(request,tId);
        return ResultUtil.success("新增成功");
    }

    @GetMapping("/bookmovie")
    public String bookMovie(){
        return "movie";
    }

    /**
     * 影院端影片列表
     * @param session
     * @param pageNum 当前页面
     * @param pageSize 页面大小
     * @param showState 上映状态
     * @param mName 影片名称
     * @param cId 类别id
     * @param sortBy
     * @param model
     * @return
     */
    @GetMapping("/theaterlist")
    public String theaterlist(HttpSession session,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2") int pageSize,
                              @RequestParam(required = false) String showState, @RequestParam(required = false) String mName,
                              @RequestParam(required = false) Long cId, @RequestParam(required = false) String sortBy, Model model){
        Long tId= (Long) session.getAttribute("tId");
        Integer showStateEnum=null;
        if(showState!=null) {
            if (showState.equals("即将上映")) {
                showStateEnum = 1;
            } else if (showState.equals("上映")) {
                showStateEnum = 2;
            } else if (showState.equals("下架")) {
                showStateEnum = 3;
            }
        }

        List<MovieListVo> list=movieService.findList(tId, showStateEnum, cId, sortBy,(mName==null||mName=="")?null:"%"+mName+"%");
        List<Catergory> options=catergoryService.findList(tId);
        model.addAttribute("options",options);
        model.addAttribute("list",list);
        return "movie";
    }
    @GetMapping("/userlist")
    public String userlist(HttpSession session,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2") int pageSize,
                           @RequestParam(required = false) String showState, @RequestParam(required = false) String mName,
                       @RequestParam(required = false) Long cId, @RequestParam(required = false) String sortBy, Model model){
        Long uId= (Long) session.getAttribute("uId");
        Integer showStateEnum=null;
        if(showState!=null) {
            if (showState.equals("即将上映")) {
                showStateEnum = 1;
            } else if (showState.equals("上映")) {
                showStateEnum = 2;
            } else if (showState.equals("下架")) {
                showStateEnum = 3;
            }
        }
        List<MovieListVo> list=movieService.findList(null, showStateEnum, cId, sortBy,(mName==null||mName=="")?null:"%"+mName+"%");
        Page<MovieListVo> page=PageHelper.startPage(pageNum, pageSize);
        PageInfo<MovieListVo> pageInfo=page.toPageInfo();
        pageInfo.setList(list);
        model.addAttribute("list",pageInfo);
        return "movielist";
    }

    @PostMapping("/edit")
    public String editMovie(@RequestBody AddMovieRequest request,HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        movieService.editMovie(request,tId);
        return "forward:/movie/theaterlist";
    }
    @ResponseBody
    @PostMapping("/delete")
    public RootData deleteMovie(@RequestParam(required = false,value="mId") Long mId
            ,HttpSession session){
        Long tId= (Long) session.getAttribute("tId");
        movieService.deleteMovie(mId, tId);
        return ResultUtil.success("删除成功");
    }
    @ResponseBody
    @GetMapping("/detail")
    public RootData MovieDetail(@RequestParam(required = false)  Long mId,HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        return ResultUtil.success(movieService.movieDetail(mId,tId));
    }
}
