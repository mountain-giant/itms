package com.lister.itms.biz;

import com.lister.itms.dao.entity.SysLogDO;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.SysLogVo;


/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/22 20:41.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/22 20:41.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@SuppressWarnings("all")
public interface SysLogBiz {
   
    /**
     * 添加系统日志
     * @param sysLogVo
     */
    void addSysLog(SysLogDO sysLogVo);

    /**
     * 分页查询登录日志
     * @param pageInfo
     * @param value
     * @param startDate
     * @param endDate
     * @return
     */
    MyPageInfo<SysLogVo> pageOperas(MyPageInfo pageInfo, String value, String startDate, String endDate);
}
