package cn.zucc.etakeout.controller;

import cn.zucc.etakeout.dto.OrderDTO;
import cn.zucc.etakeout.exception.SellException;
import cn.zucc.etakeout.form.OrderQueryForm;
import cn.zucc.etakeout.form.PayCreateForm;
import cn.zucc.etakeout.mappings.ResultMapping;
import cn.zucc.etakeout.service.OrderService;
import cn.zucc.etakeout.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Date ：Created in 2019/5/4 10:56
 * @Description：订单支付
 * @Created By：bing
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestBody @Valid PayCreateForm form, BindingResult bindingResult,
                               Map<String, Object> map){
        if(bindingResult.hasErrors()){
            throw new SellException(ResultMapping.ORDER_PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String orderId = form.getOrderId();
        String returnUrl = form.getReturnUrl();

        OrderDTO order = orderService.findOne(orderId);
        if(order==null){
            throw new SellException(ResultMapping.ORDER_NOT_EXIST);
        }

        PayResponse payResponse = payService.create(order);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

}
