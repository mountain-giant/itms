package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.ProductBiz;
import com.lister.itms.biz.RoleBiz;
import com.lister.itms.biz.SysConfigBiz;
import com.lister.itms.biz.UserBiz;
import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.UserInfoForm;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ProductVo;
import com.lister.itms.vo.RoleVo;
import com.lister.itms.vo.SysConfigVo;
import com.lister.itms.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/12 18:34.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/12 18:34.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserInfoController extends BaseController {

    /**
     * 用户默认头像
     */
    public static final String DEFAULT_USER_IMAGE_NAME = "/img/default_user_head_image.png";

    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private ProductBiz productBiz;

    @Autowired
    private SysConfigBiz sysConfigBiz;

    @ViewDesc("用户列表")
    @RequestMapping("/userIndex")
    public String toview() {
        return "/usermanager/list";
    }

    /**
     * 进入新增页面
     *
     * @param model
     * @return
     */
    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model) {
        List<RoleVo> roles = roleBiz.getAllRole(null);
        List<ProductVo> productVos = productBiz.getAllProduct(null);
        List<SysConfigVo> processRoles = sysConfigBiz.getByType("ProcessRole");
        model.addAttribute("title", "添加用户");
        model.addAttribute("userInfo", new UserInfoVo());
        model.addAttribute("roles", roles);
        model.addAttribute("products", productVos);
        model.addAttribute("processRoles", processRoles);
        return "/usermanager/form";
    }


    /**
     * 进入编辑页面
     *
     * @param model
     * @return
     */
    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model, UserInfoVo userInfoVo) {
        userInfoVo = userBiz.getUser(userInfoVo.getId());
        List<RoleVo> roles = roleBiz.getAllRole(userInfoVo.getId());
        List<ProductVo> productVos = productBiz.getAllProduct(userInfoVo.getId());
        List<SysConfigVo> processRoles = sysConfigBiz.getProcessRoleByUserId(userInfoVo.getId());
        model.addAttribute("title", "编辑用户");
        model.addAttribute("userInfo", userInfoVo);
        model.addAttribute("roles", roles);
        model.addAttribute("products", productVos);
        model.addAttribute("processRoles", processRoles);
        return "/usermanager/form";
    }

    /**
     * 查询用户信息
     *
     * @param page
     * @param user
     * @return
     */
    @RequestMapping("/page")
    public @ResponseBody
    MyPageInfo<UserInfoVo> page(MyPageInfo page, UserInfoVo user) {
        return userBiz.page(page, user);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllUser")
    public List<UserInfoVo> getAllUser() {
        return userBiz.getAllUser();
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/adduser")
    @RequestDesc("新增系统用户")
    public ResponseResult addUser(@Valid UserInfoForm user, BindingResult bindingResult) throws Exception {
        super.hasParamsErrot(bindingResult);
        user.setHeadImage(DEFAULT_USER_IMAGE_NAME);
        userBiz.addUser(user);
        return ResponseResult.createResult();
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequestDesc("修改用户信息")
    public ResponseResult updateUser(@Valid UserInfoForm user, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        UserDO userDO = getUserInfo();
        if (user.getId().equals(userDO.getId())) {
            throw new BizException("不允许修改自己的信息");
        }
        userBiz.updateUser(user);
        return ResponseResult.createResult();
    }

    /**
     * 修改自己的信息
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMe")
    @RequestDesc("修改用户信息")
    public ResponseResult updateMe(@Valid UserInfoForm user, BindingResult bindingResult) {
        super.hasParamsErrot(bindingResult);
        userBiz.updateSelf(user);
        return ResponseResult.createResult();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    @RequestDesc("删除用户信息")
    public ResponseResult delete(Long id, HttpSession session) throws Exception {
        UserDO loginUser = (UserDO) session.getAttribute("user");
        if (id == null) {
            throw new Exception("用户ID不可以为空");
        }
        if (id == 1) {
            throw new BizException("不可以删除系统第一用户");
        }
        if (loginUser.getId().longValue() == id.longValue()) {
            throw new BizException("无法删除自己");
        }
        userBiz.delete(id);
        return ResponseResult.createResult();
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/updatePwd")
    public ResponseResult updateUserPassword(UserDO user) throws Exception {
        userBiz.updatePassword(user);
        return ResponseResult.createResult();
    }

    /**
     * 根据流程角色查询用户信息
     * @param processRole
     * @return
     */
   /* @RequestMapping("/getByProcessRole")
    public @ResponseBody
    List<UserDO> getUserByProcessRole(String processRole, String isDbChange, String processName) {
        if (StringUtils.isEmpty(processRole)) {
            try {
                String userProcessRole = getUserInfo().getProcessRole();
                if (StringUtils.isNotBlank(userProcessRole)) {
                    if (userProcessRole.equals(PublishProcessRoleEnum.ProcessRole.PM.code)) {
                        return userBiz.getUserByProcessRole(PublishProcessRoleEnum.ProcessRole.TEST.code);
                    } else if (userProcessRole.equals(PublishProcessRoleEnum.ProcessRole.TEST.code)) {
                        return userBiz.getUserByProcessRole(PublishProcessRoleEnum.ProcessRole.SA.code);
                    } else if (userProcessRole.equals(PublishProcessRoleEnum.ProcessRole.SA.code) && "Y".equals(isDbChange)) {
                        return userBiz.getUserByProcessRole(PublishProcessRoleEnum.ProcessRole.DBA.code);
                    } else if (userProcessRole.equals(PublishProcessRoleEnum.ProcessRole.SA.code) && "N".equals(isDbChange)) {
                        return userBiz.getUserByProcessRole(PublishProcessRoleEnum.ProcessRole.OPS.code);
                    } else if (userProcessRole.equals(PublishProcessRoleEnum.ProcessRole.DBA.code) && PublishProcessRoleEnum.ProcessName.DB_CHECK_SQL.name.equals(processName)) {
                        return userBiz.getUserByProcessRole(PublishProcessRoleEnum.ProcessRole.DBA.code);
                    } else if (userProcessRole.equals(PublishProcessRoleEnum.ProcessRole.DBA.code) && PublishProcessRoleEnum.ProcessName.DB_RUN_SQL.name.equals(processName)) {
                        return userBiz.getUserByProcessRole(PublishProcessRoleEnum.ProcessRole.OPS.code);
                    } else if (userProcessRole.equals(PublishProcessRoleEnum.ProcessRole.OPS.code)) {
                        return userBiz.getUserByProcessRole(PublishProcessRoleEnum.ProcessRole.PD.code);
                    }
                }
            } catch (BizException e) {
                getResponse().setStatus(500);
            }
        }
        return userBiz.getUserByProcessRole(processRole);
    }*/
}
