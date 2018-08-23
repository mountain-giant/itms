package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.SysLogBiz;
import com.lister.itms.dao.entity.SysLogDO;
import com.lister.itms.dao.mapper.SysLogMapper;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.SysLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/22 20:47.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/22 20:47.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Service
public class SysLogBizImpl implements SysLogBiz {
    
    @Autowired
    private SysLogMapper sysLogMapper;

    @Async
    @Override
    public void addSysLog(SysLogDO sysLog) {
        sysLogMapper.insert(sysLog);
    }

    /**
     * 分页查询登录日志
     * @param pageInfo
     * @param value
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public MyPageInfo<SysLogVo> pageOperas(MyPageInfo pageInfo, String value, String startDate, String endDate) {
        if (!StringUtils.isEmpty(startDate)) {
            startDate += " 00:00:00";
        }
        if (!StringUtils.isEmpty(endDate)) {
            endDate += " 23:59:59";
        }
        PageHelper.startPage(pageInfo.getPageNumber(),pageInfo.getPageSize());
        Page<SysLogDO> page = sysLogMapper.listPage(value,startDate,endDate);
        List<SysLogVo> vos = DTOUtils.map(page.getResult(), SysLogVo.class);
        pageInfo = MyPageInfo.create(page);
        pageInfo.setRows(vos);
        return pageInfo;
    }
}
