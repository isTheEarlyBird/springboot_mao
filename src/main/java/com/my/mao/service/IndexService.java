package com.my.mao.service;

import com.my.mao.domain.Category;
import com.my.mao.domain.Product;

import java.util.List;

public interface IndexService {
    List<Category> findHotCategory(Integer limit);

    List<Product> findProductByHotCategoryId(Integer categoryId, Integer limit);
}
