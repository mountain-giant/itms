package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.ProjectBiz;
import com.lister.itms.dao.entity.ProjectDO;
import com.lister.itms.dao.mapper.ProjectMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.ProjectForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.ProjectVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Lister on 2017/6/15.
 */
@Slf4j
@Service
public class ProjectBizImpl implements ProjectBiz {

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 验证名称是否因存在
     * @param projectName
     * @param id
     * @return
     */
    private boolean checkProjectExists(String projectName, Long id) {
        return projectMapper.checkProjectExists(projectName.trim(), id) > 0;
    }

    /**
     * 数据库一致性校验
     * @param projectForm
     * @throws BizException
     */
    private void checkParams(ProjectForm projectForm) throws BizException {
        if(checkProjectExists(projectForm.getProjectName(), projectForm.getId())){
            throw new BizException("项目名已存在");
        }
    }
    
    /**
     * 创建项目
     *
     * @param projectForm
     * @return
     */
    @Override
    public void createProject(ProjectForm projectForm) throws BizException {
        checkParams(projectForm);
        ProjectDO projectDO = DTOUtils.map(projectForm, ProjectDO.class);
        projectDO.setCreateBy(projectForm.getCreateBy());
        projectDO.setUpdateBy(projectForm.getCreateBy());
        projectMapper.insert(projectDO);
    }

    /**
     * 修改项目
     *
     * @param projectForm
     * @return
     */
    @Override
    public void updateProject(ProjectForm projectForm) throws BizException {
        checkParams(projectForm);
        ProjectDO projectDO = DTOUtils.map(projectForm, ProjectDO.class);
        projectDO.setUpdateTime(new Date());
        projectDO.setUpdateBy(projectForm.getUpdateBy());
        projectMapper.updateByPrimaryKeySelective(projectDO);
    }
    

    /**
     * 分页查询
     *
     * @param myPageInfo
     * @param project
     * @return
     */
    @Override
    public MyPageInfo<ProjectVo> page(MyPageInfo myPageInfo, ProjectVo project) {
        ProjectDO projectDO =DTOUtils.map(project,ProjectDO.class);
        PageHelper.startPage(myPageInfo.getPageNumber(),myPageInfo.getPageSize());
        Page<ProjectDO> page = projectMapper.listPage(projectDO);
        List<ProjectVo> vos = DTOUtils.map(page.getResult(), ProjectVo.class);
        myPageInfo = MyPageInfo.create(page);
        myPageInfo.setRows(vos);
        return myPageInfo;
    }

    /**
     * 获取项目详情
     *
     * @param id
     * @return
     */
    @Override
    public ProjectVo getProject(Long id) {
        ProjectDO projectDO = projectMapper.selectByPrimaryKey(id);
        if (projectDO != null)
            return DTOUtils.map(projectDO,ProjectVo.class);
        throw new BizException("项目不存在");
    }

}
