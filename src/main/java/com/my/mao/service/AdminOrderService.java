package com.my.mao.service;

import com.my.mao.domain.OrderItem;
import com.my.mao.domain.OrderProduct;

import java.util.List;

public interface AdminOrderService {

    /**
     * 查询没有处理（发货）的订单
     * @param page   当前页
     * @param size   一次查询几个数据
     * @param deliveryStatus   查询的订单是未发货（0）或已经发货（1）
     * @return
     */
    List<OrderItem> findUnprocessedOrder(Integer page, Integer size, Integer deliveryStatus);


    /**
     * 确认订单已发货
     * @param date   发货时间
     * @param oid    订单id
     */
    void sureProcessed(String date, String oid);

    /**
     * 显示订单详情（商品）
     * @param oid
     */
    List<OrderProduct> showOrderProduct(String oid);
}
