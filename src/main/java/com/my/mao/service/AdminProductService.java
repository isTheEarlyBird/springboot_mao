package com.my.mao.service;

import com.my.mao.domain.Category;
import com.my.mao.domain.Product;

import java.util.List;

public interface AdminProductService {

    /**
     * 查询所有分类
     * @return
     */
    List<Category> findAllCategory();

    /**
     * 添加分类
     * @param category
     * @return
     */
    void insertCategory(Category category);

    /**
     * 根据id查询分类
     * @param cid
     * @return
     */
    Category findCategoryById(Integer cid);

    /**
     * 修改分类
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 查询所有商品
     * @param page  当前页
     * @param size  一页多少个数据
     * @return
     */
    List<Product> findAllProduct(Integer page, Integer size);

    /**
     * 查询商品详细信息
     * @param pid
     * @return
     */
    Product findProductById(String pid);

    /**
     * 保存商品
     * @param product
     */
    void insertProduct(Product product);

    /**
     * 更新商品信息
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 删除商品
     * @param pid
     */
    void deleteProductById(String pid);



    /**
     * 根据分类名查询分类
     * @param categoryName
     * @return
     */
    Category findCategoryByName(String categoryName);
}
