package cn.zucc.demo.interceptor;

import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.bean.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 登录拦截器
 * @author: hjj
 * @create: 2020-03-26 22:16
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    private Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if ("/index".equals(request.getRequestURI()) || "/theater/login".equals(request.getRequestURI())) {
            return true;}
        //重定向
        Object object = request.getSession().getAttribute("tId");
        if (null == object) {
            request.getSession().setAttribute("tId",Long.valueOf(1));

            response.sendRedirect("/theater/login");
            return false;}
        return true;



    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.info("afterCompletion...");
    }
}
