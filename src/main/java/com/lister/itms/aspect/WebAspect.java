package com.lister.itms.aspect;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.SysLogBiz;
import com.lister.itms.dao.entity.SysLogDO;
import com.lister.itms.dao.entity.UserDO;
import com.lister.itms.enums.SysLogTypeEnums;
import com.lister.itms.exception.BizException;
import com.lister.itms.modal.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Describe : 记录操作日志和页面日志的切面
 * Created by Lister on 2018/7/16 8:23 PM.
 * Version : 1.0
 */
@Slf4j
@Aspect
@Component
public class WebAspect {

    @Autowired
    private SysLogBiz sysLogBiz;

    @Pointcut("execution(* com.lister.itms.controller.*.*(..))")
    private void pointCutMethod() {
    }

    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        HttpSession session = request.getSession();
        UserDO user = (UserDO) session.getAttribute("user");
        Exception ex = null;
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Exception e) {
            ex = e;
        }
        // 记录操作日志
        RequestDesc requestDesc = targetMethod.getAnnotation(RequestDesc.class);
        if (requestDesc != null) {
            if (obj instanceof ResponseResult) {
                ResponseResult result = (ResponseResult) obj;
                if (result != null) {
                    String code = "S";
                    String msg = result.getMsg();
                    SysLogDO sysLog = new SysLogDO();
                    sysLog.setIp(request.getRemoteAddr());
                    sysLog.setLogName(requestDesc.value());
                    sysLog.setLogTime(new Date());
                    sysLog.setParams(formatterParam(request));
                    sysLog.setPath(request.getRequestURI());
                    sysLog.setTimeConsuming((System.currentTimeMillis() - startTime));
                    sysLog.setUser(user.getId());
                    sysLog.setStatus(code);
                    sysLog.setResult(msg);
                    sysLog.setLogType(SysLogTypeEnums.OPER.code);
                    sysLogBiz.addSysLog(sysLog);
                }
            } else if(ex != null) {
                String code;
                if (ex instanceof BizException) {
                    code = "F";
                } else {
                    code = "E";
                }
                SysLogDO sysLog = new SysLogDO();
                sysLog.setIp(request.getRemoteAddr());
                sysLog.setLogName(requestDesc.value());
                sysLog.setParams(formatterParam(request));
                sysLog.setPath(request.getRequestURI());
                sysLog.setTimeConsuming((System.currentTimeMillis() - startTime));
                sysLog.setUser(user.getId());
                sysLog.setStatus(code);
                sysLog.setResult(ex.getMessage());
                sysLog.setLogType(SysLogTypeEnums.OPER.code);
                sysLogBiz.addSysLog(sysLog);
            }
        }

        // 记录页面浏览日志
        ViewDesc viewDesc = targetMethod.getAnnotation(ViewDesc.class);
        if (viewDesc != null) {
            if (!StringUtils.isEmpty(viewDesc.value())) {
                SysLogDO sysLog = new SysLogDO();
                sysLog.setIp(request.getRemoteAddr());
                sysLog.setLogName(viewDesc.value());
                sysLog.setParams(formatterParam(request));
                sysLog.setPath(request.getRequestURI());
                sysLog.setTimeConsuming((System.currentTimeMillis() - startTime));
                sysLog.setUser(user.getId());
                sysLog.setResult("正常返回页面");
                sysLog.setLogType(SysLogTypeEnums.VIEW.code);
                sysLogBiz.addSysLog(sysLog);
            }
            // 控制页面返回，如果是GET请求，则返回首页，由首页路由加载子页面    
            if ("GET".equals(request.getMethod())) {
                return "/index";
            } else {
                return obj;
            }
        }
        if (ex != null) {
            throw ex;
        }
        return obj;

    }

    /**
     * 格式化参数
     *
     * @param request
     * @return
     */
    public String formatterParam(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer().append("[");
        Map map = request.getParameterMap();
        Set keSet = map.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if (ov instanceof String[]) {
                value = (String[]) ov;
            } else {
                value[0] = ov.toString();
            }

            for (int k = 0; k < value.length; k++) {
                sb.append(ok + "=" + value[k] + ",");
            }
        }
        String param = (sb.length() > 1 ? sb.substring(0, sb.length() - 1) : sb.toString()) + "]";
        if (param.length() > 999) {
            return param.substring(0, 999) + "...]";
        }
        return param;
    }
}
