package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.EnvironmentsBiz;
import com.lister.itms.dao.entity.EnvironmentsDO;
import com.lister.itms.dao.mapper.EnvironmentsMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.EnvironmentsForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.utils.PingUtils;
import com.lister.itms.vo.EnvironmentsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Describe :
 * Created by LY on 2018/6/7 18:21.
 * Update reason :
 * Updated by LY on 2018/6/7 18:21.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Service
public class EnvironmentsBizImpl implements EnvironmentsBiz {

    @Autowired
    private EnvironmentsMapper environmentsMapper;

    private static final Logger L = LoggerFactory.getLogger(EnvironmentsBizImpl.class);

    /**
     * 检查环境是否已存在
     * @param environment
     * @param id
     */
    private void checkEnvironmentsExists(String environment, Long id) {
        if (environmentsMapper.checkEnvironmentsExists(environment.trim(), id) > 0){
            throw new BizException("环境已存在");
        }
    }

    /**
     * 
     * @param environmentsForm
     * @throws BizException
     */
    @Override
    public void createEnvironments(EnvironmentsForm environmentsForm) throws BizException {
        checkEnvironmentsExists(environmentsForm.getEnvironment(), environmentsForm.getId());
        EnvironmentsDO environmentsDO = DTOUtils.map(environmentsForm,EnvironmentsDO.class);
        environmentsMapper.insert(environmentsDO);
    }

    /**
     * 
     * @param environmentsForm
     * @throws BizException
     */
    @Override
    public void updateEnvironments(EnvironmentsForm environmentsForm) throws BizException {
        checkEnvironmentsExists(environmentsForm.getEnvironment(), environmentsForm.getId());
        EnvironmentsDO environmentsDO = DTOUtils.map(environmentsForm,EnvironmentsDO.class);
        environmentsMapper.updateByPrimaryKeySelective(environmentsDO);
    }

    /**
     * 
     * @param environmentsForm
     * @throws Exception
     */
    @Override
    public void testEnvironments(EnvironmentsForm environmentsForm) throws Exception {
        boolean success;
        try {
            success = PingUtils.ping(environmentsForm.getIp());
        } catch (Exception e) {
            L.info("环境连接失败",e);
            success = false;
        }
        EnvironmentsDO environmentsDO = DTOUtils.map(environmentsForm,EnvironmentsDO.class);
        if (!success) {
            environmentsDO.setState("离线");
            environmentsDO.setUpdateTime(new Date());
            environmentsMapper.updateByPrimaryKeySelective(environmentsDO);
            throw new BizException("设备连接失败");
        }
        environmentsDO.setState("正常");
        environmentsDO.setUpdateTime(new Date());
        environmentsMapper.updateByPrimaryKeySelective(environmentsDO);
    }

    /**
     * 
     * @param environmentsForm
     */
    @Override
    public void deleteEnvironments(EnvironmentsForm environmentsForm) {
        EnvironmentsDO environmentsDO = DTOUtils.map(environmentsForm,EnvironmentsDO.class);
        environmentsMapper.deleteByPrimaryKey(environmentsDO);
    }

    /**
     * 
     * @param id
     * @return
     */
    @Override
    public EnvironmentsVo getEnvironment(Long id) {
        EnvironmentsDO environmentsDO = environmentsMapper.selectByPrimaryKey(id);
        return DTOUtils.map(environmentsDO,EnvironmentsVo.class);
    }

    /**
     * 
     * @param pageInfo
     * @param environmentsVo
     * @return
     */
    @Override
    public MyPageInfo<EnvironmentsDO> page(MyPageInfo pageInfo, EnvironmentsVo environmentsVo) {
        EnvironmentsDO environmentsDO = DTOUtils.map(environmentsVo,EnvironmentsDO.class);
        
        PageHelper.startPage(pageInfo.getPageNumber(),pageInfo.getPageSize());
        Page<EnvironmentsDO> page = environmentsMapper.listPage(environmentsDO);
        
        List<EnvironmentsVo> vos = DTOUtils.map(page.getResult(),EnvironmentsVo.class);
        pageInfo = MyPageInfo.create(page);
        pageInfo.setRows(vos);
        
        return pageInfo;
    }

    
}
