package com.my.mao.domain;

import lombok.Data;

/**
 * 每一个订单
 */
@Data
public class OrderItem {

    private String id; //订单id
    private String address; // 收货地址
    private String recipient; //收货人
    private String phone; // 联系电话
    private String userMessage; // 买家留言
    private String createDate; // 创建时间
    private String payDate; // 付款时间
    private String deliveryDate; //发货时间
    private Float total; //这个订单所要支付的总数
    private Integer payStatus = 0; //订单状态，0为未付款， 1为已付款
    private String payStatusStr; //订单状态，0为未付款， 1为已付款
    private Integer deliveryStatus = 0; //订单状态，0为未发货， 1为已发货
    private String deliveryStatusStr; //订单状态，0为未发货， 1为已发货
    private User user; //用户

    public String getPayStatusStr() {
        if (payStatus == 0) {
            payStatusStr = "未付款";
        }else {
            payStatusStr = "已付款";
        }
        return payStatusStr;
    }

    public String getDeliveryStatusStr() {
        if(deliveryStatus == 0) {
            deliveryStatusStr = "未发货";
        }else {
            deliveryStatusStr = "已发货";
        }
        return deliveryStatusStr;
    }
}
