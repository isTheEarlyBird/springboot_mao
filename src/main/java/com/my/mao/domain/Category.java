package com.my.mao.domain;

import lombok.Data;

/**
 * 分类bean
 */
@Data
public class Category {
    private Integer id;
    private String name;
    private Integer hot;

    public Category() {
    }

    public Category(String name, Integer hot) {
        this.name = name;
        this.hot = hot;
    }

    public Category(Integer id, String name, Integer hot) {
        this.id = id;
        this.name = name;
        this.hot = hot;
    }
}
