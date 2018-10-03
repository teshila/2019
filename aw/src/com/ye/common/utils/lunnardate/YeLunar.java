package com.ye.common.utils.lunnardate;

public class YeLunar {
	
	
	
	/** 
	 * 计算清明节的日期(可计算范围: 1700-3100) 
	 *  
	 * @param year 
	 *            需要计算的年份 
	 * @return 清明节在公历中的日期 
	 */  
	public static int qing(int year) {  
	    if (year == 2232) {  
	        return 4;  
	    }  
	    if (year < 1700) {  
	        throw new RuntimeException("1700年以前暂时不支持");  
	    }  
	    if (year >= 3100) {  
	        throw new RuntimeException("3100年以后暂时不支持");  
	    }  
	    double[] coefficient = { 5.15, 5.37, 5.59, 4.82, 5.02, 5.26, 5.48, 4.70, 4.92, 5.135, 5.36, 4.60, 4.81, 5.04,  
	            5.26 };  
	    int mod = year % 100;  
	    return (int) (mod * 0.2422 + coefficient[year / 100 - 17] - mod / 4);  
	}  
	
	public static void main(String[] args) {
		System.out.println(qing(2018));
	}
}
