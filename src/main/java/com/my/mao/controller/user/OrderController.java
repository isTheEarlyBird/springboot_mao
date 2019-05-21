package com.my.mao.controller.user;

import com.my.mao.domain.*;
import com.my.mao.service.OrderService;
import com.my.mao.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user/permission/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 提交订单
     * @param orderItem
     * @param request
     * @return
     */
    @PostMapping("/insertOrder.do")
    public ModelAndView addOrder(OrderItem orderItem, HttpServletRequest request){

        String oid = UUID.randomUUID().toString().replace("-", "");
        orderItem.setId(oid);
        orderItem.setCreateDate(DateUtils.date2String(new Date()));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        orderItem.setUser(user);

        // 获取session中取购物车cart
        Cart cart = (Cart) session.getAttribute("cart");
        orderService.addOrder(orderItem, cart);
        //遍历购物车
        for (CartItem cartItem : cart.getCartItems()) {
            //修改商品的数量
            Product product = cartItem.getProduct();
            orderService.changeProductNum(product.getId(), product.getStock() - cartItem.getNum());
        }


        //移除session中的cart
        session.removeAttribute("cart");
        ModelAndView mv = new ModelAndView();
        mv.addObject("orderId", oid);
        mv.setViewName("user/permission/pay");

        return mv;
    }

    /**
     * 支付成功，改变状态
     * @param request
     */
    @RequestMapping("/payOrder.do")
    public ModelAndView pay(String orderId, HttpServletRequest request){

        orderService.payOrder(DateUtils.date2String(new Date()), orderId);
        ModelAndView mv = new ModelAndView("redirect:/user/permission/order/findOrderList.do");
        return mv;
    }

    /**
     * 显示所有订单
     * @return
     */
    @RequestMapping("/findOrderList.do")
    public ModelAndView orderlist(HttpServletRequest request){

        //获取当前用户
        String userid =  ((User)request.getSession().getAttribute("user")).getId();
        //获取每个订单和订单中的商品
        Map<OrderItem, List<OrderProduct>> map = orderService.orderlist(userid);
        ModelAndView mv = new ModelAndView();
        mv.addObject("map", map);
        mv.setViewName("user/permission/orderlist");
        return mv;
    }
}
