package com.ye.dao;

import java.util.List;

import com.ye.pojo.IPAddressRecord;

public interface IPRecordeMapper {

	public List<IPAddressRecord> getIpListByIP(String ip);
	
	public void save(IPAddressRecord ip);
}
