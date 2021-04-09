package com.lai.demo.commons.dto;

/**
 * 请求响应数据
 * @author Administrator
 *
 * @param <T>
 */
public class resResult<T> {
	private String code;
	private String msg;
	private T data;
	
	/**
	 * 成功返回
	 * @param succMsg
	 * @param succData
	 * @return
	 */
	public static <T> resResult<T> sucess(String succMsg,T succData){
		resResult<T> result=new resResult<T>();
		result.setCode("SUCESS");
		result.setMsg(succMsg);
		result.setData(succData);
		return result;
	}
	
	/**
	 * 失败返回
	 * @param succMsg
	 * @return
	 */
	public static <T> resResult<T> error(String succMsg){
		resResult<T> result=new resResult<T>();
		result.setCode("ERROR");
		result.setMsg(succMsg);
		return result;
	}

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

	public void setData(T data) {
		this.data = data;
	}
	
	
}
