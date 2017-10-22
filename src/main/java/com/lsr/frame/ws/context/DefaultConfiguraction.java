package com.lsr.frame.ws.context;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lsr.frame.ws.config.WSActionConfig;
import com.lsr.frame.ws.config.WSConfigDTDEntityResolver;
import com.lsr.frame.ws.config.WSConfiguration;
import com.lsr.frame.ws.config.WSContextConfigConstants;
import com.lsr.frame.ws.config.WSInterceptorConfig;
import com.lsr.frame.ws.config.WSPropertyConfig;
import com.lsr.frame.ws.config.WSVOConfig;
import com.lsr.frame.ws.control.WSAction;
import com.lsr.frame.ws.exception.WSConfigException;
import com.lsr.frame.ws.utils.DomUtils;

public class DefaultConfiguraction implements WSConfiguration{
	private final static Log LOG = LogFactory.getLog(DefaultConfiguraction.class);

	protected Map wSVOConfigByIdMap    = new HashMap();
	protected Map wsVOConfigByClassMap = new HashMap();
	protected Map wSActionConfigMap    = new HashMap();
	protected Map wSInterceptorConfigByIdMap    = new HashMap();
	protected Map wSInterceptorConfigByClassMap = new HashMap();
	protected Map globalWSInterceptorConfigMap  = new LinkedHashMap();

	private String mainConfigFile = null;
	private String currentConfigFile = null;

