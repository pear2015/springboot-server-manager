package pear.mybatis.springboot.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pear.mybatis.springboot.model.PasswordModel;
import pear.mybatis.springboot.model.UserInfo;
import pear.mybatis.springboot.service.UserInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PEAR on 2019/2/16.
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(HttpServletRequest request, HttpSession session) {
        Map<String,Object> map=new HashMap<>();
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        UserInfo userInfo = userInfoService.findByUserName(userName);
        if(userInfo==null){
            map.put("success",false);
            map.put("code","3001");
            map.put("message","用户不存在");
        }else{
            if(!userInfo.getPassword().equals(password)){
                map.put("success",false);
                map.put("code","3002");
                map.put("message","密码错误");
            }
            map.put("success",true);
            map.put("data",userInfo);
        }
        return   map;
    }

    /**
     * 注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String,Object> register(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<>();
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String password2=request.getParameter("password2");
        if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)||StringUtils.isBlank(password2)){
            map.put("success",false);
            map.put("code","400");
            map.put("message","参数不正确");
        }
        if(password.equals(password2)) {
            UserInfo userInfo = userInfoService.findByUserName(userName);
            if(userInfo==null) {
                UserInfo user=new UserInfo();
                user.setUserName(userName);
                user.setPassword(password);
               return   userInfoService.save(user);
            }else {
                map.put("success",false);
                map.put("code","4001");
                map.put("message","用户不存在");
            }
        }else {
            map.put("success",false);
            map.put("code","4002");
            map.put("message","密码错误");
        }
        return map;
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Map<String,Object> updatePassword(@NotBlank @RequestBody PasswordModel userInfo) {
        Map<String,Object> map=new HashMap<>();
        if(StringUtils.isBlank(userInfo.getUserId())||StringUtils.isBlank(userInfo.getPassword())||StringUtils.isBlank(userInfo.getPasswordnew())){
            map.put("success",false);
            map.put("code","400");
            map.put("message","参数不正确");
            return  map;
        }
        return  userInfoService.updatePassword(userInfo.getUserId(),userInfo.getPassword(),userInfo.getPasswordnew());
    }
}
