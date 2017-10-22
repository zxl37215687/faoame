package com.lsr.frame.base.condition;

import java.util.List;

import com.lsr.frame.base.utils.StringUtils;

public class DefaultConditionParse implements ConditionParse {

	public String parse(Condition cond){
		if(cond == null)
			return "";
		
		String strRet = parseList(cond.getFixList());//先得到固定条件
		
		//如果有固定条件
		if(cond.getFixList().size()>0 && cond.getGeneralList().size()>0){
			strRet +=" " ; 
		}
		
		strRet = strRet + parseList(cond.getGeneralList()); //再得到普通条件的值
		
		return strRet;
	}
	
	/**
	 * 解析条件对象
	 * @param conditionList 
	 * @return
	 */
	private static String parseList(List conditionList){
		StringBuffer sb = new StringBuffer(50);
		for(int idx = 0 ; idx < conditionList.size(); idx++){
			Object object = conditionList.get(idx);
			
			if(object instanceof String){
				sb.append(object);
				sb.append(" ");
			} else if (object instanceof FieldCondition){
				
				sb.append(parseField((FieldCondition)object));
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 解析单个字段
	 * @param fc 字段条件
	 * @return 组织好的字段条件
	 */
	private  static String parseField(FieldCondition fc ){
		StringBuffer sbWhere = new StringBuffer(20); 
		
		if(ConditionConstant.BETWEEN.equalsIgnoreCase(fc.getOperation())){ //范围之间
			
			String startValue = toValue(fc.getValue(),fc.getDbType());
			String endValue = toValue(fc.getEndValue(),fc.getDbType());
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			sbWhere.append(" (");
			sbWhere.append(startValue);
			sbWhere.append(" ");
			sbWhere.append(ConditionConstant.AND);
			sbWhere.append(" ");
			sbWhere.append(endValue);
			sbWhere.append(")");
			 
		} else if (ConditionConstant.ISNOTNULL.equalsIgnoreCase(fc.getOperation())){ //不为空
			
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			
		} else if (ConditionConstant.ISNULL.equalsIgnoreCase(fc.getOperation())){ //为空
			
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			
		} else if (ConditionConstant.IN.equalsIgnoreCase(fc.getOperation()) ||
				ConditionConstant.NOTIN.equalsIgnoreCase(fc.getOperation())){  //
			
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append(fc.getOperation());
			sbWhere.append(" (");
			for(int idx =0; idx< fc.getValueMany().length; idx++){
				sbWhere.append(toValue(fc.getValueMany()[idx], fc.getDbType()));
				if(idx < (fc.getValueMany().length-1)){
					sbWhere.append(",");
				}
			}
			sbWhere.append(" )");
			
		}	else if (ConditionConstant.LIKE.equalsIgnoreCase(fc.getOperation())){
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append("like");
			sbWhere.append(" ");
			sbWhere.append("'%" +StringUtils.replaceText(fc.getValue())+"%'");
		}	else if (ConditionConstant.BLIKE.equalsIgnoreCase(fc.getOperation())){
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append("like");
			sbWhere.append(" ");
			sbWhere.append("'" +StringUtils.replaceText(fc.getValue())+"%'");
		}	else if (ConditionConstant.ELIKE.equalsIgnoreCase(fc.getOperation())){
			sbWhere.append(fc.getName());
			sbWhere.append(" ");
			sbWhere.append("like");
			sbWhere.append(" ");
			sbWhere.append("'%" +StringUtils.replaceText(fc.getValue())+"'");
		}else {
		
			sbWhere.append(fc.getName());
			sbWhere.append(fc.getOperation());
			sbWhere.append(toValue(fc.getValue(),fc.getDbType()));
		}
		
		return sbWhere.toString();
	}
	
	/**
	 * 组织单个字段的值
	 * @param value 值
	 * @param dbType 数据类型
	 * @return 组织好的字段
	 */
	private static String toValue(String value,String dbType){
		String strRet = value;
		if(DataTypes.STRING.equalsIgnoreCase(dbType)){
			strRet = "'" + StringUtils.replace(strRet,"'","''") + "'";
		} else if (DataTypes.DATE.equalsIgnoreCase(dbType )){
			strRet = ConditionConstant.DATE + "('" + strRet + "')";
		} else if (DataTypes.TIMESTMAP.equalsIgnoreCase(dbType )){
			strRet = ConditionConstant.TIMESTAMP + "('" + strRet + "')";
		} else if (DataTypes.TIME.equalsIgnoreCase(dbType)){
			strRet = ConditionConstant.TIME + "('" + strRet + "')";
		}
		return strRet;
	}

}
