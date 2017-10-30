package com.lsr.frame.base.entityconver;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.lsr.frame.ws.utils.BeanUtils;
import com.lsr.frame.ws.utils.DateUtils;
import com.lsr.test.entityConver.UserPO;
import com.lsr.test.entityConver.UserVO;
import com.sun.xml.txw2.IllegalAnnotationException;

/**
 * BeanUtils
 * 
 * @author kuaihuolin
 * 
 */
public class BeanCopier {
	private static final Log log = LogFactory.getLog(BeanCopier.class);
	private static final Map<String, ConstructorAccess> constructorAccessCache = new ConcurrentHashMap<String, ConstructorAccess>();
	private static final Map<String, MethodAccess> methodAccessCache = new ConcurrentHashMap<String, MethodAccess>();
	private static final Map<String, FieldAccess> fieldAccessCache = new ConcurrentHashMap<String, FieldAccess>();
	private static final Map<String, List<EntityConverInfo>> entityMetadataCache = new ConcurrentHashMap<String, List<EntityConverInfo>>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static <T,V> List<T> copyPropertiesOfList(List<V> sourceList,
			Class<T> targetClass) {
		
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}

		Class<?> poClass = sourceList.iterator().next().getClass();

		if (!VOInterface.class.isAssignableFrom(targetClass)) {
			log.error(targetClass.getName() + " must be type of "
					+ VOInterface.class.getName());
			return null;
		}

		if (!targetClass.isAnnotationPresent(BindEntity.class)) {
			throw new IllegalAnnotationException(targetClass.getName()
					+ " no annotation BindEntity!");
		}

		MethodAccess voMethodAccess = getMethodAccess(targetClass);
		if (voMethodAccess == null) {
			log.error(String.format("Get MethodAccess of %s failed: %s",
					targetClass, " methodAccess is null,please check!"));
			return null;
		}
		MethodAccess poMethodAccess = getMethodAccess(poClass);
		if (poMethodAccess == null) {
			log.error(String.format("Get MethodAccess of %s failed: %s",
					poClass,
					" methodAccess is null,please check!"));
			return null;
		}
		FieldAccess voFiledAccess = getFieldAccess(targetClass);
		if (voFiledAccess == null) {
			log.error(String.format("Get FieldAccess of %s failed: %s",
					targetClass, " fieldAccess is null,please check!"));
			return null;
		}
		FieldAccess poFieldAccess = getFieldAccess(poClass);
		if (poFieldAccess == null) {
			log.error(String
					.format("Get FieldAccess of %s failed: %s",
							poClass,
							" fieldAccess is null,please check!"));
			return null;
		}
		
