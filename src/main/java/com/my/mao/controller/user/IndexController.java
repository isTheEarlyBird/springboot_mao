package com.my.mao.controller.user;

import com.my.mao.domain.Category;
import com.my.mao.domain.Product;
import com.my.mao.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 首页 controller
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping({"/", "/index","/index/init.do"})
    public ModelAndView initIndex(){
        ModelAndView mv = new ModelAndView();
        // 获取10个热门分类
        List<Category> hotCategoryList = indexService.findHotCategory(10);
        mv.addObject("hotCategoryList", hotCategoryList);
        if (!hotCategoryList.isEmpty()) {

            // 获取热门分类下的热门商品
            List<Product> productList1 = indexService.findProductByHotCategoryId(hotCategoryList.get(0).getId(), 8);
            mv.addObject("productList1", productList1);
            if (hotCategoryList.size() > 1){
                List<Product> productList2 = indexService.findProductByHotCategoryId(hotCategoryList.get(1).getId(), 8);
                mv.addObject("productList2", productList2);
            }
        }
        mv.setViewName("user/main");
        return mv;
    }
}
