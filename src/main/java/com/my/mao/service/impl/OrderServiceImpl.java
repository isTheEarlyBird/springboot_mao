package com.my.mao.service.impl;

import com.my.mao.dao.OrderDao;
import com.my.mao.domain.Cart;
import com.my.mao.domain.CartItem;
import com.my.mao.domain.OrderItem;
import com.my.mao.domain.OrderProduct;
import com.my.mao.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void addOrder(OrderItem orderItem, Cart cart) {
        orderDao.addOrder(orderItem, cart.getTotal());

        //获取购物车中每项商品
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            orderDao.addOrderProduct(cartItem,orderItem.getId());
        }
    }

    @Override
    public void payOrder(String date, String orderId) {
        orderDao.payOrder(date, orderId);
    }

    @Override
    public void addOrderProduct(List<CartItem> cartItems, String oid) {
        for (CartItem cartItem : cartItems) {
            orderDao.addOrderProduct(cartItem, oid);
        }
    }

    @Override
    public Map<OrderItem, List<OrderProduct>> orderlist(String uid) {
        Map<OrderItem, List<OrderProduct>> map = new HashMap<>();
        //获取所有订单
        List<OrderItem> orderItemList = orderDao.findAllOrderItemByUid(uid);
        //获取所对应的商品
        for (OrderItem orderItem : orderItemList) {
            List<OrderProduct> orderProducts = orderDao.findOrderProductByOid(orderItem.getId());
            map.put(orderItem, orderProducts);
        }

        return map;
    }

    @Override
    public void changeProductNum(String id, Integer num) {
        orderDao.changeProductNum(id, num);
    }
}
