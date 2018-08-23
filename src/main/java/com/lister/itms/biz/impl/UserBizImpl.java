package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.UserBiz;
import com.lister.itms.dao.entity.ProcessUserDO;
import com.lister.itms.dao.entity.ProductUserDO;
import com.lister.itms.dao.entity.RoleUserDO;
import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.dao.mapper.ProcessUserMapper;
import com.lister.itms.dao.mapper.ProductUserMapper;
import com.lister.itms.dao.mapper.RoleUserMapper;
import com.lister.itms.dao.mapper.UserMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.UserInfoForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.utils.DateUtil;
import com.lister.itms.utils.MD5Generator;
import com.lister.itms.vo.UserActivityFrequencyVo;
import com.lister.itms.vo.UserFrequencyVo;
import com.lister.itms.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/12 18:36.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/12 18:36.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
@Service
public class UserBizImpl implements UserBiz {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RoleUserMapper roleUserMapper;
    
    @Autowired
    private ProductUserMapper productUserMapper;
    
    @Autowired
    private ProcessUserMapper processUserMapper;
    
    /**
     * 查询系统用户信息
     *  @param page
     */
    @Override
    public MyPageInfo page(MyPageInfo page, UserInfoVo user) {
        UserDO userDO = DTOUtils.map(user,UserDO.class);
        log.info("系统用户信息查询,param="+page.toString());
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        Page<UserDO> userInfos = userMapper.listPage(userDO);
        MyPageInfo pageInfo = MyPageInfo.create(userInfos);
        
        List<UserInfoVo> userInfoVos = DTOUtils.map(pageInfo.getRows(),UserInfoVo.class);
        pageInfo.setRows(userInfoVos);
        return pageInfo;
    }

    /**
     * 修改自己的信息
     * @param user
     */
    public void updateSelf(UserInfoForm user){
        UserDO userDO = DTOUtils.map(user,UserDO.class);
        userDO.setPassword(null);
        userMapper.updateByPrimaryKeySelective(userDO);    
    }
    

    /**
     * 删除
     * 修改用户状态
     * @param id
     */
    public void delete(Long id) {
        UserDO user = new UserDO();
        user.setId(id);
        userMapper.updateUserStatus(user);
        roleUserMapper.deleteByUserId(id);
    }

    /**
     * 修改密码
     *
     * @param user
     */
    public void updatePassword(UserDO user) {
        user.setPassword(MD5Generator.toMD5(user.getPassword()));
        user.setNewPassword(MD5Generator.toMD5(user.getNewPassword()));
        if (userMapper.getUserByLoginNameAndPwd(user) == null) {
            throw new BizException("旧密码错误");
        }
        user.setPassword(user.getNewPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 获取所有用户的活动频率报表数据
     *
     * @return
     * @param days
     */
    public UserActivityFrequencyVo getUserActityReport(Integer days) {
        UserActivityFrequencyVo frequencyVO = new UserActivityFrequencyVo();
        for (int i = days-1;i >= 0;i--) {
            String date = DateUtil.daySubtraction(i);
            frequencyVO.getDates().add(date);
            List<UserFrequencyVo> results = userMapper.listUserActityNumByDate(date);
            for (UserFrequencyVo frequency : results) {                        
                Map<String,List<Integer>> datas = frequencyVO.getDatas();
                if (datas.get(frequency.getUserName())==null){
                    List<Integer> nums = new ArrayList<Integer>();
                    nums.add(frequency.getActivityNum());
                    datas.put(frequency.getUserName(),nums);
                } else {
                    List<Integer> nums = datas.get(frequency.getUserName()); 
                    nums.add(frequency.getActivityNum());
                }
            }
        }
        log.info("UserActivityNum Result:"+frequencyVO.toString());
        return frequencyVO;
    }

    /**
     * 查询所有的用户
     * @return
     */
    @Override
    public List<UserInfoVo> getAllUser() {
        List<UserDO> userDOS = userMapper.getAllUser();
        return DTOUtils.map(userDOS,UserInfoVo.class);
    }

    /**
     * 根据流程角色查询用户信息
     *
     * @param processRole
     * @return
     */
    @Override
    public List<UserDO> getUserByProcessRole(String processRole) {
        return userMapper.getUserByProcessRole(processRole);
    }

    /**
     * 新增用户
     *
     * @param user
     */
    @Override
    @Transactional
    public void addUser(UserInfoForm user) throws Exception {
        UserDO userDO = DTOUtils.map(user,UserDO.class);
        // 默认密码123123
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(MD5Generator.toMD5("123123"));
        } else {
            user.setPassword(MD5Generator.toMD5(user.getPassword()));
        }
        {
            UserDO userDB = userMapper.getUserByLoginName(user.getLoginName());
            if (userDB != null) {
                throw new BizException("用户名已存在");
            }
        }
        userMapper.insert(userDO);
        // 保存用户的角色
        if (user.getRoleIds() != null) {
            for (Long roleId : user.getRoleIds()) {
                roleUserMapper.insert(new RoleUserDO(user.getId(), roleId));
            }
        }

        // 保存用户关联产品
        if (user.getProductIds() != null) {
            for (Long productId : user.getProductIds()) {
                productUserMapper.insert(new ProductUserDO(user.getId(),productId));
            }
        }

        // 保存用户流程角色
        if (user.getProcessRoles() != null) {
            for (String processRole : user.getProcessRoles()) {
                processUserMapper.insert(new ProcessUserDO(user.getId(),processRole));
            }
        }
    }

    /**
     * 编辑员工
     *
     * @param user
     */
    @Override
    @Transactional
    public void updateUser(UserInfoForm user) {
        UserDO userDO = DTOUtils.map(user,UserDO.class);
        // 默认密码123123
        userMapper.updateByPrimaryKeySelective(userDO);
        // 保存用户的角色
        if (user.getRoleIds() != null) {
            roleUserMapper.deleteByUserId(userDO.getId());
            for (Long roleId : user.getRoleIds()) {
                roleUserMapper.insert(new RoleUserDO(userDO.getId(), roleId));
            }
        }

        // 保存用户关联产品
        if (user.getProductIds() != null) {
            productUserMapper.deleteByUserId(userDO.getId());
            for (Long productId : user.getProductIds()) {
                productUserMapper.insert(new ProductUserDO(userDO.getId(),productId));
            }
        }

        // 保存用户流程角色
        if (user.getProcessRoles() != null) {
            processUserMapper.deleteByUserId(userDO.getId());
            for (String processRole : user.getProcessRoles()) {
                processUserMapper.insert(new ProcessUserDO(userDO.getId(),processRole));
            }
        }
    }

    /**
     * 获取用户详情
     *
     * @param id
     * @return
     */
    @Override
    public UserInfoVo getUser(Long id) {
        UserDO userDO = userMapper.getById(id);
        if (userDO == null) {
            throw new BizException("查询失败");
        }
        return DTOUtils.map(userDO,UserInfoVo.class);
    }
}
