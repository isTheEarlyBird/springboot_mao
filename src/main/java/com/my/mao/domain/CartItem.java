package com.my.mao.domain;

import lombok.Data;

/**
 * 购物车里的一项
 */
@Data
public class CartItem {

    private Product product;
    private Integer num;
    private Float sub;

}