		List<EntityConverInfo> entityConverInfoList = getEntityConverInfoList(targetClass,voMethodAccess,poMethodAccess);
		if(entityConverInfoList == null){
			return null;
		}
		
		ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
		List<T> resultList = new ArrayList<T>(sourceList.size());
		for (Object po : sourceList) {
			T vo = null;
			try {
				vo = constructorAccess.newInstance();
				copyProperties(po,vo,voMethodAccess,poMethodAccess,entityConverInfoList);
				resultList.add(vo);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return resultList;
	}
	
	public static <T> T copyProperties(Object source, Class<T> targetClass) {
		T t = null;
		try {
			if (!VOInterface.class.isAssignableFrom(targetClass)) {
				log.error(targetClass.getName() + " must be type of "
						+ VOInterface.class.getName());
				return null;
			}

			if (!targetClass.isAnnotationPresent(BindEntity.class)) {
				log.error(targetClass.getName() + " no annotation BindEntity!");
				return null;
			}
			
			MethodAccess voMethodAccess = getMethodAccess(targetClass);
			if (voMethodAccess == null) {
				log.error(String.format("Get MethodAccess of %s failed: %s",
						targetClass, " methodAccess is null,please check!"));
				return null;
			}
			MethodAccess poMethodAccess = getMethodAccess(source.getClass());
			if (poMethodAccess == null) {
				log.error(String.format("Get MethodAccess of %s failed: %s",
						source.getClass(),
						" methodAccess is null,please check!"));
				return null;
			}
			FieldAccess voFiledAccess = getFieldAccess(targetClass);
			if (voFiledAccess == null) {
				log.error(String.format("Get FieldAccess of %s failed: %s",
						targetClass, " fieldAccess is null,please check!"));
				return null;
			}
			FieldAccess poFieldAccess = getFieldAccess(source.getClass());
			if (poFieldAccess == null) {
				log.error(String
						.format("Get FieldAccess of %s failed: %s",
								source.getClass(),
								" fieldAccess is null,please check!"));
				return null;
			}
			
			List<EntityConverInfo> entityConverInfoList = getEntityConverInfoList(targetClass,voMethodAccess,poMethodAccess);
			if(entityConverInfoList == null){
				return t;
			}
			ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
			t = constructorAccess.newInstance();
			copyProperties(source,t,voMethodAccess,poMethodAccess,entityConverInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	
	private static <T> T copyProperties(Object source, T t,
			MethodAccess voMethodAccess,MethodAccess poMethodAccess,
			List<EntityConverInfo> entityConverInfoList) {
		try {
			for (EntityConverInfo entityInfo : entityConverInfoList) {
				Object value = poMethodAccess.invoke(source,entityInfo.getPoPropertyGetMethodIndex());
				DataType dataType = entityInfo.getPoPropertyType();
				Object v = toVoValue(value, dataType);
				voMethodAccess.invoke(t,entityInfo.getVoPropertySetMethodIndex(), v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private static List<EntityConverInfo> getEntityConverInfoList(Class<?> targetClass,MethodAccess voMethodAccess,MethodAccess poMethodAccess){
		List<EntityConverInfo> entityConverInfoList = entityMetadataCache.get(targetClass.toString());
		if (entityConverInfoList == null) {
			entityConverInfoList = new ArrayList<EntityConverInfo>();
			entityMetadataCache.put(targetClass.toString(),
					entityConverInfoList);
			for (Field field : targetClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(BindFieldName.class)) {
					BindFieldName bindFieldName = field.getAnnotation(BindFieldName.class);

					EntityConverInfo meta = new EntityConverInfo();

					String poPropertyName = bindFieldName.value();
					meta.setPoPropertyName(poPropertyName);
					meta.setPoPropertyGetMethodName(BeanUtils
							.getGetterMethodName(poPropertyName,
									bindFieldName.dataType().toString()));
					meta.setPoPropertyGetMethodIndex(poMethodAccess
							.getIndex(meta.getPoPropertyGetMethodName()));
					meta.setPoPropertySetMethodName(BeanUtils
							.getSetterMethodName(poPropertyName));
					meta.setPoPropertySetMethodIndex(poMethodAccess
							.getIndex(meta.getPoPropertySetMethodName()));
					meta.setPoPropertyType(bindFieldName.dataType());

					String voPropertyName = field.getName();
					meta.setVoPropertyName(voPropertyName);
					meta.setVoPropertyGetMethodName(BeanUtils
							.getGetterMethodName(voPropertyName,
									DataType.STRING.toString()));
					meta.setVoPropertyGetMethodIndex(voMethodAccess
							.getIndex(meta.getVoPropertyGetMethodName()));
					meta.setVoPropertySetMethodName(BeanUtils
							.getSetterMethodName(voPropertyName));
					meta.setVoPropertySetMethodIndex(voMethodAccess
							.getIndex(meta.getVoPropertySetMethodName()));
					meta.setVoPropertyType(DataType.STRING);

					entityConverInfoList.add(meta);

				}
			}
		}
		return entityConverInfoList;
	}
	
	
	private static Object toVoValue(Object value, DataType dataType) {
		Object v = null;
		if(value == null){
			return v;
		}
		switch (dataType) {
			case TIME:
				;
			case DATE:
				v = DateUtils.DateTOStr((java.util.Date) value).substring(
						0, 10);
				break;
			case TIMESTAMP:
				v = ((Timestamp) value).toString().substring(0, 19);
				break;
			case BLOB:
				if (value == null)
					return v;
				try {
					Blob blob = (Blob) value;
					int len = (int) blob.length();
					if (len > 0) {
						v = blob.getBytes((long) 1, len);
					} else {
						v = new byte[0];
					}
				} catch (SQLException e) {
					v = new byte[0];
				}
				;
				break;
			case BYTES:
				if (value == null) {
					v = new byte[0];
				} else {
					v = value;
				}
				;
				break;
			case COLLECTION:
				v = value;
				break;
			case CLOB:
				if (value == null) {
					v = "";
				} else {
					try {
						Clob clob = (Clob) value;
						int len = (int) clob.length();
						if (len > 0)
							v = clob.getSubString(1, len);
						else
							v = "";
					} catch (SQLException e) {
						v = "";
					}
				}
				;
				break;
			default:
				v = value.toString();
				break;
			}
		return v;
	}

	private static <T> ConstructorAccess<T> getConstructorAccess(
			Class<T> targetClass) {
		ConstructorAccess<T> constructorAccess = constructorAccessCache
				.get(targetClass.toString());
		if (constructorAccess != null) {
			return constructorAccess;
		}
		try {
			constructorAccess = ConstructorAccess.get(targetClass);
			constructorAccess.newInstance();
			constructorAccessCache.put(targetClass.toString(),
					constructorAccess);
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"Create new instance of %s failed: %s", targetClass,
					e.getMessage()));
		}
		return constructorAccess;
	}

	private static <T> MethodAccess getMethodAccess(Class<T> targetClass) {
		MethodAccess methodAccess = methodAccessCache.get(targetClass
				.toString());
		if (methodAccess != null) {
			return methodAccess;
		}
		try {
			methodAccess = MethodAccess.get(targetClass);
			methodAccessCache.put(targetClass.toString(), methodAccess);
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"Get MethodAccess of %s failed: %s", targetClass,
					e.getMessage()));
		}
		return methodAccess;
	}

	private static <T> FieldAccess getFieldAccess(Class<T> targetClass) {
		FieldAccess fieldAccess = fieldAccessCache.get(targetClass.toString());
		if (fieldAccess != null) {
			return fieldAccess;
		}
		try {
			fieldAccess = FieldAccess.get(targetClass);
			fieldAccessCache.put(targetClass.toString(), fieldAccess);
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"Get FieldAccess of %s failed: %s", targetClass,
					e.getMessage()));
		}
		return fieldAccess;
	}
	
	private static void testPOtoVO() throws InstantiationException,
			IllegalAccessException {
		UserPO po = new UserPO("11", "22", "33");
		po.setAge(new Integer(18));
		po.setShortv(new Short("11"));
		po.setLongv(new Long("12"));
		po.setCurrTime(new Timestamp(System.currentTimeMillis()));
		po.setDate(new Date(System.currentTimeMillis()));
		po.setTime(new Time(System.currentTimeMillis()));
		po.setBigDecimal(new BigDecimal("100"));
		po.setFlag(true);
		po.setDou(new Double(100.2222));
		po.setFlo(new Float(100.11));

		UserVO vo = copyProperties(po, UserVO.class);
		System.out.println(vo);

	}


	private static void testReflectAsm4IndexList(int num) throws InstantiationException,
			IllegalAccessException {
		List<UserPO> poList = new ArrayList<UserPO>();
		for(int i = 0;i <num;i++){
			UserPO po = new UserPO("11", "22", "33");
			po.setId(String.valueOf(i));
			po.setAge(new Integer(18));
			po.setShortv(new Short("11"));
			po.setLongv(new Long("12"));
			po.setCurrTime(new Timestamp(System.currentTimeMillis()));
			po.setDate(new Date(System.currentTimeMillis()));
			po.setTime(new Time(System.currentTimeMillis()));
			po.setBigDecimal(new BigDecimal("100"));
			po.setFlag(true);
			po.setDou(new Double(100.2222));
			po.setFlo(new Float(100.11));
			poList.add(po);
		}
		System.out.println("第一次");
		long start = System.currentTimeMillis();
		List<UserVO> voList = copyPropertiesOfList(poList, UserVO.class);
		long end = System.currentTimeMillis();
		System.out.println("timeout=" + (end - start));// 12 15 23 14 24
		
		System.out.println("第二次");
		long start2 = System.currentTimeMillis();
		List<UserVO> voList2 = copyPropertiesOfList(poList, UserVO.class);
		long end2 = System.currentTimeMillis();
		System.out.println("timeout=" + (end2 - start2));// 12 15 23 14 24
		
		System.out.println("第三次");
		long start3 = System.currentTimeMillis();
		List<UserVO> voList3 = copyPropertiesOfList(poList, UserVO.class);
		long end3 = System.currentTimeMillis();
		System.out.println("timeout=" + (end3 - start3));// 12 15 23 14 24
		

	}
	
	private static void testReflectAsm4Index() throws InstantiationException,
			IllegalAccessException {
		UserPO po = new UserPO("11", "22", "33");
		po.setAge(new Integer(18));
		po.setShortv(new Short("11"));
		po.setLongv(new Long("12"));
		po.setCurrTime(new Timestamp(System.currentTimeMillis()));
		po.setDate(new Date(System.currentTimeMillis()));
		po.setTime(new Time(System.currentTimeMillis()));
		po.setBigDecimal(new BigDecimal("100"));
		po.setFlag(true);
		po.setDou(new Double(100.2222));
		po.setFlo(new Float(100.11));
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			copyProperties(po, UserVO.class);
		}
		long end = System.currentTimeMillis();
		System.out.println("timeout=" + (end - start));// 12 15 23 14 24
		
	}

