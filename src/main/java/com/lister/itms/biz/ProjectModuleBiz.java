package com.lister.itms.biz;

import com.lister.itms.exception.BizException;
import com.lister.itms.form.ProjectModuleForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ProjectModuleVo;


/**
 * Describe :
 * Created by LY on 2018/6/8 17:18.
 * Update reason :
 * Updated by LY on 2018/6/8 17:18.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public interface ProjectModuleBiz {
    /**
     *
     * @param pageInfo
     * @param projectModuleVo
     * @return
     */
    MyPageInfo<ProjectModuleVo> page(MyPageInfo pageInfo, ProjectModuleVo projectModuleVo);

    /**
     * 创建模块
     * @param projectModuleForm
     * @throws BizException
     */
    void createProjectModule(ProjectModuleForm projectModuleForm) throws BizException;

    /**
     * 编辑模块
     * @param projectModuleForm
     * @throws BizException
     */
    void updateProjectModule(ProjectModuleForm projectModuleForm) throws BizException;

    /**
     *
     * @param projectModuleForm
     */
    void deleteProjectModule(ProjectModuleForm projectModuleForm);

    /**
     * 模块详情
     * @param id
     * @return
     */
    ProjectModuleVo getProjectModule(Long id);
}
