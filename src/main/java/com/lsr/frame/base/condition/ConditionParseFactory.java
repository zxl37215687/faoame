package com.lsr.frame.base.condition;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.utils.PropertyUtils;

public class ConditionParseFactory {
	private static final Log log = LogFactory.getLog(ConditionParseFactory.class);
	
	public static ConditionParse createParse(){
		ConditionParse parseImpl = null;
		String className = PropertyUtils.getProperty("condition.parse");
		if(StringUtils.isBlank(className)){
			return new DefaultConditionParse();
		}
		
		try{
			parseImpl = (ConditionParse) Class.forName(className).newInstance();
		}catch(InstantiationException e){
			log.error("createParse",e);
		}catch(IllegalAccessException e){
			log.error("createParse",e);
		}catch(ClassNotFoundException e){
			log.error("createParse",e);
		}
			
		
		return parseImpl;
	}
}
