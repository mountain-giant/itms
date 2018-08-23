package com.lister.itms.controller;

import com.lister.itms.biz.RoleBiz;
import com.lister.itms.biz.UserBiz;
import com.lister.itms.vo.UserActivityFrequencyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Describe : 系统功能的报表控制器
 * Created by Lister<728661851@qq.com/> on 16/12/12 10:05.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/12/12 10:05.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Controller
@RequestMapping("/report")
public class SystemReportController extends BaseController {
    
    private static final Logger L = LoggerFactory.getLogger(SystemReportController.class);

    @Autowired
    private UserBiz userBiz;
    
    @Autowired
    private RoleBiz roleBiz;

    /**
     * 用户活动频率报表
      * @return
     */
    @RequestMapping("/actityReport")
    public @ResponseBody
    UserActivityFrequencyVo userActityReport(Integer days){
        if (days==null) {
            days = 5;   // 默认五天的数据
        }
        return userBiz.getUserActityReport(days);
    }

    /**
     * 获取角色用户数量
     * @return
     */
    @RequestMapping("/roleUserNumReport")
    public @ResponseBody
    List<Map<String,Object>> roleUserNumberReport(){
        return roleBiz.getRoleDownUserCount();
    } 
}
