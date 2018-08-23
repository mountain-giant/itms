package com.lister.itms.biz.impl;

import com.lister.itms.biz.LoginBiz;
import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.dao.mapper.UserMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.LoginForm;
import com.lister.itms.utils.MD5Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/10 16:00.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/10 16:00.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
@Service
public class LoginBizImpl implements LoginBiz {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 用户登录
     * @param loginForm
     */
    public UserDO userLogin(LoginForm loginForm) {
        loginForm.setPassword(MD5Generator.toMD5(loginForm.getPassword()));
        UserDO userDO = userMapper.getUserByLoginName(loginForm.getLoginName());
        if (userDO == null){
            throw new BizException("用户不存在");
        }
        if(!loginForm.getPassword().equals(userDO.getPassword())) {
            throw new BizException("密码错误，请重新输入");
        }
        return userDO;
    }

}
