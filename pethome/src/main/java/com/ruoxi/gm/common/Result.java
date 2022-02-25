package com.ruoxi.gm.common;

/**
 * @author ruoxi
 * @create 2021-12-20 14:06
 */
public class Result {
    private Integer code = 20000;
    private Boolean success;
    private String msg;
    private Object data;

    /**
     * 构造器初始化success字段为true
     */
    private Result() {
        this.success = true;
    }

    /**
     * @param success 是否成功，默认为true
     */
    public Result(Boolean success) {
        this.success = success;
    }

    /**
     * 返回Result对象并初始化success字段为true
     * @return Result对象
     */
    public static Result me(){
        return new Result();
    }

    /**
     * 返回Result对象并初始化success字段为true,且传入需要携带的数据
     * @param data 需要携带的数据
     * @return Result对象
     */
    public static Result me(Object data){
        return new Result().setData(data);
    }

    /**
     * 返回Result对象并初始化success字段为传入参数
     * @param success 是否成功,默认为true
     * @return Result对象
     */
    public static Result me(Boolean success){
        return new Result(success);
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
