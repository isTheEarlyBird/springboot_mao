package com.my.mao.service;

import com.my.mao.domain.Product;

public interface ProductService {

    /**
     * 根据id查询商品
     */
    public Product findProductById(String id);
}
