package com.example.demo.interceptor;

import com.example.demo.constant.CommonConstant;
import com.example.demo.exception.NoLoginException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author zhouxiong
 */
public class LoginStatusChecker extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (Objects.isNull(session)
            || Objects.isNull(session.getAttribute(CommonConstant.CURRENT_CUSTOMER_KEY))) {
            throw new NoLoginException();
        }

        return true;
    }

}
