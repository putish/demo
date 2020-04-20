package cn.zucc.demo.form;

import cn.zucc.demo.vo.SeatAddVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.jrockit.jfr.ValueDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 添加播放厅
 * @author: hjj
 * @create: 2020-02-26 13:50
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddHallRequest {
    //  影厅名称
    private String hName;
    //    行数
    private int rows;
    //    列数
    private int cols;
    //    座位数量
    private int seatCount;
    //    屏幕类型
    private String screenCate;
    //    座位表
    private List<SeatAddVo> seatVos;


    public static void main(String args[]) throws JsonProcessingException {
        AddHallRequest request=new AddHallRequest();
        request.setCols(4);
        request.setHName("1");
        request.setRows(5);
        request.setSeatCount(20);
        request.setScreenCate("2d");
        List<SeatAddVo> seatAddVos=new ArrayList<>();
        for(int i=1;i<=5;i++){
            for(int j=1;j<=4;j++){
                seatAddVos.add(new SeatAddVo(i,j));
            }
        }
        request.setSeatVos(seatAddVos);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);
        System.out.println(json);
    }
}