	private static void testBeanUtils() throws InstantiationException,
			IllegalAccessException {
		UserPO po = new UserPO("11", "22", "33");
		po.setAge(new Integer(18));

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			DefaultEntityConvertor.getInstance().copyPoToVo(po, UserVO.class);
		}
		long end = System.currentTimeMillis();
		System.out.println("timeout=" + (end - start));// 12 15 23 14 24

	}


	/**
	 * 
	 * 测试环境
	 * i5-6300HQ CPU @2.30GHz 
	 * 8G RAM
	 * 64bit
	 * @param args
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		 //testPOtoVO();
		//testReflectAsm4Index();//无类型转换，10万条数据：timeout=238ms,232ms,227ms ||| 100万条数据：1255ms,1224ms,1216ms |||1000万条数据： 10112ms
		
		//testReflectAsm4Index();//添加类型转换，10万条数据：1014，
		
		// testBeanUtils();//10万条数据：timeout=2923ms,3001ms,2958ms |||100万条数据：22810ms
		 
		//A-->10万条数据:第一次timeout=1454第二次timeout=562
		//B-->10万条数据:第一次timeout=1406第二次timeout=547
		//C-->10万条数据:第一次timeout=1421第二次timeout=562
		 testReflectAsm4IndexList(100000);
		 
	}

	private static void test() {
		UserPO p = new UserPO();
		MethodAccess access = MethodAccess.get(UserPO.class);
		int setNameIndex = access.getIndex("setName");
		access.invoke(p, setNameIndex, "Awesome McLovin");
		int getNameIndex = access.getIndex("getName");
		String name = (String) access.invoke(p, getNameIndex);
		System.out.println(name);

		// ------------------------------------------------------
		UserPO p1 = new UserPO();
		FieldAccess access1 = FieldAccess.get(UserPO.class);
		int nameIndex = access1.getIndex("name");
		access1.set(p1, nameIndex, "Awesome McLovin");
		String name1 = (String) access1.get(p1, nameIndex);
		System.out.println(name1);

		// ------------------------------------------------------
		ConstructorAccess<UserPO> access2 = ConstructorAccess.get(UserPO.class);
		UserPO p2 = access2.newInstance();
		System.out.println(p2);
	}
	
}
