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

package pear.mybatis.springboot.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pear.mybatis.springboot.service.UserInfoService;
import pear.mybatis.springboot.model.UserInfo;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuzh
 * @since 2015-12-19 11:10
 */
@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查看用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public Map<String,Object> view(@PathVariable String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("success",true);
        map.put("data",userInfoService.getById(id));
        return map;
    }

    /**
     * 修改个人信息
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Map<String,Object> updateUser(@RequestBody @NotBlank UserInfo userInfo) {
        Map<String,Object> map=new HashMap<>();
        if(!userInfo.validateData()){
            map.put("success",false);
            map.put("code","400");
            map.put("message","参数不正确");
            return  map;
        }
        return userInfoService.updateUser(userInfo);
    }
}
