package com.my.mao.service.impl;

import com.github.pagehelper.PageHelper;
import com.my.mao.dao.CategoryDao;
import com.my.mao.dao.ProductDao;
import com.my.mao.dao.ProductImgDao;
import com.my.mao.domain.Category;
import com.my.mao.domain.Product;
import com.my.mao.domain.ProductImg;
import com.my.mao.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    public List<Category> findAllCategory(){
        return categoryDao.findAllCategory();
    }

    @Override
    public void insertCategory(Category category) {
        categoryDao.insertCategory(category);
    }

    @Override
    public Category findCategoryById(Integer cid) {
        return categoryDao.findCategoryById(cid);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    @Override
    public List<Product> findAllProduct(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return productDao.findAllProduct();
    }

    @Override
    public Product findProductById(String pid) {
        return productDao.findProductById(pid);
    }

    @Override
    public void insertProduct(Product product) {
        productDao.insertProduct(product);
        List<ProductImg> list = product.getImgs();
        if (!list.isEmpty()){
            for (ProductImg productImg : list) {
                productImgDao.insertProductImg(productImg, product.getId());
            }
        }
    }

    @Override
    public void updateProduct(Product product) {
        String pid = product.getId();
        productDao.updateProduct(product);
        List<ProductImg> list = product.getImgs();
        //没有上传图片不需要修改
        if (null != list && !list.isEmpty()){
            //上传了图片
            //删除数据库中图片数据
            productImgDao.deleteImgById(pid);

            for (int i = 0; i < list.size(); i ++) {
                ProductImg productImg = list.get(i);

                productImgDao.insertProductImg(productImg, product.getId());
            }
        }
    }

    @Override
    public void deleteProductById(String pid) {
        //删除该商品的图片
        productImgDao.deleteImgById(pid);
        //删除该商品
        productDao.deleteProductById(pid);
    }

    @Override
    public Category findCategoryByName(String categoryName) {
        return productDao.findCategoryByName(categoryName);
    }
}
