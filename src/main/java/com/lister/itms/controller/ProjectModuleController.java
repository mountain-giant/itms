package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.ProjectModuleBiz;
import com.lister.itms.form.ProjectModuleForm;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ProjectModuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * Describe :
 * Created by LY on 2018/6/8 18:07.
 * Update reason :
 * Updated by LY on 2018/6/8 18:07.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Controller
@RequestMapping("/module")
public class ProjectModuleController extends BaseController {

    @Autowired
    private ProjectModuleBiz projectModuleBiz;

    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model, Long id) {
        model.addAttribute("projectId", id);
        model.addAttribute("projectModule", new ProjectModuleVo());
        model.addAttribute("title", "添加模块");
        return "/projectmanager/moduleForm";
    }

    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model, ProjectModuleVo projectModuleVo) {
        projectModuleVo = projectModuleBiz.getProjectModule(projectModuleVo.getId());
        model.addAttribute("projectModule", projectModuleVo);
        model.addAttribute("title", "编辑模块");
        return "/projectmanager/moduleForm";
    }

    /**
     * 分页查询
     *
     * @param page
     * @param projectModuleVo
     * @return
     */
    @RequestMapping("/page")
    public @ResponseBody
    MyPageInfo<ProjectModuleVo> page(MyPageInfo page, ProjectModuleVo projectModuleVo) {
        return projectModuleBiz.page(page, projectModuleVo);
    }

    /**
     * 新增模块
     *
     * @param projectModuleForm
     * @return
     */
    @RequestDesc("新增模块")
    @RequestMapping("/create")
    public @ResponseBody
    ResponseResult createModule(@Valid ProjectModuleForm projectModuleForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        projectModuleForm.setCreateBy(getUserInfo().getName());
        projectModuleBiz.createProjectModule(projectModuleForm);
        return ResponseResult.createResult();
    }

    /**
     * @param projectModuleForm
     * @return
     */
    @RequestDesc("编辑模块")
    @RequestMapping("/update")
    public @ResponseBody
    ResponseResult updateEnvironments(ProjectModuleForm projectModuleForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        projectModuleForm.setUpdateBy(getUserInfo().getName());
        projectModuleBiz.updateProjectModule(projectModuleForm);
        return ResponseResult.createResult();
    }

    /**
     * 删除模块
     *
     * @param projectModuleForm
     * @return
     */
    @RequestMapping("/del")
    @RequestDesc("删除模块")
    public @ResponseBody
    ResponseResult del(ProjectModuleForm projectModuleForm) {
        projectModuleForm.setUpdateBy(getUserInfo().getName());
        projectModuleBiz.deleteProjectModule(projectModuleForm);
        return ResponseResult.createResult();
    }
}
