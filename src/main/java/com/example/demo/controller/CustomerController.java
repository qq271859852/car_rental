package com.example.demo.controller;

import com.example.demo.bean.ApiResult;
import com.example.demo.bean.entity.Customer;
import com.example.demo.bean.req.LoginReq;
import com.example.demo.constant.CommonConstant;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.service.CustomerService;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhouxiong
 */
@RestController
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * customer login
     * @param request
     * @param loginReq telephone \ password
     * @return
     */
    @PostMapping("/login")
    public ApiResult<Boolean> login(HttpServletRequest request, @RequestBody @Validated LoginReq loginReq) {
        String telephone = loginReq.getTelephone();
        String password = loginReq.getPassword();

        Customer customer = customerService.findCustomerInfo(telephone);

        // check password
        Optional.ofNullable(customer)
            .filter(customerInfo -> {
                // the db value password = md5(password + salt), and the salt is "176r8p"
                String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
                String dbValuePassword = customerInfo.getPassword();
                return Objects.equals(dbValuePassword, md5Password);
            })
            .orElseThrow(() -> new LoginFailedException());

        // invalid last session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        session = request.getSession(true);
        session.setAttribute(CommonConstant.CURRENT_CUSTOMER_KEY, customer);
        return ApiResult.success(true);
    }

    /**
     * customer logout
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public ApiResult<Boolean> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return ApiResult.success(true);
    }

}
