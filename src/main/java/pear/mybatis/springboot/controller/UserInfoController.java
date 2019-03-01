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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pear.mybatis.springboot.model.PasswordModel;
import pear.mybatis.springboot.service.UserInfoService;
import pear.mybatis.springboot.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public Map<String, Object> view(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("data", userInfoService.getById(id));
        return map;
    }

    /**
     * 修改个人信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Map<String, Object> updateUser(@RequestParam("file") MultipartFile file,
                                          UserInfo userInfo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (!userInfo.validateData()) {
            map.put("success", false);
            map.put("code", "400");
            map.put("message", "参数不正确");
            return map;
        }
        userInfo.setPicture(file.getBytes());
        return userInfoService.updateUser(userInfo);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Map<String, Object> getUserList() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("data", userInfoService.getAll());
        return map;
    }

    @RequestMapping(value = "/getTeacherList", method = RequestMethod.GET)
    public Map<String, Object> getTeacherList() {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("data", userInfoService.getTeacherList());
        return map;
    }

    @PostMapping("/upload") // 等价于 @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uplaod(HttpServletRequest req, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) path = new File("");
            File upload = new File(path.getAbsolutePath(), "static/images/upload/");
           if (!upload.exists()) upload.mkdirs();
            File dest = new File(upload.getAbsoluteFile() + File.separator + fileName);
            file.transferTo(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        }

        return "showImg";
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Map<String, Object> updatePassword(@NotBlank @RequestBody PasswordModel userInfo) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(userInfo.getUserId()) || StringUtils.isBlank(userInfo.getPassword()) || StringUtils.isBlank(userInfo.getPasswordnew())) {
            map.put("success", false);
            map.put("code", "400");
            map.put("message", "参数不正确");
            return map;
        }
        return userInfoService.updatePassword(userInfo.getUserId(), userInfo.getPassword(), userInfo.getPasswordnew());
    }
}
