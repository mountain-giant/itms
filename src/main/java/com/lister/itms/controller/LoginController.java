package com.lister.itms.controller;

import com.lister.itms.biz.LoginBiz;
import com.lister.itms.biz.ResourceBiz;
import com.lister.itms.biz.SysLogBiz;
import com.lister.itms.dao.entity.ResourceDO;
import com.lister.itms.dao.entity.SysLogDO;
import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.enums.SysLogTypeEnums;
import com.lister.itms.form.LoginForm;
import com.lister.itms.modal.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe : 用户登录登出
 * Created by Lister<728661851@qq.com/> on 16/11/10 15:38.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/10 15:38.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
@Controller
public class LoginController extends BaseController {
    
    @Autowired
    private LoginBiz loginBiz;
    
    @Autowired
    private SysLogBiz sysLogBiz;
    
    @Autowired
    private ResourceBiz resourceBiz;
   
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }


    /**
     * 用户登录验证
     * @param loginForm
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/login")
    public ResponseResult userLogin(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request,HttpSession session) throws Exception {
        super.hasParamsErrot(bindingResult);
        Long startTime= System.currentTimeMillis();
        UserDO u = loginBiz.userLogin(loginForm);
        session.setAttribute("user",u);
        // 获取左侧菜单
        session.setAttribute("menus", resourceBiz.getMenu(u.getId()).toString());
        // 获取所有的菜单
        List<ResourceDO> menus = resourceBiz.getAllMenus();
        Map<String,String> menusMap = new HashMap<>();
        for (ResourceDO menu : menus){
            menusMap.put(menu.getHref(),menu.getHref());
        }
        List<ResourceDO> mymenus = resourceBiz.getMySelfMenus(u.getId());
        Map<String,String> mymenusMap = new HashMap<>();
        for (ResourceDO menu : mymenus){
            mymenusMap.put(menu.getHref(),menu.getHref());
        }
        // 获取拥有的操作权限
        List<ResourceDO> funs = resourceBiz.getFunction(u.getId());
        Map<String,String> userFuns = new HashMap<>();
        for (ResourceDO fun : funs) {
            userFuns.put(fun.getHref(),fun.getName());
        }
        session.setAttribute("userFuns",userFuns);
        session.setAttribute("allmenus",menusMap);
        session.setAttribute("mymenus",mymenusMap);
        SysLogDO sysLog = new SysLogDO();
        sysLog.setIp(request.getRemoteUser());
        sysLog.setLogName("系统登录");
        sysLog.setParams("客户端信息：" + request.getHeader("user-agent"));
        sysLog.setUser(u.getId());
        sysLog.setResult("登录成功");
        sysLog.setTimeConsuming((System.currentTimeMillis())-startTime);
        sysLog.setLogType(SysLogTypeEnums.LOGIN.code);
        sysLogBiz.addSysLog(sysLog);
        return ResponseResult.createResult();
    }

    /**
     * 系统登出
     * @param session
     * @return 登录页面
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "/system/login";
    }

    
}
