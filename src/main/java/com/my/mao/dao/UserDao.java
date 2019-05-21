package com.my.mao.dao;

import com.my.mao.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao {

    /**
     * 登录操作 查询用户是否存在
     * @param name   用户名
     * @param password   用户密码
     * @return
     */
    @Select("select * from user where name = #{name} and password = #{password}")
    public User checkExistsUser(@Param("name") String name, @Param("password") String password) throws Exception;

    /**
     * 注册用户
     * @param user
     */
    @Insert("insert into user values(replace(UUID(), '-', ''), #{name}, #{password}, #{createTime})")
    void addUser(User user) throws Exception;

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findUserById(String id);

    /**
     * 根据用户名查询 用户名是否已存在
     * @param name
     * @return
     */
    @Select("select count(*) from user where name = #{name}")
    Long checkedUserExists(String name);
}
