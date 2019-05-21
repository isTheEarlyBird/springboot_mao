package com.my.mao.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.my.mao.domain.OrderItem;
import com.my.mao.domain.OrderProduct;
import com.my.mao.domain.Role;
import com.my.mao.service.AdminOrderService;
import com.my.mao.utils.DateUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * 所有后台管理员都可以调用
 */

@Controller
@RequestMapping("/admin")
@RequiresRoles(Role.COMMON)
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    /**
     * 根据发货情况查询订单
     * @return
     */
    @RequestMapping("/findUnprocessedOrder.do")
    public ModelAndView findUnprocessedOrder(@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
                                             @RequestParam(value = "size", required = true, defaultValue = "5") Integer size,
                                             @RequestParam(value = "deliveryStatus", required = true, defaultValue = "0") Integer deliveryStatus){

        List<OrderItem> orderItemList = adminOrderService.findUnprocessedOrder(page, size, deliveryStatus);

        PageInfo<OrderItem> pageInfo = new PageInfo<>(orderItemList);

        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        // deliveryStatus为0就是查询还没有处理订单，为1就是查询已经处理订单
        if (deliveryStatus == 0) {
            mv.setViewName("admin/order/admin-unprocessed-order");
        }else {
            mv.setViewName("admin/order/admin-processed-order");
        }
        return mv;
    }

    /**
     * 确认订单已发货
     * @param oid
     * @return
     */
    @RequestMapping("/sureProcessed.do")
    public ModelAndView sureProcessed(String oid){

        adminOrderService.sureProcessed(DateUtils.date2String(new Date()), oid);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/findUnprocessedOrder.do?page=1&limit=5&deliveryStatus=0");
        return mv;
    }


    /**
     * 展示该订单的所有商品
     * @param oid
     * @return
     */
    @RequestMapping(value = "/showOrderProduct.do",  produces="text/html;charset=UTF-8")
    @ResponseBody
    public String showOrderProduct(String oid){
        List<OrderProduct> orderProducts = adminOrderService.showOrderProduct(oid);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderProducts", orderProducts);

        return jsonObject.toJSONString();
    }
}
