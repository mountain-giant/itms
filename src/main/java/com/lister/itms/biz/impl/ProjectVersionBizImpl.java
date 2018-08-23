package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.ProjectVersionBiz;
import com.lister.itms.dao.entity.ProjectVersionDO;
import com.lister.itms.dao.mapper.ProjectVersionMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.ProjectVersionForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.ProjectVersionVo;
import com.lister.itms.vo.ProjectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━by:LISTER
 * Describe :
 * Created by Lister<18627416004@163.com/> on 2018/6/8 下午5:20.
 * Version : 1.0
 */
@Service
public class ProjectVersionBizImpl implements ProjectVersionBiz {
    
    private static final Logger L = LoggerFactory.getLogger(ProjectVersionBizImpl.class);
    
    @Autowired
    private ProjectVersionMapper projectVersionMapper;

    /**
     * 版本列表
     *
     * @param versionVo
     * @return
     */
    @Override
    public MyPageInfo<ProjectVersionVo> page(MyPageInfo pageInfo, ProjectVersionVo versionVo) {
        ProjectVersionDO projectVersionDO = DTOUtils.map(versionVo,ProjectVersionDO.class);
        PageHelper.startPage(pageInfo.getPageNumber(),pageInfo.getPageSize());
        Page<ProjectVersionDO> page = projectVersionMapper.listPage(projectVersionDO);
        List<ProjectVersionVo> vos = DTOUtils.map(page.getResult(), ProjectVersionVo.class);
        pageInfo = MyPageInfo.create(page);
        pageInfo.setRows(vos);
        return pageInfo;
    }

    /**
     * 编辑
     *
     * @param projectVersionForm
     */
    @Override
    public void update(ProjectVersionForm projectVersionForm) {
        projectVersionForm.setProjectId(null);
        ProjectVersionDO projectVersionDO = DTOUtils.map(projectVersionForm,ProjectVersionDO.class);
        checkIsExist(projectVersionDO);
        projectVersionDO.setUpdateTime(new Date());
        projectVersionMapper.updateByPrimaryKeySelective(projectVersionDO);
    }

    /**
     * 获取版本详情
     *
     * @param id
     * @return
     */
    @Override
    public ProjectVersionVo getVersionById(Long id) {
        ProjectVersionDO projectVersionDO = projectVersionMapper.selectByPrimaryKey(id);
        if (projectVersionDO != null)
            return DTOUtils.map(projectVersionDO,ProjectVersionVo.class);
        throw new BizException("无此版本");
    }

    /**
     * 创建版本
     *
     * @param projectVersionForm
     */
    @Override
    public void create(ProjectVersionForm projectVersionForm) {
        ProjectVersionDO projectVersionDO = DTOUtils.map(projectVersionForm,ProjectVersionDO.class);
        checkIsExist(projectVersionDO);
        projectVersionDO.setCreateBy(projectVersionForm.getCreateBy());
        projectVersionDO.setUpdateBy(projectVersionForm.getCreateBy());
        projectVersionMapper.insert(projectVersionDO);
    }

    private void checkIsExist(ProjectVersionDO projectVersionDO) {
        if (projectVersionMapper.checkExists(projectVersionDO) > 0){
            throw new BizException("版本名称已存在");
        }
    }

    /**
     * 删除版本
     *
     * @param projectVersionForm
     */
    @Override
    public void delete(ProjectVersionForm projectVersionForm) {
        ProjectVersionDO projectVersionDO = DTOUtils.map(projectVersionForm,ProjectVersionDO.class);
        projectVersionMapper.deleteByPrimaryKey(projectVersionDO);
    }
}
