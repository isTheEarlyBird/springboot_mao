package com.my.mao.dao;

import com.my.mao.domain.OrderItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface AdminOrderDao {

    /**
     * 查询没有处理（发货）的订单
     * @return
     */
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "address", property = "address"),
            @Result(column = "recipient", property = "recipient"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "userMessage", property = "userMessage"),
            @Result(column = "createDate", property = "createDate"),
            @Result(column = "payDate", property = "payDate"),
            @Result(column = "deliveryDate", property = "deliveryDate"),
            @Result(column = "total", property = "total"),
            @Result(column = "payStatus", property = "payStatus"),
            @Result(column = "deliveryStatus", property = "deliveryStatus"),
            @Result(column = "uid", property = "user", one = @One(select = "com.my.mao.dao.UserDao.findUserById")),

    })
    @Select("select * from orderitem where deliveryStatus = #{deliveryStatus} order by payStatus, createDate")
    List<OrderItem> findUnprocessedOrder(Integer deliveryStatus);


    /**
     * 确认发货了
     * @param date
     * @param oid
     */
    @Update("update orderitem set deliveryStatus = 1, deliveryDate = #{date} where id = #{oid}")
    void sureProcessed(@Param("date") String date, @Param("oid") String oid);


}
