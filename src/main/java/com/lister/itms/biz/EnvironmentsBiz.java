package com.lister.itms.biz;

import com.lister.itms.dao.entity.EnvironmentsDO;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.EnvironmentsForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.EnvironmentsVo;

/**
 * Describe :
 * Created by LY on 2018/6/7 18:18.
 * Update reason :
 * Updated by LY on 2018/6/7 18:18.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public interface EnvironmentsBiz {
    
    /**
     * 
     * @param page
     * @param environmentsVo
     * @return
     */
    MyPageInfo<EnvironmentsDO> page(MyPageInfo page, EnvironmentsVo environmentsVo);

    /**
     * 创建环境
     * @param environmentsForm
     * @throws BizException
     */
    void createEnvironments(EnvironmentsForm environmentsForm) throws BizException;

    /**
     * 编辑环境
     * @param environmentsForm
     * @throws BizException
     */
    void updateEnvironments(EnvironmentsForm environmentsForm) throws BizException;

    /**
     * 
     * @param environmentsForm
     * @throws BizException
     */
    void testEnvironments(EnvironmentsForm environmentsForm) throws Exception;
    
    /**
     *
     * @param environmentsForm
     */
    void deleteEnvironments(EnvironmentsForm environmentsForm);
    
    /**
     * 环境详情
     * @param id
     * @return
     */
    EnvironmentsVo getEnvironment(Long id);
}
