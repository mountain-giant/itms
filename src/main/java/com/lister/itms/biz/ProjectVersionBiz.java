package com.lister.itms.biz;


import com.lister.itms.form.ProjectVersionForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ProjectVersionVo;


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
 * Describe : 版本管理
 * Created by Lister<18627416004@163.com/> on 2018/6/8 下午5:19.
 * Version : 1.0
 */
public interface ProjectVersionBiz {

    /**
     * 版本列表
     * @param versionVo
     * @return
     */
    MyPageInfo<ProjectVersionVo> page(MyPageInfo page, ProjectVersionVo versionVo);

    /**
     * 编辑
     * @param projectVersionForm
     */
    void update(ProjectVersionForm projectVersionForm);

    /**
     * 获取版本详情
     * @param id
     * @return
     */
    ProjectVersionVo getVersionById(Long id);

    /**
     * 创建版本
     * @param projectVersionVo
     */
    void create(ProjectVersionForm projectVersionVo);

    /**
     * 删除版本
     * @param projectVersionForm
     */
    void delete(ProjectVersionForm projectVersionForm);
}
