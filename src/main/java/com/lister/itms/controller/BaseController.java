package com.lister.itms.controller;

import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Describe : 通用处理
 * Created by Lister<728661851@qq.com/> on 16/7/8 08:58.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/7/8 08:58.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
public abstract class BaseController {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public HttpServletRequest getRequest() {
        return request;
    }

    @Resource
    public BaseController setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    @Resource
    public BaseController setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    /**
     * 获取用户信息
     * @return
     * @throws BizException
     */
    protected UserDO getUserInfo() throws BizException {
        UserDO userDO = (UserDO) request.getSession().getAttribute("user");
        if (userDO == null) {
            throw new BizException("认证失败，请重新登录");
        }
        return userDO;
    }

    /**
     * 参数校验
     * @param bindingResult
     */
    protected void hasParamsErrot(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BizException(bindingResult.getFieldError().getDefaultMessage());
        }
    }
}
