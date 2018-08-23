package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.ProjectVersionBiz;
import com.lister.itms.form.ProjectVersionForm;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ProjectVersionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━by:LISTER
 * Describe : 项目版本Controller
 * Created by Lister<18627416004@163.com/> on 2018/6/8 下午5:17.
 * Version : 1.0
 */
@Controller
@RequestMapping("/projectVersion")
public class ProjectVersionController extends BaseController {

    @Autowired
    private ProjectVersionBiz projectVersionBiz;

    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model, Long id){
        model.addAttribute("projectId",id);
        model.addAttribute("projectVersion",new ProjectVersionVo());
        model.addAttribute("title","添加版本");
        return "/projectmanager/versionForm";
    }

    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model, ProjectVersionVo projectVersionVo){
        projectVersionVo = projectVersionBiz.getVersionById(projectVersionVo.getId());
        model.addAttribute("projectId",null);
        model.addAttribute("projectVersion",projectVersionVo);
        model.addAttribute("title","编辑版本");
        return "/projectmanager/versionForm";
    }
    
    /**
     * 版本列表
     * @param versionVo
     * @return
     */
    @RequestMapping("/page")
    public @ResponseBody
    MyPageInfo<ProjectVersionVo> page(MyPageInfo page, ProjectVersionVo versionVo) {
        return projectVersionBiz.page(page,versionVo);     
    }

    /**
     * 版本归档
     * @param projectVersionForm
     * @return
     */
    @RequestMapping("/guidang")
    public @ResponseBody
    ResponseResult guidang(ProjectVersionForm projectVersionForm) {
        projectVersionForm.setUpdateBy(getUserInfo().getName());
        projectVersionForm.setState("归档");
        projectVersionBiz.update(projectVersionForm);
        return ResponseResult.createResult();
    }

    /**
     * 新增版本
     * @param projectVersionForm
     * @return
     */
    @RequestDesc("新增版本")
    @RequestMapping("/create")
    public @ResponseBody
    ResponseResult create(@Valid ProjectVersionForm projectVersionForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        projectVersionForm.setCreateBy(getUserInfo().getName());
        projectVersionBiz.create(projectVersionForm);
        return ResponseResult.createResult();
    }

    /**
     *
     * @param projectVersionForm
     * @return
     */
    @RequestDesc("编辑版本")
    @RequestMapping("/update")
    public @ResponseBody
    ResponseResult<Boolean> update(@Valid ProjectVersionForm projectVersionForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        projectVersionForm.setUpdateBy(getUserInfo().getName());
        projectVersionBiz.update(projectVersionForm);
        return ResponseResult.createResult();
    }

    /**
     * 删除版本
     * @param projectVersionForm
     * @return
     */
    @RequestMapping("/del")
    @RequestDesc("删除版本")
    public @ResponseBody
    ResponseResult<Boolean> del(ProjectVersionForm projectVersionForm){
        projectVersionForm.setUpdateBy(getUserInfo().getName());
        projectVersionBiz.delete(projectVersionForm);
        return ResponseResult.createResult();
    }
    
}
