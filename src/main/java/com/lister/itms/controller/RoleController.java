package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.ResourceBiz;
import com.lister.itms.biz.RoleBiz;
import com.lister.itms.form.RoleForm;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.RoleVo;
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
@SuppressWarnings("all")
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    private ResourceBiz resourceBiz;

    @ViewDesc("角色管理")
    @RequestMapping("/roleIndex")
    public String toview(Model model) {
        return "/rolemanager/list";
    }

    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model) {
        model.addAttribute("title", "添加角色");
        model.addAttribute("role", new RoleVo());
        return "/rolemanager/form";
    }

    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model, RoleVo roleVo) {
        roleVo = roleBiz.getRoleById(roleVo.getRoleId());
        model.addAttribute("title", "编辑角色");
        model.addAttribute("role", roleVo);
        return "/rolemanager/form";
    }

    /**
     * 查询角色信息
     *
     * @param pageInfo
     * @param role
     * @return
     */
    @RequestMapping("/page")
    public @ResponseBody
    MyPageInfo<RoleVo> page(MyPageInfo pageInfo, RoleVo role) {
        return roleBiz.page(pageInfo, role);
    }

    /**
     * 新增角色
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRole")
    @RequestDesc("新增角色信息")
    public ResponseResult addRole(@Valid RoleForm roleForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        roleForm.setUserId(getUserInfo().getId());
        roleBiz.addRole(roleForm);
        return ResponseResult.createResult();
    }

    /**
     * 修改角色信息
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequestDesc("修改角色信息")
    public ResponseResult updateRole(@Valid RoleForm roleForm, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        roleForm.setUserId(getUserInfo().getId());
        roleBiz.updateRole(roleForm);
        return ResponseResult.createResult();
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    @RequestDesc("删除角色信息")
    public ResponseResult delete(@Valid @NotEmpty(message = "角色编号不允许为空") Long id,BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        roleBiz.delete(id);
        return ResponseResult.createResult();
    }

    /**
     * 获取所有角色
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/allrole")
    public List<RoleVo> getAllRole() {
        return roleBiz.getAllRole(null);
    }
}
