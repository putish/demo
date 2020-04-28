package cn.zucc.demo.task;

import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.dao.TheaterDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.service.HallService;
import cn.zucc.demo.service.MovieService;
import cn.zucc.demo.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-04-28 13:51
 **/
@Component
public class TheatherTask {
    @Autowired
    private TheaterDao theaterDao;

    @Autowired
    private ScreenService screenService;

    @Autowired
    private HallService hallService;

    @Autowired
    private MovieService movieService;



    @Scheduled(cron="0 0 24 ?")
    private void screenScheduleTask(){
        List<Theater> theaters=theaterDao.findByDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        for(Theater theater:theaters) {
            screenService.screenSchedule(theater.getTId());
        }
        System.out.println("排片完成");
    }

    @Scheduled(cron="0 0 24 ?")
    private void hallTask(){
        hallService.HallCheackTask();
        System.out.println("播放厅检查完成");
    }

    @Scheduled(cron="0 0 24 ?")
    private void movieTask(){
        movieService.movieCheckTask();
        System.out.println("影片检查完成");
    }

    @Scheduled(cron="0 0 */1 ?")
    private void screenTask(){
        screenService.screenCheckTask();
        System.out.println("播放场次检查完成");
    }
}
