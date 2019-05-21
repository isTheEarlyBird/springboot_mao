package com.my.mao.domain;

import lombok.Data;

import java.util.List;

/**
 * 商品bean
 */
@Data
public class Product {

    private String id;
    private String name;
    private float price;
    private Integer stock;
    private Integer hot;
    private Category category;
    private List<ProductImg> imgs;

}
