package com.my.mao.utils;

import com.my.mao.domain.ProductImg;
import org.apache.commons.collections.ListUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 上传图片工具类
 */
public class UploadImgFileUtils {

    public static List<ProductImg> uploadImg(MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws IOException {
        List<ProductImg> imgs = new ArrayList<>();
        // 获取所有文件选择器名（<input type="file" multiple id="exampleInputFile" name="files">）
        Iterator<String> fileNames = multiRequest.getFileNames();

        while(fileNames.hasNext()){
            // 获取该文件选择器上传的文件
            List<MultipartFile> files = multiRequest.getFiles(fileNames.next());

            //如果没有上传文件，但是会上传一个""字符串，要排除掉
            if (files.size() == 1){
                MultipartFile multipartFile = files.get(0);
                String originalFilename = multipartFile.getOriginalFilename();
                if (StringUtils.isEmpty(originalFilename)){
                    return null;
                }
            }

            for (MultipartFile file : files) {
                // 获取文件名
                String filename = file.getOriginalFilename();
                // 获取文件名后缀
                String[] names = filename.split("\\.");
                String detialName = names[names.length-1];
                // 判断是否是图片
                if(detialName.toUpperCase().equals("PNG") || detialName.toUpperCase().equals("GIF") || detialName.toUpperCase().equals("JPEG") || detialName.toUpperCase().equals("SVG") || detialName.toUpperCase().equals("JPG")){

                    //URLDecoder.decode解决文件夹中文乱码问题
                    String path =  URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath()+"static/upload/", "UTF-8");

                    //查看路径是否存在，不存在就创建
                    File f = new File(path);
                    if(!f.exists()){
                        System.out.println("create");
                        f.mkdirs();
                    }
                    //spring的transferTo保存文件方法
                    file.transferTo(new File(path, file.getOriginalFilename()));

                    ProductImg productImg = new ProductImg();
                    productImg.setUrl("/upload/"+filename);
                    imgs.add(productImg);
                }else {
                    return null;
                }
            }
        }
        return imgs;
    }
}
