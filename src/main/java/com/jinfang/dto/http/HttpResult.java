package com.jinfang.dto.http;

public class HttpResult {

	private int code = HttpStatus.SC_OK;
	private String msg;
	private Object data;

	public static HttpResult ok() {
		return error(HttpStatus.SC_OK, "执行成功");
	}
	
	public static HttpResult error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器开小差了，请稍后再试");
	}
	
	public static HttpResult error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static HttpResult error(int code, String msg) {
		HttpResult r = new HttpResult();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

	public static HttpResult ok(String msg) {
		HttpResult r = new HttpResult();
		r.setMsg(msg);
		return r;
	}

	public static HttpResult ok(Object data) {
		HttpResult r = new HttpResult();
		r.setData(data);
		return r;
	}

	public static HttpResult data(Object data) {
		HttpResult r = new HttpResult();
		r.setData(data);
		return r;
	}

	public static HttpResult by(boolean result) {
		if(result) {
			return ok();
		}
		return error();
	}

	public boolean isSuccess() {
		return code == HttpStatus.SC_OK;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
