package com.lister.itms.biz;

import com.lister.itms.exception.BizException;
import com.lister.itms.form.ProjectForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ProjectVo;

import java.util.List;

/**
 * 项目管理
 * Created by Lister.
 */
public interface ProjectBiz {


    /**
     * 创建项目
     * @param projectForm
     * @return
     */
    void createProject(ProjectForm projectForm) throws BizException;

    /**
     * 修改项目
     * @param projectForm
     * @return
     */
    void updateProject(ProjectForm projectForm) throws BizException;

    /**
     * 分页查询
     * @param page
     * @param project
     * @return
     */
    MyPageInfo<ProjectVo> page(MyPageInfo page, ProjectVo project);

    /**
     * 获取项目详情
     * @param id
     * @return
     */
    ProjectVo getProject(Long id);
}
