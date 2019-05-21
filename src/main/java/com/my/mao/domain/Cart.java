package com.my.mao.domain;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 一个购物车
 */
@Data
public class Cart {

    private List<CartItem> cartItems = new LinkedList<>();
    private Float total = 0.00f;

}
