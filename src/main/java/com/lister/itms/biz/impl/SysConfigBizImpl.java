package com.lister.itms.biz.impl;

import com.lister.itms.biz.SysConfigBiz;
import com.lister.itms.dao.entity.SysConfigDO;
import com.lister.itms.dao.mapper.SysConfigMapper;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.SysConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * Created by Lister<18627416004@163.com/> on 2018/6/6 下午3:17.
 * Version : 1.0
 */
@Service
public class SysConfigBizImpl implements SysConfigBiz {
    
    @Autowired
    private SysConfigMapper sysConfigMapper;
    
    /**
     * 根据TYPE获取
     *
     * @param type
     * @return
     */
    @Override
    public List<SysConfigVo> getByType(String type) {
        List<SysConfigDO> sysConfigDOS = sysConfigMapper.getByType(type);
        List<SysConfigVo> sysConfigVos = DTOUtils.map(sysConfigDOS,SysConfigVo.class);
        return sysConfigVos;
    }

    /**
     * 获取流程角色
     *
     * @param id
     * @return
     */
    @Override
    public List<SysConfigVo> getProcessRoleByUserId(Long id) {
        List<SysConfigDO> sysConfigDOS = sysConfigMapper.getProcessRoleByUserId(id);
        List<SysConfigVo> sysConfigVos = DTOUtils.map(sysConfigDOS,SysConfigVo.class);
        return sysConfigVos;
    }
}
