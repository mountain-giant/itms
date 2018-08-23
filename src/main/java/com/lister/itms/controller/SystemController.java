package com.lister.itms.controller;

import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.SysLogBiz;
import com.lister.itms.biz.UserBiz;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.SysLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Describe :
 * Created by Lister on 2018/7/16 5:05 PM.
 * Version : 1.0
 */
@Slf4j
@Controller
@RequestMapping("/sys")
public class SystemController extends BaseController {

    @Autowired
    private UserBiz userBiz;
    
    @Autowired
    private SysLogBiz sysLogBiz;
    
    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        return "/index";
    }

    /**
     * 进入系统操作流水查询页面
     * @return
     */
    @ViewDesc("系统日志")
    @RequestMapping("/operaIndex")
    public String toOperaIndex(Model model){
        model.addAttribute("users",userBiz.getAllUser());
        return "/operaInfo";
    }

    /**
     * 分页查询当前用户的操作记录
     * @param pageInfo
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseBody
    @RequestMapping("/pageOperas")
    public MyPageInfo<SysLogVo> pageOperas(MyPageInfo pageInfo, String startDate, String endDate, String value) {
        return sysLogBiz.pageOperas(pageInfo,value,startDate,endDate);
    }
}
