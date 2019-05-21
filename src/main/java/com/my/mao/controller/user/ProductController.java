package com.my.mao.controller.user;

import com.my.mao.domain.Product;
import com.my.mao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 根据id查询商品
     */
    @GetMapping("/findProductById.do")
    public ModelAndView findProductById(@RequestParam(value = "pid", required = true) String pid, HttpServletRequest request){
        Product product = productService.findProductById(pid);
        ModelAndView mv = new ModelAndView();
        mv.addObject("product", product);
        mv.setViewName("user/show-product");
        return mv;
    }
}
