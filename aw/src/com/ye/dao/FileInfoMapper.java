package com.ye.dao;

import java.util.List;

import com.ye.pojo.FileInfo;

public interface FileInfoMapper {
	
	
	public List getFile();
	public void save(FileInfo info);
	public void delete(FileInfo fileInfo);
}
