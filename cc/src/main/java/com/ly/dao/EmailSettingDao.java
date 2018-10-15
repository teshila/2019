package com.ly.dao;

import com.ly.pojo.EmailSetting;

public interface EmailSettingDao {

	public EmailSetting getEmailSetting();

	public void saveEmailSetting(EmailSetting h);

	public void delete();
}
