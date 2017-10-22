package com.lsr.frame.ws.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WSConfigDTDEntityResolver implements EntityResolver, Serializable{

	private static final long serialVersionUID = 8009739988532030723L;

	private static final Log log = LogFactory.getLog(WSConfigDTDEntityResolver.class);
	
	private static final String WS_CONFIG_NAMESPACE = "http://lsr.cn/";
	
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException,IOException {
		if(systemId != null){
			if(systemId.startsWith(WS_CONFIG_NAMESPACE)){
				String      path   =  systemId.substring(WS_CONFIG_NAMESPACE.length());
				InputStream dtdStream = resolveInNamespace(path);
				if(dtdStream == null){
					throw new IOException("not find DTD com/lsr/ws/config/" + path);
				}
				InputSource source = new InputSource( resolveInNamespace(path) );
				source.setPublicId(publicId);
				source.setSystemId(systemId);
				return source;
			}
		}
		return null;
	}
	
	protected InputStream resolveInNamespace(String path) {
		return this.getClass().getResourceAsStream(path);
	}
}
