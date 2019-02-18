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

package pear.mybatis.springboot.service;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.ognl.IntHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pear.mybatis.springboot.mapper.UserInfoMapper;
import pear.mybatis.springboot.model.UserInfo;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author liuzh
 * @since 2016-01-31 21:42
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    public UserInfo getById(String id) {
        return userInfoMapper.getOne(id);
    }

    /**
     *  新增
     * @param user
     * @return
     */
    public Map<String,Object> save(UserInfo user) {
        Map<String,Object> map=new HashMap<>();
        try {
            user.setUserId(UUID.randomUUID().toString());
            userInfoMapper.save(user);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("code","500");
            map.put("message","服务异常");
        }
        return  map;

    }

    /**
     *  修改密码
     * @param userId
     * @param password
     * @return
     */
    public Map<String,Object> updatePassword( String userId,String password,String passwordNew) {
        Map<String,Object> map=new HashMap<>();
        UserInfo userInfo=userInfoMapper.getOne(userId);
        if (userInfo==null){
            map.put("success",false);
            map.put("code","4001");
            map.put("message","用户不存在");
            return  map;
        }
        if (!password.equals(userInfo.getPassword())){
            map.put("success",false);
            map.put("code","4002");
            map.put("message","密码错误");
            return map;
        }
        try {
            userInfoMapper.updatePassword(userId,passwordNew);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("code","500");
            map.put("message","服务异常");
        }
        return  map;

    }

    /**
     *  修改用户信息
     * @param userInfo
     * @return
     */
    public Map<String,Object> updateUser(UserInfo userInfo) {
        Map<String,Object> map=new HashMap<>();
        if(userInfoMapper.getOne(userInfo.getUserId())==null){
            map.put("success",false);
            map.put("code","4001");
            map.put("message","用户不存在");
            return  map;
        }
        try {
            userInfoMapper.updateUser(userInfo);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("code","500");
            map.put("message","服务异常");
        }
        return  map;
    }

    /**
     * 查询用户名已注册的用户
     * @param username
     * @return
     */
    public UserInfo findByUserName(String username) {
        return  userInfoMapper.findByUserName(username);
    }

    public List<UserInfo> getAll() {
        return userInfoMapper.getAllUserInfo();
    }

    public void deleteById(String id) {
        userInfoMapper.deleteById(id);
    }


}
