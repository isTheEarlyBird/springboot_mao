package com.my.mao.controller.user;

import com.my.mao.domain.Cart;
import com.my.mao.domain.CartItem;
import com.my.mao.domain.Product;
import com.my.mao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user/permission/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    /**
     * 清空购物车
     * @param request
     * @return
     */
    @RequestMapping("/clearCart.do")
    public ModelAndView clearCart(HttpServletRequest request){

        request.getSession().removeAttribute("cart");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/permission/cart");
        return mv;
    }

    /**
     * 删除购物车中这个商品
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping("/deleteCart.do")
    public ModelAndView deleteCart(String productId, HttpServletRequest request){

        //获取session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        List<CartItem> cartItems = cart.getCartItems();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            Product product = cartItem.getProduct();
            if(product.getId().equals(productId)){
                //删除这个商品
                cartItems.remove(i);
                cart.setTotal(cart.getTotal() - cartItem.getSub());
            }
        }
        if (cart.getCartItems().size() > 0){
            //重新添加回session
            session.setAttribute("cart", cart);
        }else {
            // 从session删除这个cartsession
            session.removeAttribute("cart");
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/permission/cart");
        return mv;
    }

    /**
     * 添加到购物车
     * @param productId  商品id
     * @param num   商品数量
     */
    @RequestMapping("/insertCart.do")
    public String addCart(String productId, Integer num, HttpServletRequest request){

        // 获取session
        HttpSession session = request.getSession();

        // 获取该商品
        Product product = productService.findProductById(productId);
        // 购物车一项
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setNum(num);
        Float sub = num * product.getPrice();
        cartItem.setSub(sub);

        //加入到session
        Object obj =  session.getAttribute("cart");
        Cart cart;
        if (null == obj && !(obj instanceof Cart)){
            cart = new Cart();
        }else {
            cart = (Cart) obj;
            //判断购物车是否已经有该商品
            List<CartItem> cartItems = cart.getCartItems();
            for (CartItem item : cartItems) {
                if(item.getProduct().getId().equals(productId)){
                    item.setNum(item.getNum() + num);
                    //数量*单价
                    Float money = num * item.getProduct().getPrice();
                    item.setSub(item.getSub() + money);

                    cart.setTotal(cart.getTotal() + money);

                    return "redirect:/product/findProductById.do?pid="+productId;
                }
            }
        }

        cart.getCartItems().add(cartItem);
        cart.setTotal(cart.getTotal() + sub);
        session.setAttribute("cart", cart);
        session.setMaxInactiveInterval(24 * 60 * 60);

        return "redirect:/product/findProductById.do?pid="+productId;
    }
}
