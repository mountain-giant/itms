package com.lister.itms.modal;

import com.lister.itms.enums.ResponseEnum;

/**
 * Describe :
 * Created by Lister<18627416004@163.com/> on 2017/6/9 15:54.
 * Update reason :
 */
public class ResponseResult<T> {
    private String code = ResponseEnum.SUCCESS.getCode();
    private String msg = ResponseEnum.SUCCESS.getMsg();
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public ResponseResult setData(T data) {
        this.data = data;
        return this;
    }

    public void setResult(ResponseEnum contextEnum) {
        this.code = contextEnum.getCode();
        this.msg = contextEnum.getMsg();
    }

    public void setResult(ResponseEnum contextEnum, T data) {
        this.code = contextEnum.getCode();
        this.msg = contextEnum.getMsg();
        this.data = data;
    }

    public ResponseResult() {
        this.code = ResponseEnum.SUCCESS.getCode();
        this.msg = ResponseEnum.SUCCESS.getMsg();
    }

    public ResponseResult(T data) {
        this.code = ResponseEnum.SUCCESS.getCode();
        this.msg = ResponseEnum.SUCCESS.getMsg();
        this.data = data;
    }

    public ResponseResult(ResponseEnum contextEnum, T data) {
        setResult(contextEnum);
        this.data = data;
    }
    
    public ResponseResult(String state,String tip) {
        this.code = state;
        this.msg = tip;
    }


    public static ResponseResult createResult(String state,String tip) {
        return new ResponseResult(state,tip);
    }

    public static ResponseResult createResult() {
        return new ResponseResult();
    }

    public boolean isSuccess() {
        return code.equals(ResponseEnum.SUCCESS) ? true : false;
    }
}
