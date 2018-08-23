package com.lister.itms.biz;



import com.lister.itms.form.RoleForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.RoleVo;

import java.util.List;
import java.util.Map;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/12 20:36.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/12 20:36.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@SuppressWarnings("all")
public interface RoleBiz {

    /**
     * 查询所有的Role角色
     * @return
     * @param userId
     */
    List<RoleVo> getAllRole(Long userId);
    
    /**
     * 查询系统角色信息
     *  @param pageInfo
     * @param role
     * @param user  @return
     */
    MyPageInfo<RoleVo> page(MyPageInfo pageInfo, RoleVo roleVo);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 获取角色下角色数量
     * @return
     */
    List<Map<String,Object>> getRoleDownUserCount();

    /**
     * 根据角色查询角色拥有的角色名称
     * 提供给前端主界面显示
     * @param userId
     * @return 以数组的方式返回
     */
    String getRoleByUser(Long userId);

    /**
     * 根据Id查询角色信息
     * @param roleId
     * @return
     */
    RoleVo getRoleById(Long roleId);

    /**
     * 新增角色
     * @param roleForm
     */
    void addRole(RoleForm roleForm);

    /**
     * 编辑角色
     * @param roleForm
     */
    void updateRole(RoleForm roleForm);
}
