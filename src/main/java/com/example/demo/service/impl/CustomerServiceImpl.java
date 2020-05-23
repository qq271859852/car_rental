package com.example.demo.service.impl;

import com.example.demo.bean.entity.Customer;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhouxiong
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Customer findCustomerInfo(String telephone) {
        return customerMapper.selectByTelephone(telephone);
    }
}
