package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.ProjectModuleBiz;
import com.lister.itms.dao.entity.ProjectDO;
import com.lister.itms.dao.entity.ProjectModuleDO;
import com.lister.itms.dao.mapper.ProjectModuleMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.ProjectModuleForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.ProjectModuleVo;
import com.lister.itms.vo.ProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Describe :
 * Created by LY on 2018/6/8 17:22.
 * Update reason :
 * Updated by LY on 2018/6/8 17:22.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Service
public class ProjectModuleBizImpl implements ProjectModuleBiz {

    @Autowired
    private ProjectModuleMapper projectModuleMapper;

    private void checkProjectModuleExists(String moduleName, Long id) {
        if (projectModuleMapper.checkProjectModuleExists(moduleName.trim(), id) > 0) {
            throw new BizException("模块已存在");
        }
    }

    /**
     * @param pageInfo
     * @param projectModuleVo
     * @return
     */
    @Override
    public MyPageInfo<ProjectModuleVo> page(MyPageInfo pageInfo, ProjectModuleVo projectModuleVo) {
        ProjectModuleDO projectModuleDO = DTOUtils.map(projectModuleVo, ProjectModuleDO.class);
        PageHelper.startPage(pageInfo.getPageNumber(), pageInfo.getPageSize());
        Page<ProjectModuleDO> page = projectModuleMapper.listPage(projectModuleDO);
        List<ProjectModuleVo> vos = DTOUtils.map(page.getResult(), ProjectModuleVo.class);
        pageInfo = MyPageInfo.create(page);
        pageInfo.setRows(vos);
        return pageInfo;
    }

    @Override
    public void createProjectModule(ProjectModuleForm projectModuleForm) throws BizException {
        checkProjectModuleExists(projectModuleForm.getModuleName(), null);
        ProjectModuleDO projectModuleDO = DTOUtils.map(projectModuleForm, ProjectModuleDO.class);
        projectModuleMapper.insert(projectModuleDO);
    }

    @Override
    public void updateProjectModule(ProjectModuleForm projectModuleForm) throws BizException {
        projectModuleForm.setProjectId(null);
        ProjectModuleDO projectModuleDO = DTOUtils.map(projectModuleForm, ProjectModuleDO.class);
        checkProjectModuleExists(projectModuleDO.getModuleName(), projectModuleDO.getId());
        projectModuleMapper.updateByPrimaryKeySelective(projectModuleDO);
    }

    @Override
    public void deleteProjectModule(ProjectModuleForm projectModuleForm) {
        ProjectModuleDO projectModuleDO = DTOUtils.map(projectModuleForm, ProjectModuleDO.class);
        projectModuleMapper.deleteByPrimaryKey(projectModuleDO);
    }

    @Override
    public ProjectModuleVo getProjectModule(Long id) {
        ProjectModuleDO projectModuleDO = projectModuleMapper.selectByPrimaryKey(id);
        ProjectModuleVo projectModuleVo = DTOUtils.map(projectModuleDO, ProjectModuleVo.class);
        return projectModuleVo;
    }
}
