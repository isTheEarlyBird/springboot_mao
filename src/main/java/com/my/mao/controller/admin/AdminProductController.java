package com.my.mao.controller.admin;

import com.github.pagehelper.PageInfo;
import com.my.mao.domain.Category;
import com.my.mao.domain.Product;
import com.my.mao.domain.ProductImg;
import com.my.mao.domain.Role;
import com.my.mao.service.AdminProductService;
import com.my.mao.utils.RefererPathUtils;
import com.my.mao.utils.UUIDUtile;
import com.my.mao.utils.UploadImgFileUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * 所有后台管理员都可以调用
 */

@Controller
@RequestMapping("/admin")
@RequiresRoles(Role.COMMON)
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    /**
     * 显示所有分类
     * @return
     */
    @RequestMapping("/findCategory.do")
    public ModelAndView findCategory(){
        List<Category> categories = adminProductService.findAllCategory();

        ModelAndView mv = new ModelAndView();
        mv.addObject("categories", categories);
        mv.setViewName("admin/product/admin-categorys");
        return mv;
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @RequestMapping("/insertCategory.do")
    public ModelAndView insertCategory(Category category){
        String categoryName = category.getName();
        ModelAndView mv = new ModelAndView();
        if (null == categoryName || "".equals(categoryName)){
            mv.setViewName("/admin/product/admin-insertCategory");
            mv.addObject("error", "分类名不允许为空");
            return mv;
        }
        //查询数据库是否已经存在该角色
        if (null != adminProductService.findCategoryByName(categoryName)){
            mv.setViewName("/admin/product/admin-insertCategory");
            mv.addObject("error", "角色已存在");
            return mv;
        }

        adminProductService.insertCategory(category);
        mv.setViewName("redirect:/admin/findCategory.do");
        return mv;
    }


    /**
     * 展示对应的分类信息
     * @param cid
     * @return
     */
    @RequestMapping("/toUpdateCategory.do")
    public ModelAndView updateCategoryJsp(Integer cid){
        Category category = adminProductService.findCategoryById(cid);
        ModelAndView mv = new ModelAndView();
        mv.addObject("category", category);
        mv.setViewName("admin/product/admin-updateCategory");
        return mv;
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PostMapping("/updateCategory.do")
    public String updateCategory(Category category, Model model){
        if (StringUtils.isEmpty(category.getName()) || null == category.getHot()){
            model.addAttribute("error", "不能为空");
            return "redirect:/admin/toUpdateCategory.do?cid="+category.getId();
        }
        adminProductService.updateCategory(category);
        return "redirect:/admin/findCategory.do";
    }

    /**
     * 查询所有商品
     * @return
     */
    @RequestMapping("/findProduct.do")
    public ModelAndView findProduct(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page, @RequestParam(value = "size", required = true, defaultValue = "7") Integer size){
        List<Product> productList = adminProductService.findAllProduct(page, size);
        PageInfo pageInfo = new PageInfo<>(productList);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("admin/product/admin-products");
        return mv;
    }

    /**
     * 展示对应的商品信息，跳转到修改商品页面
     * @param pid
     * @return
     */
    @RequestMapping("/toUpdateProduct.do")
    public ModelAndView toUpdateProduct(String pid){
        //获取对于的商品
        Product product = adminProductService.findProductById(pid);
        //获取所有的分类
        List<Category> categories = adminProductService.findAllCategory();
        ModelAndView mv = new ModelAndView();
        mv.addObject("product", product);
        mv.addObject("categories", categories);
        mv.setViewName("admin/product/admin-showProduct");
        return mv;
    }

    /**
     * 跟新商品信息
     * @param name
     * @param price
     * @param stock
     * @param hot
     * @param cid
     * @param multiRequest
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateProduct.do", method = RequestMethod.POST)
    public ModelAndView updateProduct(String pid, String name, Float price, Integer stock, Integer hot, Integer cid,
                                      MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws IOException {
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isEmpty(pid) || StringUtils.isEmpty(name) || null == price || null == stock || null == hot){

            //返回修改页面（工具类RefererPathUtils获取），如果不是从修改页面过来的，那么会跳转到商品页面
            mv.setViewName(RefererPathUtils.getRefererPathUtils(request, "/admin/findProduct.do"));
            mv.addObject("error", "不能为空");
            return mv;
        }

        Product product = new Product();

        product.setId(pid);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setHot(hot);
        Category category = new Category();
        category.setId(cid);
        product.setCategory(category);

        List<ProductImg> imgs = UploadImgFileUtils.uploadImg(multiRequest, request);
        product.setImgs(imgs);

        //更新商品信息
        adminProductService.updateProduct(product);

        mv.setViewName("redirect:/admin/findProduct.do");
        return mv;
    }

    /**
     * 删除商品
     * @param pid
     * @return
     */
    @RequestMapping("/deleteProductById.do")
    public String deleteProductById(String pid){
        adminProductService.deleteProductById(pid);

        return "redirect:/admin/findProduct.do";
    }

    /**
     * 获取所有分类，跳转到添加商品页面
     * @return
     */
    @RequestMapping("/toInsertProduct.do")
    public ModelAndView addProduct(){
        List<Category> categories = adminProductService.findAllCategory();

        ModelAndView mv = new ModelAndView();
        mv.addObject("categories", categories);
        mv.setViewName("admin/product/admin-insertProduct");
        return mv;
    }


    /**
     * 添加商品
     * @param name
     * @param price
     * @param stock
     * @param hot
     * @param cid
     * @param multiRequest
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/insertProduct.do")
    public String insertProduct(String name, Float price, Integer stock, Integer hot, Integer cid,
                                MultipartHttpServletRequest multiRequest, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, ServletException {
        if ( StringUtils.isEmpty(name) || null == price || null == stock || null == hot || null == multiRequest){
            request.setAttribute("error", "不能为空");
            request.getRequestDispatcher("/admin/toInsertProduct.do").forward(request, response);
            return null;
        }
        List<ProductImg> imgs = UploadImgFileUtils.uploadImg(multiRequest, request);
        if (null == imgs){
            request.setAttribute("error", "文件类型不正确或者没有上传文件");
            request.getRequestDispatcher("/admin/toInsertProduct.do").forward(request, response);
            return null;
        }

        Product product = new Product();

        product.setId(UUIDUtile.getUUID());
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setHot(hot);
        product.setImgs(imgs);

        Category category = new Category();
        category.setId(cid);
        product.setCategory(category);

        // 保存到数据库
        adminProductService.insertProduct(product);

        return "redirect:/admin/findProduct.do";
    }
}
