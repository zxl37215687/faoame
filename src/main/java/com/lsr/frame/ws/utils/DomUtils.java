package com.lsr.frame.ws.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.helpers.DefaultHandler;

public abstract class DomUtils {
	/**
	 * 
	 * @param source
	 * @return
	 */
	public static Document loadDocument(String source, EntityResolver entityResolver) {
		return loadDocument(new ByteArrayInputStream(source.getBytes()), entityResolver);
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static Document loadDocument(InputStream source, EntityResolver entityResolver) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			if (entityResolver != null) {
				docBuilder.setEntityResolver(entityResolver);
			}
			docBuilder.setErrorHandler(new DefaultHandler());
			return docBuilder.parse(source);
		} catch (Throwable th) {
			th.printStackTrace(System.out);
			return null;
		}
	}

	/**
	 * 
	 * @param docSource
	 * @return
	 */
	public static String toString(Document docSource) {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer();
			StringWriter out = new StringWriter();
			transformer.transform(new DOMSource(docSource), new StreamResult(out));
			return out.toString();
		} catch (Throwable th) {
			th.printStackTrace(System.out);
		}
		return null;
	}

	/**
	 * 
	 * @param attributeName
	 * @param node
	 * @return
	 */
	public static String getElementAttributeValue(Element element, String attributeName) {
		try {
			String value = element.getAttribute(attributeName);
			if (value == null) {
				return "";
			}
			return value;
		} catch (Throwable th) {
			return "";
		}
	}

	/**
	 * Retrieve all child elements of the given DOM element that match the given
	 * element name. Only look at the direct child level of the given element;
	 * do not go into further depth (in contrast to the DOM API's
	 * <code>getElementsByTagName</code> method).
	 * 
	 * @param ele
	 *            the DOM element to analyze
	 * @param childEleName
	 *            the child element name to look for
	 * @return a List of child <code>org.w3c.dom.Element</code> instances
	 * @see org.w3c.dom.Element
	 * @see org.w3c.dom.Element#getElementsByTagName
	 */
	public static List getChildElementsByTagName(Element ele, String childEleName) {
		NodeList nl = ele.getChildNodes();
		List childEles = new ArrayList();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element && nodeNameEquals(node, childEleName)) {
				childEles.add(node);
			}
		}
		return childEles;
	}

	/**
	 * Utility method that returns the first child element identified by its
	 * name.
	 * 
	 * @param ele
	 *            the DOM element to analyze
	 * @param childEleName
	 *            the child element name to look for
	 * @return the <code>org.w3c.dom.Element</code> instance, or
	 *         <code>null</code> if none found
	 */
	public static Element getChildElementByTagName(Element ele, String childEleName) {
		NodeList nl = ele.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element && nodeNameEquals(node, childEleName)) {
				return (Element) node;
			}
		}
		return null;
	}

	/**
	 * Utility method that returns the first child element value identified by
	 * its name.
	 * 
	 * @param ele
	 *            the DOM element to analyze
	 * @param childEleName
	 *            the child element name to look for
	 * @return the extracted text value, or <code>null</code> if no child
	 *         element found
	 */
	public static String getChildElementValueByTagName(Element ele, String childEleName) {
		Element child = getChildElementByTagName(ele, childEleName);
		return (child != null ? getTextValue(child) : null);
	}

	/**
	 * Namespace-aware equals comparison. Returns <code>true</code> if either
	 * {@link Node#getLocalName} or {@link Node#getNodeName} equals
	 * <code>desiredName</code>, otherwise returns <code>false</code>.
	 */
	public static boolean nodeNameEquals(Node node, String desiredName) {
//		Assert.notNull(node, "Node must not be null");
//		Assert.notNull(desiredName, "Desired name must not be null");
		return desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName());
	}

	/**
	 * Extract the text value from the given DOM element, ignoring XML comments.
	 * <p>
	 * Appends all CharacterData nodes and EntityReference nodes into a single
	 * String value, excluding Comment nodes.
	 * 
	 * @see CharacterData
	 * @see EntityReference
	 * @see Comment
	 */
	public static String getTextValue(Element valueEle) {
		StringBuffer value = new StringBuffer();
		NodeList nl = valueEle.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node item = nl.item(i);
			if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference) {
				value.append(item.getNodeValue());
			}
		}
		return value.toString();
	}

}
