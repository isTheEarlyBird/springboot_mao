package com.my.mao.dao;

import com.my.mao.domain.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryDao {

    /**
     * 根据id查询分类
     * @param cid
     * @return
     */
    @Select("select * from category where id = #{cid}")
    Category findCategoryById(Integer cid);

    /**
     * 查询热门的分类
     * @param limit   查询个数
     * @return
     */
    @Select("select * from category order by hot DESC limit 0,#{limit}")
    List<Category> findHotCategory(Integer limit);

    /**
     * 查询所有分类
     * @return
     */
    @Select("select * from category")
    List<Category> findAllCategory();

    /**
     * 添加分类
     * @param category
     * @return
     */
    @Insert("insert into category values(null, #{name}, #{hot})")
    void insertCategory(Category category);

    /**
     * 修改分类
     * @param category
     */
    @Update("update category set name = #{name}, hot = #{hot} where id = #{id}")
    void updateCategory(Category category);

}
