package com.ly.pojo;

public class EmailSetting {

	private String fromer;
	private String pwd;
	private String host;
	private Integer port;
	private String isAuth;
	private Integer count;

	public String getFromer() {
		return fromer;
	}

	public void setFromer(String fromer) {
		this.fromer = fromer;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

}
