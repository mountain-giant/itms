package com.lister.itms.dao.mapper;

import com.github.pagehelper.Page;
import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.vo.UserFrequencyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户相关Mapper
 */
public interface UserMapper {
    
    /**
     * 删除用户
     * @param user
     * @return
     */
    int updateUserStatus(UserDO user);

    /**
     * 新增用户
     * @param record
     * @return
     */
    int insert(UserDO record);

    /**
     * 非空验证修改用户信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UserDO record);

    /**
     * 分页查询
     * @param userDO
     * @return
     */
    Page<UserDO> listPage(UserDO userDO);

    /**
     * 根据日期获取用户使用系统频率
     * @param date
     * @return
     * TODO
     */
    List<UserFrequencyVo> listUserActityNumByDate(String date);

    /**
     * 根据登录名查询用户
     * @param loginName
     */
    UserDO getUserByLoginName(@Param("loginName") String loginName);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    UserDO getById(@Param("id") Long id);

    /**
     * 获取所有的用户
     * @return
     */
    List<UserDO> getAllUser();

    /**
     * 根据流程角色信息查询用户
     * @param processRole
     * @return
     */
    List<UserDO> getUserByProcessRole(String processRole);

    /**
     * 根据账号和密码查询用户
     * @param user
     * @return
     */
    UserDO getUserByLoginNameAndPwd(UserDO user);
}
