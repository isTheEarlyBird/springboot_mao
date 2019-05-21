package com.my.mao.service.impl;

import com.my.mao.dao.ProductDao;
import com.my.mao.domain.Product;
import com.my.mao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product findProductById(String id) {
        return productDao.findProductById(id);
    }
}
