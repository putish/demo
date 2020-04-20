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
        List<MovieListVo> nowshowlist=movieService.findList(tId, ShowStateEnum.IN_SHOW.getValue(), null, null,mName==null?null:"%"+mName+"%");
        List<MovieListVo> noslist=movieService.findList(tId, ShowStateEnum.IN_SHOW.getValue(), null, null,mName==null?null:"%"+mName+"%");
        model.addAttribute("nowshowlist",nowshowlist);//即将上映
        model.addAttribute("noslist",noslist);//上映
        return "index";
    }

    @ResponseBody
    @PostMapping("/add")
    public RootData addMovie(@RequestBody AddMovieRequest request, HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        movieService.addMovie(request,tId);
        return ResultUtil.success("新增成功");
    }

    @GetMapping("/bookmovie")
    public String bookMovie(){
        return "movie";
    }

    /**
     * 影片列表
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
    @GetMapping("/list")
    public String list(HttpSession session,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2") int pageSize, @RequestParam(required = false) Integer showState, @RequestParam(required = false) String mName,
                       @RequestParam(required = false) Long cId, @RequestParam(required = false) String sortBy, Model model){
        Long tId= (Long) session.getAttribute("tId");
        List<MovieListVo> list=movieService.findList(tId, showState, cId, sortBy,mName==null?null:"%"+mName+"%");
        Page<MovieListVo> page=PageHelper.startPage(pageNum, pageSize);
        PageInfo<MovieListVo> pageInfo=page.toPageInfo();
        pageInfo.setList(list);
        List<Catergory> options=catergoryService.findList(tId);
        model.addAttribute("options",options);
        model.addAttribute("list",pageInfo);
        return "movie";
    }

    @GetMapping("/indexlist")
    public String indexlist( HttpSession session,Model model){
        Long tId= (Long) session.getAttribute("tId");
        List<MovieListVo> list=movieService.findList(tId, null, null, null,"%%");
        model.addAttribute("list",list);
        return "index";
    }
    @PostMapping("/edit")
    public String editMovie(@RequestBody AddMovieRequest request,@RequestParam(required = false)  HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        movieService.editMovie(request,tId);
        return "movie";
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
    public RootData MovieDetail(@RequestParam(required = false)  Long mId,@RequestParam(required = false)  HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        return ResultUtil.success(movieService.movieDetail(mId,tId));
    }
}