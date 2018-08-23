package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.ResourceBiz;
import com.lister.itms.form.ResourceForm;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ResourceVo;
import com.lister.itms.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/18 21:16.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/18 21:16.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceBiz resourceBiz;

    @ViewDesc("资源管理")
    @RequestMapping("/menuIndex")
    public String toview() {
        return "/resourcemanager/list";
    }

    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model) {
        model.addAttribute("title", "添加资源");
        model.addAttribute("resource", new ResourceVo());
        return "/resourcemanager/form";
    }

    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model, ResourceVo resource) {
        resource = resourceBiz.getInfo(resource.getId());
        model.addAttribute("title", "编辑资源");
        model.addAttribute("resource", resource);
        return "/resourcemanager/form";
    }

    /**
     * 分页查询
     *
     * @param page
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public MyPageInfo<ResourceVo> page(MyPageInfo page, ResourceVo menu) {
        return resourceBiz.page(page, menu);
    }

    /**
     * 新增菜单
     *
     * @param resource
     * @return
     */
    @ResponseBody
    @RequestMapping("/addMenu")
    @RequestDesc("新增菜单")
    public ResponseResult addMenu(@Valid ResourceForm resource, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        resource.setUserId(getUserInfo().getId());
        resourceBiz.addMenu(resource);
        return ResponseResult.createResult();
    }

    /**
     * 修改菜单信息
     *
     * @param resource
     * @return
     */             
    @ResponseBody
    @RequestDesc("修改菜单信息")
    @RequestMapping("/update")
    public ResponseResult updateMenu(@Valid ResourceForm resource,BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        resource.setUserId(getUserInfo().getId());
        resourceBiz.updateMenu(resource);
        return ResponseResult.createResult();
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    @RequestDesc("删除菜单信息")
    public ResponseResult delete(@Valid @NotEmpty(message = "资源编号不允许为空") Long id,BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        resourceBiz.delete(id);
        return ResponseResult.createResult();
    }


    /**
     * 菜单节点
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/permissions")
    public @ResponseBody
    List<TreeVo> getPermissions(Long roleId) {
        return resourceBiz.getMenuForPermissions(roleId);
    }
}
