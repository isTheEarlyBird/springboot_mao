package com.my.mao.dao;

import com.my.mao.domain.ProductImg;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
public interface ProductImgDao {

    /**
     * 根据商品id查询图片
     * @param pid
     * @return
     */
    @Select("select * from productImg where pid = #{pid}")
    public ProductImg findByPid(@Param("pid") String pid);

    /**
     * 添加图片
     * @param productImg
     * @param pid
     */
    @Insert("insert into productImg values(null, #{productImg.url}, #{pid})")
    public void insertProductImg(@Param("productImg") ProductImg productImg, @Param("pid") String pid);

    /**
     * 删除图片
     * @param pid
     */
    @Delete("delete from productImg where pid = #{pid}")
    void deleteImgById(String pid);

}
