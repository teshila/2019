package com.ye.dao;

import com.ye.pojo.Holiday;


public interface HolidayMapper {

	public Holiday getIsHoliday();
	public void saveHoliday(Holiday h);
	public void delete();
}
