/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package pear.mybatis.springboot.mapper;

import org.apache.ibatis.annotations.*;
import pear.mybatis.springboot.model.UserInfo;

import java.util.List;

/**
 * @author liuzh_3nofxnp
 * @since 2016-01-22 22:17
 */
public interface UserInfoMapper  {
    @Select("SELECT * FROM user_info  WHERE user_id = #{id}")
    @Results({
            @Result(property = "userId", column = "user_id")
    })
    UserInfo getOne(String id);

    @Select("SELECT * FROM user_info  WHERE user_id = #{id}")
    @Results({
            @Result(property = "userId", column = "user_id")
    })
    List<UserInfo> getAllUserInfo();

   @Delete("DELETE from  user_info  WHERE user_id = #{id} ")
    void deleteById(String id);

    @Select("SELECT * FROM user_info  WHERE username = #{userName}  limit 1")
    @Results({
            @Result(property = "userId", column = "user_id")
    })
    UserInfo findByUserName(String userName);

    @Insert({"insert into user_info(user_id,username,password,userType) values (#{user.userId},#{user.userName},#{user.password},'1')"})
    void save(@Param("user") UserInfo user);

    @Update({"update  user_info set password = #{password} where user_id = #{id}"})
    void updatePassword( @Param("id") String  id,@Param("password") String password);

    @Select("<script> " +
            " update  user_info" +
            " <set>"+
            " <if test=\"user.userName != null\"> username=#{user.userName},</if> " +
            " <if test=\"user.email != null\">  email=#{user.email}</if> " +
            " </set> where user_id =#{user.userId}"+
            " </script> ")
    void updateUser(@Param("user") UserInfo user);
}
