package com.lister.itms.enums;

/**
 * Describe : 配置程序中的一些常量
 * Created by Lister<728661851@qq.com/> on 16/11/10 15:46.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/10 15:46.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@SuppressWarnings("all")
public enum ResponseEnum {
    SUCCESS("0000","操作成功"),
    FAILED("9999","操作失败，请稍后再试"),
    ERROR("1111","程序出现错误"),
    EXCEPTION("5555","系统异常，请联系管理员"),

    PARAM_IS_NULL("9998", "参数为空"),

    PROJECT_ID_IS_NOT_EXISTS("12002", "项目ID不存在"),

    TEST_CASE_UPDATE_FORBIDDEN("13001", "测试案例已禁止修改"),
    TEST_CASE_SUBMIT_NEED_ROLE("13002", "测试案例提交需要分配角色"),


    USE_CASE_ALREADY_PASS("14001", "执行案例已通过，不允许再次修改"),

    ENVIRONMENT_IS_RELATED("15001", "环境变量已被引用"),

//    QUESTION_NOT_EXISTS("16001", "该问题不存在");

    DIRECTORY_HAS_FILES("17001", "该文件夹下存在文件，不能直接删除"),
    FILE_EXISTS_IN_KNOWLEDGE("17002", "该文件在当前文件夹下已存在"),
    FILENAME_EXISTS_IN_CURRENT_DIR("17003", "该文件名称存在重复"),
    ;

    private String code;
    private String msg;

    private ResponseEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }
}
