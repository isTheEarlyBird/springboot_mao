package com.my.mao.dao;

import com.my.mao.domain.CartItem;
import com.my.mao.domain.OrderItem;
import com.my.mao.domain.OrderProduct;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface OrderDao {

    /**
     * 添加订单
     * @param orderItem
     */
    @Insert("insert into orderitem(id, address, recipient, phone, userMessage, createDate, total, uid) values(#{orderItem.id}, #{orderItem.address},#{orderItem.recipient},#{orderItem.phone}, #{orderItem.userMessage}, #{orderItem.createDate}, #{total}, #{orderItem.user.id})")
    public void addOrder(@Param("orderItem") OrderItem orderItem, @Param("total") Float total);

    /**
     * 支付成功，改变状态
     * @param date
     * @param oid
     */
    @Update("update orderitem set payDate = #{date}, payStatus = 1 where id =  #{oid}")
    void payOrder(@Param("date") String date, @Param("oid") String oid);

    /**
     * 订单中的商品
     * @param cartItem
     */
    @Insert("insert into order_product values(null, #{cartItem.num}, #{cartItem.product.id}, #{oid})")
    void addOrderProduct(@Param("cartItem") CartItem cartItem, @Param("oid") String oid);

    /**
     * 查询所有订单
     * @return
     */
    @Select("select * from orderitem where uid = #{uid}")
    List<OrderItem> findAllOrderItemByUid(String uid);



    /**
     * 根据订单id查询对应的商品
     * @param oid
     * @return
     */
    @Results({
            @Result(column = "num", property = "num"),
            @Result(column = "pid", property = "product", one = @One(select = "com.my.mao.dao.ProductDao.findProductById"))
    })
    @Select("select num, pid from order_product where oid in(#{oid})")
    List<OrderProduct> findOrderProductByOid(String oid);

    /**
     * 修改商品数量
     * @param id
     * @param num
     */
    @Update("update product set stock = #{num} where id = #{id}")
    void changeProductNum(@Param("id") String id,@Param("num") Integer num);
}
