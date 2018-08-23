package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.EnvironmentsBiz;
import com.lister.itms.dao.entity.EnvironmentsDO;
import com.lister.itms.form.EnvironmentsForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.vo.EnvironmentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Describe :
 * Created by LY on 2018/6/7 18:01.
 * Update reason :
 * Updated by LY on 2018/6/7 18:01.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Controller
@RequestMapping("/environments")
public class EnvironmentsController extends BaseController{

    @Autowired
    private EnvironmentsBiz environmentsBiz;

    /**
     * 
     * @return
     */
    @ViewDesc("环境列表")
    @RequestMapping("/index")
    public String toview(){
        return "/environmentsmanager/list";
    }

    /**
     * 
     * @param model
     * @return
     */
    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model){
        model.addAttribute("environments",new EnvironmentsVo());
        model.addAttribute("title","添加环境");
        return "/environmentsmanager/form";
    }

    /**
     * 
     * @param model
     * @param environments
     * @return
     */
    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model, EnvironmentsVo environments){
        environments = environmentsBiz.getEnvironment(environments.getId());
        model.addAttribute("environments",environments);
        model.addAttribute("title","编辑环境");
        return "/environmentsmanager/form";
    }
    
    /**
     * 新增环境
     * @param environmentsForm
     * @return
     */
    @ResponseBody
    @RequestDesc("新增环境")
    @RequestMapping("/create")
    public ResponseResult createEnvironments(@Valid EnvironmentsForm environmentsForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        environmentsForm.setCreateBy(getUserInfo().getName());
        environmentsForm.setUpdateBy(getUserInfo().getName());
        environmentsBiz.createEnvironments(environmentsForm);
        return ResponseResult.createResult();
    }

    /**
     * 
     * @param environmentsForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestDesc("编辑环境")
    @RequestMapping("/update")
    public ResponseResult updateEnvironments(@Valid EnvironmentsForm environmentsForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        environmentsForm.setUpdateBy(getUserInfo().getName());
        environmentsBiz.updateEnvironments(environmentsForm);
        return ResponseResult.createResult();
    }

    /**
     * 
     * @param environmentsForm
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    @RequestDesc("删除环境")
    public ResponseResult del(EnvironmentsForm environmentsForm){
        environmentsForm.setUpdateBy(getUserInfo().getName());
        environmentsBiz.deleteEnvironments(environmentsForm);
        return ResponseResult.createResult();
    }

    /**
     * 
     * @param environmentsForm
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestDesc("测试")
    @RequestMapping("/test")
    public ResponseResult test(EnvironmentsForm environmentsForm) throws Exception {
        environmentsBiz.testEnvironments(environmentsForm);
        return ResponseResult.createResult();
    }

    /**
     * 
     * @param page
     * @param environmentsVo
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public MyPageInfo<EnvironmentsDO> page(MyPageInfo page, EnvironmentsVo environmentsVo){
        return environmentsBiz.page(page,environmentsVo);
    }

}
