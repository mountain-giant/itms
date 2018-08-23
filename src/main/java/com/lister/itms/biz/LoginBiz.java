package com.lister.itms.biz;

import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.LoginForm;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/10 15:57.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/10 15:57.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@SuppressWarnings("all")
public interface LoginBiz {
    /**
     * 用户登录
     * @param loginForm
     */
    UserDO userLogin(LoginForm loginForm) throws Exception;

}
