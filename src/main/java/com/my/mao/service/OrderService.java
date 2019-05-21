package com.my.mao.service;

import com.my.mao.domain.Cart;
import com.my.mao.domain.CartItem;
import com.my.mao.domain.OrderItem;
import com.my.mao.domain.OrderProduct;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 添加订单
     * @param orderItem  订单内容（除商品外的信息）
     * @param cart   购物车（商品）
     */
    void addOrder(OrderItem orderItem, Cart cart);

    /**
     * 支付成功，改变状态
     * @param date   支付时间
     * @param orderId   订单id
     */
    void payOrder(String date, String orderId);

    /**
     * 订单中的商品
     * @param cart
     */
    void addOrderProduct(List<CartItem> cart, String oid);


    /**
     * 显示所有订单
     */
    Map<OrderItem, List<OrderProduct>> orderlist(String uid);

    /**
     * 修改商品数量
     * @param id
     * @param num
     */
    void changeProductNum(String id, Integer num);
}
