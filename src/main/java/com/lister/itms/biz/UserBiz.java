package com.lister.itms.biz;


import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.form.UserInfoForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.UserActivityFrequencyVo;
import com.lister.itms.vo.UserInfoVo;

import java.util.List;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/12 18:35.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/12 18:35.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public interface UserBiz {

    /**
     * 查询系统用户信息
     *  @param pageInfo
     * @param user
     * @param user  @return
     */
    MyPageInfo page(MyPageInfo pageInfo, UserInfoVo user);

    /**
     * 修改自己的信息
     * @param user
     */
    void updateSelf(UserInfoForm user);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 修改密码
     * @param user
     */
    void updatePassword(UserDO user) throws Exception;

    /**
     * 获取所有用户的活动频率报表数据
     * @return
     * @param days
     */
    UserActivityFrequencyVo getUserActityReport(Integer days);

    /**
     * 查询所有的用户
     * @return
     */
    List<UserInfoVo> getAllUser();

    /**
     * 根据流程角色查询用户信息
     * @param processRole
     * @return
     */
    List<UserDO> getUserByProcessRole(String processRole);

    /**
     * 新增用户
     * @param user
     */
    void addUser(UserInfoForm user) throws Exception;

    /**
     * 编辑员工
     * @param user
     */
    void updateUser(UserInfoForm user);

    /**
     * 获取用户详情
     * @param id
     * @return
     */
    UserInfoVo getUser(Long id);
}
