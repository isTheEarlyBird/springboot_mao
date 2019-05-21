package com.my.mao.service.impl;

import com.github.pagehelper.PageHelper;
import com.my.mao.dao.AdminOrderDao;
import com.my.mao.dao.OrderDao;
import com.my.mao.domain.OrderItem;
import com.my.mao.domain.OrderProduct;
import com.my.mao.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private AdminOrderDao adminOrderDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<OrderItem> findUnprocessedOrder(Integer page, Integer size, Integer deliveryStatus) {
        PageHelper.startPage(page, size);
        return adminOrderDao.findUnprocessedOrder(deliveryStatus);
    }

    @Override
    public void sureProcessed(String date, String oid) {
        adminOrderDao.sureProcessed(date, oid);
    }

    @Override
    public List<OrderProduct> showOrderProduct(String oid) {
        return orderDao.findOrderProductByOid(oid);
    }
}
