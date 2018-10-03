package com.ye.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegluarUtil {
	
	
	//判断字符串中是否包含字母
	public static boolean judgeContainsStr(String cardNum) {
		String regex = ".*[a-zA-Z]+.*";
		Matcher m = Pattern.compile(regex).matcher(cardNum);
		return m.matches();
	}
}
