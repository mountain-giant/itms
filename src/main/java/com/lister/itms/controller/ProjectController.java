package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.ProductBiz;
import com.lister.itms.biz.ProjectBiz;
import com.lister.itms.form.ProjectForm;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ProductVo;
import com.lister.itms.vo.ProjectVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Describe :
 * Created by Lister on 2018/7/16 8:11 PM.
 * Version : 1.0
 */
@Slf4j
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {


    @Autowired
    private ProjectBiz projectBiz;

    @Autowired
    private ProductBiz productBiz;

    /**
     * 进入项目概括页面
     * @return
     */
    @ViewDesc
    @RequestMapping("/projectSurvey")
    public String projectSurvey(){
        return "/projectmanager/projectSurvey";
    }

    /**
     * 进入项目列表页面
     * @param model
     * @return
     */
    @ViewDesc("项目列表")
    @RequestMapping("/index")
    public String toview(Model model){
        List<ProductVo> productVos = productBiz.getAllProduct(null);
        model.addAttribute("products",productVos);
        return "/projectmanager/list";
    }

    /**
     * 进入项目新增页面
     * @param model
     * @return
     */
    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model){
        List<ProductVo> productVos = productBiz.getAllProduct(null);
        model.addAttribute("products",productVos);
        model.addAttribute("project",new ProjectVo());
        model.addAttribute("title","添加项目");
        return "/projectmanager/form";
    }

    /**
     * 进入项目修改页面
     * @param model
     * @param projectVo
     * @return
     */
    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model,ProjectVo projectVo){
        projectVo = projectBiz.getProject(projectVo.getId());
        model.addAttribute("project",projectVo);
        List<ProductVo> productVos = productBiz.getAllByProjectShowHave(projectVo.getId());
        model.addAttribute("products",productVos);
        model.addAttribute("title","编辑项目");
        return "/projectmanager/form";
    }

    /**
     * 获取项目详情
     * @param model
     * @param id
     * @return
     */
    @ViewDesc
    @RequestMapping("/info")
    public String projectInfo(Model model,Long id) {
        ProjectVo projectVo = projectBiz.getProject(id);
        model.addAttribute("project",projectVo);
        return "/projectmanager/info";
    }

    /**
     * 分页查询项目列表
     * @param page
     * @param project
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public MyPageInfo<ProjectVo> page(MyPageInfo page, ProjectVo project){
        return projectBiz.page(page,project);
    }

    /**
     * 创建项目
     * @param projectForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestDesc("创建项目")
    @RequestMapping("/create")
    public ResponseResult createProject(ProjectForm projectForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        projectForm.setCreateBy(getUserInfo().getName());
        projectBiz.createProject(projectForm);
        return ResponseResult.createResult();
    }

    /**
     * 修改项目
     * @param projectForm
     * @return
     */
    @ResponseBody
    @RequestDesc("修改项目")
    @RequestMapping("/update")
    public ResponseResult updateProject(ProjectForm projectForm,BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        projectForm.setUpdateBy(getUserInfo().getName());
        projectBiz.updateProject(projectForm);
        return ResponseResult.createResult();
    }
    
}
