package com.my.mao.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取上一个路径 工具类
 */
public class RefererPathUtils {

    /**
     * 获取上一个路径
     * @param request
     * @param indexUrl 没有上一个路径则跳转到该路径
     * @return
     */
    public static String getRefererPathUtils(HttpServletRequest request, String indexUrl){
        // 获取上一个路径
        String referer = request.getHeader("Referer");
        String path;
        if (null == referer || referer.length() <= 0 || "".equals(referer)){
            path = indexUrl;
        }else {
            path = referer.split(request.getContextPath())[1];
        }
        return path;
    }
}