	public WSConfiguration config(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			throw new IllegalArgumentException("filePath is null");
		}
		InputStream fileSource = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		if (fileSource == null) {
			throw new WSConfigException("not find config file:'" + filePath + "'");
		}
		LOG.info("ws config about file:'" + filePath + "'");
		if (mainConfigFile == null) {
			mainConfigFile = filePath;
		}
		currentConfigFile = filePath;
		return config(fileSource);
	}

	public WSConfiguration config(InputStream fileSource) {
		doConfig(fileSource);
		return this;
	}

	protected void doConfig(InputStream fileSource) {
		
		try {

				if (fileSource == null) {
					throw new IllegalArgumentException("fileSource is null");
				}
				Document configDoc = DomUtils.loadDocument(fileSource, new WSConfigDTDEntityResolver());
				Element wsConfigEle = configDoc.getDocumentElement();

				// 拦截器 interceptors
				Element interceptorsEle = DomUtils.getChildElementByTagName(wsConfigEle, WSContextConfigConstants.TAG_INTERCEPTORS_DEFINE);
				if (interceptorsEle != null) {
					List interceptorEleList = DomUtils.getChildElementsByTagName(interceptorsEle, WSContextConfigConstants.TAG_INTERCEPTOR);
					for (int i = 0; i < interceptorEleList.size(); i++) {
				
						Element wsInterceptorEle = (Element) interceptorEleList.get(i);
						String id = DomUtils.getElementAttributeValue(wsInterceptorEle, WSContextConfigConstants.ATT_ID);
						String type = DomUtils.getElementAttributeValue(wsInterceptorEle, WSContextConfigConstants.ATT_TYPE);

						WSInterceptorConfig interceptorConfig = new WSInterceptorConfig(id, type);
						if (wSInterceptorConfigByIdMap.get(id) == null) {
							wSInterceptorConfigByIdMap.put(id, interceptorConfig);
						} else {
							throw new WSConfigException("wsinterceptor id:'" + id + "' duplicate, file:'" + currentConfigFile + "'");
						}
						if (wSInterceptorConfigByClassMap.get(interceptorConfig.getType()) == null) {
							wSInterceptorConfigByClassMap.put(interceptorConfig.getType(), interceptorConfig);
						} else {
							throw new WSConfigException("wsinterceptor type:'" + type + "' duplicate, file:'" + currentConfigFile + "'");
						}
						LOG.info("config wsinterceptor id:'" + id + "', type:'" + type + "'");
					}
				}

				// 全局配置 global
				Element globalEle = DomUtils.getChildElementByTagName(wsConfigEle, WSContextConfigConstants.TAG_GLOBAL_DEFINE);
				if (globalEle != null) {
					Collection interceptorConfigColl = getReferencedInterceptors(globalEle);
					for (Iterator it = interceptorConfigColl.iterator(); it.hasNext();) {
						WSInterceptorConfig interceptorConfig = (WSInterceptorConfig) it.next();
						interceptorConfig.setGlobal(true);
						globalWSInterceptorConfigMap.put(interceptorConfig.getId(), interceptorConfig);
						LOG.info("wsinterceptor id:'" + interceptorConfig.getId() + "' is global");
					}
				}

				// WSVO配置
				List wsvosEleList = DomUtils.getChildElementsByTagName(wsConfigEle, WSContextConfigConstants.TAG_WSVOS_DEFINE);
				for (int i = 0; i < wsvosEleList.size(); i++) {
					Element wsvosEle = (Element) wsvosEleList.get(i);
					String packageName = DomUtils.getElementAttributeValue(wsvosEle, WSContextConfigConstants.ATT_PACKAGE);
					List wsvoEleList = DomUtils.getChildElementsByTagName(wsvosEle, WSContextConfigConstants.TAG_WSVO);
					for (int j = 0; j < wsvoEleList.size(); j++) {
						Element wsvoEle = (Element) wsvoEleList.get(j);
						String id = DomUtils.getElementAttributeValue(wsvoEle, WSContextConfigConstants.ATT_ID);
						String type = (StringUtils.isNotBlank(packageName) ? (packageName + WSContextConfigConstants.PACKAGE_SPLIT) : "") + DomUtils.getElementAttributeValue(wsvoEle, WSContextConfigConstants.ATT_TYPE);
						WSVOConfig wsvoConfig = new WSVOConfig(id, type);
						// WSVO Porperty配置
						List wsvoPropertyEleList = DomUtils.getChildElementsByTagName(wsvoEle, WSContextConfigConstants.TAG_WSVOPROPERTY);

						if (wsvoPropertyEleList.isEmpty()) {
							throw new WSConfigException("<" + WSContextConfigConstants.TAG_WSVO + "> lost <" + WSContextConfigConstants.TAG_WSVOPROPERTY + ">, file:'" + currentConfigFile + "'");
						}

						for (int z = 0; z < wsvoPropertyEleList.size(); z++) {
							Element wsvoPropertyEle = (Element) wsvoPropertyEleList.get(z);
							String proId = DomUtils.getElementAttributeValue(wsvoPropertyEle, WSContextConfigConstants.ATT_ID);
							String property = DomUtils.getElementAttributeValue(wsvoPropertyEle, WSContextConfigConstants.ATT_PROPERTY);
							String realtype = DomUtils.getElementAttributeValue(wsvoPropertyEle, WSContextConfigConstants.ATT_REALTYPE);

							WSPropertyConfig wsPropertyConfig = null;
							if (StringUtils.isNotEmpty(realtype)) {
								wsPropertyConfig = new WSPropertyConfig(proId, property, realtype);
							} else {
								wsPropertyConfig = new WSPropertyConfig(proId, property);
							}
							wsvoConfig.addPropertyConfig(wsPropertyConfig);
						}
						if (wSVOConfigByIdMap.get(id) == null) {
							wSVOConfigByIdMap.put(id, wsvoConfig);
						} else {
							throw new WSConfigException("wsvo id:'" + id + "' duplicate, file:'" + currentConfigFile + "'");
						}
						if (wSVOConfigByIdMap.get(wsvoConfig.getType()) == null) {
							wsVOConfigByClassMap.put(wsvoConfig.getType(), wsvoConfig);
						} else {
							throw new WSConfigException("wsvo type:'" + type + "' duplicate, file:'" + currentConfigFile + "'");
						}
						LOG.info("config wsvo id:'" + id + "', type:'" + type + "'");
					}
				}

				// WSAction配置
				List wsActionsEleList = DomUtils.getChildElementsByTagName(wsConfigEle, WSContextConfigConstants.TAG_WSACTIONS_DEFINE);
				for (int i = 0; i < wsActionsEleList.size(); i++) {
					Element wsActionsEle = (Element) wsActionsEleList.get(i);
					String packageName = DomUtils.getElementAttributeValue(wsActionsEle, WSContextConfigConstants.ATT_PACKAGE);
					// 获得<wsactions-define>内的全局拦截器，这些拦截器作用于<wsactions-define>内的所有wsaction，并且优先执行
					Collection wsActionOuterInterceptors = getReferencedInterceptors(wsActionsEle);

					List wsActionEleList = DomUtils.getChildElementsByTagName(wsActionsEle, WSContextConfigConstants.TAG_WSACTION);
					for (int j = 0; j < wsActionEleList.size(); j++) {
						Element wsActionEle = (Element) wsActionEleList.get(j);
						// /获得<wsaction>内的全局拦截器
						Collection wsActionInnerInterceptors = getReferencedInterceptors(wsActionEle);

						String id = DomUtils.getElementAttributeValue(wsActionEle, WSContextConfigConstants.ATT_ID);
						String type = (StringUtils.isNotBlank(packageName) ? (packageName + WSContextConfigConstants.PACKAGE_SPLIT) : "") + DomUtils.getElementAttributeValue(wsActionEle, WSContextConfigConstants.ATT_TYPE);
						String scope = DomUtils.getElementAttributeValue(wsActionEle, WSContextConfigConstants.ATT_SCOPE);

						WSActionConfig wsActionConfig = new WSActionConfig(id, type, scope);

						if (!WSAction.class.isAssignableFrom(wsActionConfig.getType())) {
							throw new WSConfigException("wsaction type:'" + type + "' not implement " + WSAction.class.getName() + ", file:'" + currentConfigFile + "'");
						}

						if (wsActionOuterInterceptors != null && !wsActionOuterInterceptors.isEmpty()) {
							wsActionConfig.getInterceptorConfigs().addAll(wsActionOuterInterceptors);
						}
						if (wsActionInnerInterceptors != null && !wsActionInnerInterceptors.isEmpty()) {
							wsActionConfig.getInterceptorConfigs().addAll(wsActionInnerInterceptors);
						}

						// 获得wsAction 拦截需要忽略的方法
						Element interceptIgnoreSetEle = DomUtils.getChildElementByTagName(wsActionEle, WSContextConfigConstants.TAG_INTERCEPT_IGNORE_SET);
						if (interceptIgnoreSetEle != null) {
							List interceptIgnoreEleList = DomUtils.getChildElementsByTagName(interceptIgnoreSetEle, WSContextConfigConstants.TAG_INTERCEPT_IGNORE);
							for (int z = 0; z < interceptIgnoreEleList.size(); z++) {
								Element interceptIgnoreEle = (Element) interceptIgnoreEleList.get(z);
								String interceptorId = DomUtils.getElementAttributeValue(interceptIgnoreEle, WSContextConfigConstants.ATT_INTERCEPTORID);
								String actionMethods = DomUtils.getElementAttributeValue(interceptIgnoreEle, WSContextConfigConstants.ATT_ACTIONMETHODS);
								if (wSInterceptorConfigByIdMap.get(interceptorId) == null) {
									throw new WSConfigException(getInterceptorConfigLostMessage(interceptorId));
								}
								wsActionConfig.addInterceptIgnoreMethod(interceptorId, actionMethods);
							}
						}

						if (wSActionConfigMap.get(id) == null) {
							wSActionConfigMap.put(id, wsActionConfig);
							LOG.info("config wsaction id:'" + id + "', type:'" + type + "'");
						} else {
							throw new WSConfigException("wsaction id:'" + id + "' duplicate, file:'" + currentConfigFile + "'");
						}
					}
				}

				// 获得config-include
				Element configIncEle = DomUtils.getChildElementByTagName(wsConfigEle, WSContextConfigConstants.TAG_CONFIG_INCLUDE);
				if (configIncEle != null) {

					if (!currentConfigFile.equals(mainConfigFile)) {
						throw new WSConfigException("file:'" + currentConfigFile + "', <" + WSContextConfigConstants.TAG_CONFIG_INCLUDE + "> just allow set in file:'" + mainConfigFile + "'");
					}
					List resourceEleList = DomUtils.getChildElementsByTagName(configIncEle, WSContextConfigConstants.TAG_RESOURCE);
					for (int i = 0; i < resourceEleList.size(); i++) {
						Element resourceEle = (Element) resourceEleList.get(i);
						String path = DomUtils.getElementAttributeValue(resourceEle, WSContextConfigConstants.ATT_PATH);

						config(path);
					}
				}

			} catch (Exception ex) {
				throw new WSConfigException(this.currentConfigFile + ": " + ex.getMessage(), ex);
			}

	}

	/**
	 * 获得已经定义的拦截器配置
	 * 
	 * @param interceptorIds
	 *            拦截器ID字符串，采用逗号分隔
	 * @return
	 */
	private Collection getDefinedInterceptorConfig(String interceptorIds) {
		// LinkedHashSet保证拦截器不会重复
		Collection retColl = new LinkedHashSet();
		if (StringUtils.isEmpty(interceptorIds)) {
			return retColl;
		}
		String[] interceptorIdArray = interceptorIds.split(WSContextConfigConstants.ATT_VALUE_SPLIT);
		for (int i = 0; i < interceptorIdArray.length; i++) {
			WSInterceptorConfig interceptorInMap = (WSInterceptorConfig) wSInterceptorConfigByIdMap
					.get(interceptorIdArray[i]);
			if (interceptorInMap == null) {
				throw new WSConfigException(getInterceptorConfigLostMessage(interceptorIdArray[i]));
			}
			retColl.add(interceptorInMap);
		}
		return retColl;
	}

	/**
	 * 
	 * @param interceptorId
	 * @return
	 */
	private String getInterceptorConfigLostMessage(String interceptorId) {
		return "not find id:'" + interceptorId + "' wsinterceptor, file:" + currentConfigFile;
	}

	/**
	 * 获得某元素内的拦截器引用ID
	 * 
	 * @param element
	 * @return
	 */
	private String getReferencedInterceptorIds(Element element) {
		if (element == null) {
			throw new IllegalArgumentException("element is null");
		}
		Element interceptorsEle = DomUtils.getChildElementByTagName(element,
				WSContextConfigConstants.TAG_INTERCEPTORS_REF);
		if (interceptorsEle == null) {
			// throw new WSConfigException("在<" + element.getTagName() +
			// ">内没有定义<interceptors-ref>");
			return null;
		}
		return DomUtils.getElementAttributeValue(interceptorsEle, WSContextConfigConstants.ATT_REFERENCES);
	}

	/**
	 * 获得元素内的拦截器引用
	 * 
	 * @param element
	 * @return
	 */
	private Collection getReferencedInterceptors(Element element) {
		String ids = getReferencedInterceptorIds(element);
		return getDefinedInterceptorConfig(ids);
	}

	public WSActionConfig getWSActionConfig(String id) {
		WSActionConfig config = (WSActionConfig) wSActionConfigMap.get(id);
		if (config == null) {
			throw new WSConfigException("not config id:'" + id + "' wsaction");
		}
		return config;
	}

	public WSInterceptorConfig getWSInterceptorConfig(String id) {
		WSInterceptorConfig config = (WSInterceptorConfig) wSInterceptorConfigByIdMap.get(id);
		if (config == null) {
			throw new WSConfigException("not config id:'" + id + "' wsinterceptor");
		}
		return config;
	}

	public WSInterceptorConfig getWSInterceptorConfig(Class interceptorClazz) {
		WSInterceptorConfig config = (WSInterceptorConfig) wSInterceptorConfigByClassMap.get(interceptorClazz);
		if (config == null) {
			throw new WSConfigException("not config type:'" + interceptorClazz.getName() + "' wsinterceptor");
		}
		return config;
	}

	public Collection getAllGlobalWSInterceptorConfig() {
		return globalWSInterceptorConfigMap.values();
	}

	public WSVOConfig getWSVOConfig(String id) {
		WSVOConfig config = (WSVOConfig) wSVOConfigByIdMap.get(id);
		if (config == null) {
			throw new WSConfigException("not config id:'" + id + "' wsvo");
		}
		return config;
	}

	public WSVOConfig getWSVOConfig(Class voClazz) {
		WSVOConfig config = (WSVOConfig) wsVOConfigByClassMap.get(voClazz);
		if (config == null) {
			throw new WSConfigException("not config type:'" + voClazz.getName() + "' wsvo");
		}
		return config;
	}

	public boolean isWSVO(Class voClazz) {
		WSVOConfig config = (WSVOConfig) wsVOConfigByClassMap.get(voClazz);
		return config != null;
	}

	public boolean isWSVO(Object object) {
		return isWSVO(object.getClass());
	}

	public void clear() {
		wSVOConfigByIdMap.clear();
		wsVOConfigByClassMap.clear();
		wSActionConfigMap.clear();
		wSInterceptorConfigByIdMap.clear();
		globalWSInterceptorConfigMap.clear();
	}

	public String getMainConfigFile() {
		return mainConfigFile;
	}
}
