package com.lister.itms.biz;


import com.lister.itms.vo.SysConfigVo;

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
 * Created by Lister<18627416004@163.com/> on 2018/6/6 下午3:16.
 * Version : 1.0
 */
public interface SysConfigBiz {

    /**
     * 根据TYPE获取
     * @param type
     * @return
     */
    List<SysConfigVo> getByType(String type);

    /**
     * 获取流程角色
     * @param id
     * @return
     */
    List<SysConfigVo> getProcessRoleByUserId(Long id);
}
