package com.luqili.tools;

import org.springframework.util.StringUtils;

public class CreateVipCode {
	
	public static String switchStr(int len,String idNo){
		if(len == 5){
			return idNo;
		}
		if(len == 4){
			
			return idNo+"0";
		}
		
		if(len == 3){
			return idNo+"00";
		}
		
		if(len == 2){
			return idNo+"000";
		}
		
		if(len == 1){
			return idNo+"0000";
		}
		return "";
	}
	
	public static Long getOrgCode(String orgcode){
		
		if(StringUtils.isEmpty(orgcode)){
			
			return Long.parseLong(RandomCodeCreator.createRandomNum(5));
		}
		
		int len = orgcode.length();
		
		if(len == 5){
			return Long.parseLong(orgcode);
		}
		if(len == 4){
			
			return Long.parseLong(orgcode+"0");
		}
		
		if(len == 3){
			return Long.parseLong(orgcode+"00");
		}
		
		if(len == 2){
			return Long.parseLong(orgcode+"000");
		}
		
		if(len == 1){
			return Long.parseLong(orgcode+"0000");
		}
		
		return 0l;
	}
}
