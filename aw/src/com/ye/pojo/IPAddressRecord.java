package com.ye.pojo;

import java.util.Date;

public class IPAddressRecord {

	private String id;
	private String ipadd;
	private String ipregion;
	private String osname;
	private String osversion;
	private Date addDate;
	private String browserVersion;
	
	
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getOsname() {
		return osname;
	}
	public void setOsname(String osname) {
		this.osname = osname;
	}
	public String getOsversion() {
		return osversion;
	}
	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIpadd() {
		return ipadd;
	}
	public void setIpadd(String ipadd) {
		this.ipadd = ipadd;
	}
	public String getIpregion() {
		return ipregion;
	}
	public void setIpregion(String ipregion) {
		this.ipregion = ipregion;
	}
	
	
	
}
