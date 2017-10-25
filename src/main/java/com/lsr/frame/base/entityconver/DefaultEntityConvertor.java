package com.lsr.frame.base.entityconver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.utils.BeanUtils;
import com.lsr.frame.ws.utils.DateUtils;
import com.lsr.test.entityConver.UserPO;
import com.lsr.test.entityConver.UserVO;
import com.sun.xml.txw2.IllegalAnnotationException;

/**
 * POVO默认转换器
 * 
 * @author kuaihuolin
 * 
 */
public class DefaultEntityConvertor implements EntityConver {
	private static final Log log = LogFactory
			.getLog(DefaultEntityConvertor.class);

	@Override
	public <T> T copyPoToVo(BasePO po, Class<T> voClass) {
		if(!VOInterface.class.isAssignableFrom(voClass)){
			log.error(voClass.getName()+" must be type of "+VOInterface.class.getName());
			return null;
		}
		
		if (!voClass.isAnnotationPresent(BindEntity.class)) {
			throw new IllegalAnnotationException(voClass.getName()
					+ " no annotation BindEntity!");
		}

		VOInterface vo = (VOInterface) this.getInstance(voClass.getName());

		try {
			for (Field field : voClass.getDeclaredFields()) {
				String voPropertyName = field.getName();
				if (field.isAnnotationPresent(BindFileldName.class)) {
					BindFileldName bindFileldName = field
							.getAnnotation(BindFileldName.class);
						String poPropertyName = bindFileldName.value();
						Object poValue = BeanUtils.getProperty(po, poPropertyName);
						Object voValue = toVOValue(poValue,
								bindFileldName.dataType());
						BeanUtils.setProperty(vo, voPropertyName, voValue);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return (T) vo;
	}

	@Override
	public BasePO copyVoToPo(VOInterface vo) {
		Class<?> voClass = vo.getClass();
		if (!voClass.isAnnotationPresent(BindEntity.class)) {
			throw new IllegalAnnotationException(voClass.getName()
					+ " no annotation BindEntity!");
		}

		BindEntity bindEntity = voClass.getAnnotation(BindEntity.class);
		Class<?> poClass = bindEntity.value();
		BasePO po = (BasePO) getInstance(poClass.getName());

		try {
			for (Field field : voClass.getDeclaredFields()) {
				String voPropertyName = field.getName();
				if (field.isAnnotationPresent(BindFileldName.class)) {
					BindFileldName bindFileldName = field
							.getAnnotation(BindFileldName.class);
						Object voValue = BeanUtils.getProperty(vo, voPropertyName);
						String poPropertyName = bindFileldName.value();
						int idx = poPropertyName.indexOf('.');
						if (idx != -1) {
							if (voValue != null && !"".equals(voValue)) {
								Object obj = po;
								do {
									String propertyName = upperFirst(poPropertyName
											.substring(0, idx));
									Class objClass = obj.getClass();
									Method propertyGetter = objClass.getMethod(
											"get" + propertyName, null);
									Object property;
									property = propertyGetter.invoke(obj, null);
									if (property == null) {
										Class propertyClass = propertyGetter
												.getReturnType();
										property = propertyClass.newInstance();
										Method propertySetter = objClass.getMethod(
												"set" + propertyName,
												new Class[] { propertyClass });
										propertySetter.invoke(obj,
												new Object[] { property });
									}
									obj = property;
									poPropertyName = poPropertyName
											.substring(idx + 1);
									idx = poPropertyName.indexOf('.');
								} while (idx != -1);
								Object poValue = toPoValue(voValue,
										bindFileldName.dataType());
								BeanUtils.setSimpleProperty(obj, poPropertyName,
										poValue);
							}
						} else {
							Object poValue = toPoValue(voValue,
									bindFileldName.dataType());
							BeanUtils
									.setSimpleProperty(po, poPropertyName, poValue);
						}
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return po;
	}

	@Override
	public List<VOInterface> copyPoListToVO(List<BasePO> poList,
			Class<VOInterface> voClass) {
		if (poList == null || poList.isEmpty()) {
			return null;
		}

		List<VOInterface> voList = new ArrayList<VOInterface>();
		for (BasePO po : poList) {
			VOInterface _vo = this.copyPoToVo(po, voClass);
			voList.add(_vo);
		}
		return voList;
	}

	@Override
	public List<BasePO> copyVoListToPO(List<VOInterface> voList) {
		if (voList == null) {
			return null;
		}

		List<BasePO> poList = new ArrayList<BasePO>();

		for (VOInterface vo : voList) {
			poList.add(this.copyVoToPo(vo));
		}

		return poList;
	}

	public Object getInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	private static Object toVOValue(Object value, DataType dataType) {
		if (value == null) {
			if (DataType.BYTES.equals(dataType))
				return new byte[0];
			else if (DataType.BLOB.equals(dataType))
				return null;
			else
				return "";
		}

		if (DataType.TIMESTAMP.equals(dataType)) {
			return ((Timestamp) value).toString().substring(0, 19);
		} else if (DataType.CLOB.equals(dataType)) {
			try {
				Clob clob = (Clob) value;
				int len = (int) clob.length();
				if (len > 0)
					return clob.getSubString(1, len);
				else
					return "";
			} catch (SQLException e) {
				return "";
			}

		} else if (DataType.BLOB.equals(dataType)) {

			try {
				Blob blob = (Blob) value;
				int len = (int) blob.length();
				if (len > 0)
					return blob.getBytes(1, len);
				else
					return new byte[0];
			} catch (SQLException e) {
				return new byte[0];
			}
		} else if (DataType.BYTES.equals(dataType)) {
			return value;
		} else if (DataType.DATE.equals(dataType)) {
			if (value instanceof java.util.Date) {
				return DateUtils.DateTOStr((java.util.Date) value).substring(0,
						10);
			}
			if (value instanceof java.sql.Timestamp) {
				return ((java.sql.Timestamp) value).toString().substring(0, 10);
			}
			if (value instanceof java.sql.Timestamp) {
				return ((java.sql.Timestamp) value).toString().substring(0, 10);
			}
			return null;
		} else if (DataType.COLLECTION.equals(dataType)) {
			return value;
		} else {

			return value.toString();

		}

	}

	private static Object toPoValue(Object value, DataType dataType) {
		if (DataType.INT.equals(dataType)) {
			return Integer.valueOf(value == null ? "0" : value.toString());
		} else if (DataType.LONG.equals(dataType)) {
			return Long.valueOf(value == null ? "0" : value.toString());
		} else if (DataType.SHORT.equals(dataType)) {
			return Short.valueOf(value == null ? "0" : value.toString());
		} else if (DataType.BOOLEAN.equals(dataType)) {
			return Boolean.valueOf("true".equals(value));
		} else if (DataType.FLOAT.equals(dataType)) {
			return Float.valueOf(value == null ? "0" : value.toString());
		} else if (DataType.DOUBLE.equals(dataType)) {
			return Double.valueOf(value == null ? "0" : value.toString());
		} else if (DataType.DECIMAL.equals(dataType)) {
			return Double.valueOf(value == null ? "0" : value.toString());
		} else if (DataType.BIGDECIMAL.equals(dataType)) {
			if (value == null) {
				return null;
			}
			return new BigDecimal((value == null ? "0" : value.toString()));
		} else if (DataType.DATE.equals(dataType)) {
			String str = (value == null ? "" : value.toString());
			return Date.valueOf(str);
		} else if (DataType.TIME.equals(dataType)) {
			String str = (value == null ? "" : value.toString());
			if (str.length() == 5) {
				str += ":00";
			}
			return Time.valueOf(str);
		} else if (DataType.TIMESTAMP.equals(dataType)) {
			if (value == null) {
				return null;
			}
			String str = value.toString();
			if (str.length() == 16) {
				str += ":00";
			}
			return Timestamp.valueOf(str);
		} else if (DataType.CLOB.equals(dataType)) {
			// return Hibernate.createClob(String.valueOf(value));
		} else if (DataType.BLOB.equals(dataType)) {
			// return Hibernate.createBlob((byte[])value);
		} else if (DataType.BYTES.equals(dataType)) {
			return (byte[]) value;
		} else if (DataType.COLLECTION.equals(dataType)) {
			return value;
		} else {
			return value;
		}
		return null;
	}

	private static String upperFirst(String str) {
		if (str == null || str.length() == 0
				|| Character.isUpperCase(str.charAt(0))) {
			return str;
		}
		char chars[] = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);

	}

	public static void main(String[] args) {
//		UserPO po = new UserPO("11","22","33");
		DefaultEntityConvertor c = new DefaultEntityConvertor();
//		UserVO vo = c.copyPoToVo(po, UserVO.class);
//		System.out.println(vo.getId());
//		System.out.println(vo.getAccount());
//		System.out.println(vo.getPassword());
		
		UserVO vo1 = new UserVO("a","b","c");
		UserPO po1 = (UserPO) c.copyVoToPo(vo1);
		System.out.println(po1.getId());
		System.out.println(po1.getAccount());
		System.out.println(po1.getPassword());
		
	}
}
