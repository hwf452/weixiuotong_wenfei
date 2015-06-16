package com.example.httpdemo.model;

import java.io.Serializable;

public class HttpStatus implements Serializable {

	private static final long serialVersionUID = -8540968628351485191L;

	private String result;
	private String code;
	private String module;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Override
	public String toString() {
		return "HttpStatus [result=" + result + ", code=" + code + ", module=" + module + "]";
	}
}
