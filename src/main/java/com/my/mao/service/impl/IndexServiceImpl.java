package com.my.mao.service.impl;

import com.my.mao.dao.CategoryDao;
import com.my.mao.dao.ProductDao;
import com.my.mao.domain.Category;
import com.my.mao.domain.Product;
import com.my.mao.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    /**
     * 寻找最火热的分类
     * @param limit  限制寻找的个数
     */
    @Override
    public List<Category> findHotCategory(Integer limit) {
        return categoryDao.findHotCategory(limit);
    }

    /**
     * 根据分类，查询该分类最热门的商品
     * @param categoryId   所属分类id
     * @param limit      限制个数
     * @return
     */
    @Override
    public List<Product> findProductByHotCategoryId(Integer categoryId, Integer limit) {
        return productDao.findProductByHotCategoryId(categoryId, limit);
    }
}
