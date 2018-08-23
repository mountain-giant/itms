package com.lister.itms.interceptors;

import com.lister.itms.dao.entity.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Describe : 登录验证拦截器
 * Created by Lister on 2018/7/16 3:50 PM.
 * Version : 1.0
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserDO userInfo = (UserDO) session.getAttribute("user");
        String requestType = request.getHeader("X-Requested-With");
        if (userInfo == null) {
            if ("XMLHttpRequest".equals(requestType)) {
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.write("<section class=\"content\"><div class=\"callout callout-danger\">\n" +
                        "<h4>温馨提示!</h4>\n" +
                        "<p>本次会话已过期，请" +
                        "<b><a href=\"/login.bms\">重新登录</a></b>" +
                        "</p>" +
                        "</div><section>");
                out.close();
                out.flush();
                return false;
            }
            request.getRequestDispatcher("/toLogin").forward(request, response);
            return false;
        }
        return true;

    }

}
