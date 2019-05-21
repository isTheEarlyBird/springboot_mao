package com.my.mao.dao;

import com.my.mao.domain.Category;
import com.my.mao.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductDao {

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @Select("select * from product where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock"),
            @Result(column = "hot", property = "hot"),
            @Result(column = "cid", property = "category", one = @One(select = "com.my.mao.dao.CategoryDao.findCategoryById")),
            @Result(column = "id", property = "imgs",javaType = List.class, many = @Many(select = "com.my.mao.dao.ProductImgDao.findByPid"))
    })
    public Product findProductById(String id);

    /**
     * 查询所有商品
     * @return
     */
    @Select("select * from product")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock"),
            @Result(column = "hot", property = "hot"),
            @Result(column = "cid", property = "category", one = @One(select = "com.my.mao.dao.CategoryDao.findCategoryById")),
            @Result(column = "id", property = "imgs",javaType = List.class, many = @Many(select = "com.my.mao.dao.ProductImgDao.findByPid"))
    })
    List<Product> findAllProduct();

    /**
     * 根据分类  查询热门商品
     * @param categoryId  所属分类id
     * @param limit  限制个数
     * @return
     */
    @Select("select * from product where cid = #{categoryId} order by hot DESC limit 0, #{limit}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock"),
            @Result(column = "hot", property = "hot"),
            @Result(column = "cid", property = "category", one = @One(select = "com.my.mao.dao.CategoryDao.findCategoryById")),
            @Result(column = "id", property = "imgs",javaType = List.class, many = @Many(select = "com.my.mao.dao.ProductImgDao.findByPid"))
    })
    List<Product> findProductByHotCategoryId(@Param("categoryId") Integer categoryId, @Param("limit") Integer limit);

    /**
     * 添加商品
     */
    @Insert("insert into product values(#{id}, #{name}, #{price}, #{stock}, #{hot}, #{category.id})")
    public void insertProduct(Product product);

    @Update("update product set name = #{name}, price = #{price}, stock = #{stock}, hot = #{hot}, cid = #{category.id} where id = #{id}")
    public void updateProduct(Product product);

    /**
     * 删除商品
     * @param pid
     */
    @Delete("delete from product where id = #{pid}")
    void deleteProductById(String pid);

    /**
     * 获取该分类下的所有商品id
     * @param cid
     * @return
     */
    @Select("select id from product where cid = #{cid}")
    List<String> findPidByCid(String cid);


    /**
     * 根据分类名查询分类
     * @param categoryName
     * @return
     */
    @Select("select * from category where name = #{categoryName}")
    Category findCategoryByName(String categoryName);
}
