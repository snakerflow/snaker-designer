/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.designer.io;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.snaker.designer.io.reader.XmlReader;
import org.snaker.designer.io.reader.impl.*;
import org.snaker.designer.io.writer.impl.*;
import org.snaker.designer.io.writer.XmlWriter;
import org.snaker.designer.model.Field;
import org.snaker.designer.model.Form;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Process;
import org.snaker.designer.model.Transition;
import org.snaker.designer.utils.StringUtils;
import org.snaker.designer.utils.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 设计器模型与xml的io转换
 * 该类缓存所有的节点reader、writer
 * @author yuqs
 * @version 1.0
 */
public class Environment {
	/**
	 * 节点名称
	 */
	public static final String TRANSITION = "transition";
	public static final String PROCESS = "process";
	public static final String ATTR = "attr";
	
	public static final String NODE_TASK = "task";
	public static final String FORM_FORM = "form";
	public static final String FORM_FIELD = "field";
	
	/**
	 * 节点属性名称
	 */
	public static final String ATTR_NAME = "name";
	public static final String ATTR_DISPLAYNAME = "displayName";
	public static final String ATTR_LAYOUT = "layout";
	public static final String ATTR_INSTANCEURL = "instanceUrl";
	public static final String ATTR_INSTANCENOCLASS = "instanceNoClass";
	public static final String ATTR_EXPR = "expr";
	public static final String ATTR_HANDLE = "handleClass";
	public static final String ATTR_FORM = "form";
	public static final String ATTR_ASSIGNEE = "assignee";
	public static final String ATTR_ASSIGNEMENT_HANDLER = "assignmentHandler";
	public static final String ATTR_PERFORMTYPE = "performType";
	public static final String ATTR_TO = "to";
	public static final String ATTR_G = "g";
	public static final String ATTR_OFFSET = "offset";
	public static final String ATTR_PROCESSNAME = "processName";
	public static final String ATTR_EXPIRETIME = "expireTime";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_TYPE = "type";
    public static final String ATTR_CLAZZ = "clazz";
    public static final String ATTR_METHODNAME = "methodName";
    public static final String ATTR_ARGS = "args";
    public static final String ATTR_VAR = "var";
    public static final String ATTR_INTERCEPTORS_PRE = "preInterceptors";
    public static final String ATTR_INTERCEPTORS_POST = "postInterceptors";
	
	/**
	 * xml的reader集合
	 */
	private static Map<String, XmlReader> readerMaps = new HashMap<String, XmlReader>();
	/**
	 * xml的writer集合
	 */
	private static Map<String, XmlWriter> writerMaps = new HashMap<String, XmlWriter>();
	static {
		readerMaps.put(NODE_TASK, new TaskReader());
		readerMaps.put(FORM_FIELD, new FieldReader());
		
		writerMaps.put(NODE_TASK, new TaskWriter());
		writerMaps.put(FORM_FIELD, new FieldWriter());
	}
	
	/**
	 * 根据xml节点名称，获取对应的解析reader
	 * @param nodeName
	 * @return
	 */
	public static XmlReader getReader(String nodeName) {
		return readerMaps.get(nodeName);
	}
	
	/**
	 * 根据xml节点名称，获取对应的输出writer
	 * @param nodeName
	 * @return
	 */
	public static XmlWriter getWriter(String nodeName) {
		return writerMaps.get(nodeName);
	}
	
	/**
	 * 向指定的文件file写入流程定义对象process
	 * @param process
	 * @param file
	 */
	public static void writer(Process process, String file) {
		Document doc = getDocment(process);
		try {
			XmlUtils.writeToFile(doc, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向指定的文件file写入表单定义对象form
	 * @param form
	 * @param file
	 */
	public static void writer(Form form, String file) {
		Document doc = getDocment(form);
		try {
			XmlUtils.writeToFile(doc, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据表单定义对象form构造xml的Document对象
	 * @param form
	 * @return
	 */
	private static Document getDocment(Form form) {
		DocumentBuilder documentBuilder = XmlUtils.createDocumentBuilder();
		Document doc = null;
		Element root = null;
		try {
			doc = documentBuilder.newDocument();
			root = doc.createElement(FORM_FORM);
			root.setAttribute(ATTR_NAME, form.getName());
			root.setAttribute(ATTR_DISPLAYNAME, form.getDisplayName());
			
			XmlWriter writer = getWriter(FORM_FIELD);
			for(Field field : form.getFields()) {
				writer.writer(root, field);
			}
			doc.appendChild(root);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 根据流程定义对象process构造xml的Document对象
	 * @param process
	 * @return
	 */
	private static Document getDocment(Process process) {
		DocumentBuilder documentBuilder = XmlUtils.createDocumentBuilder();
		Document doc = null;
		Element root = null;
		try {
			doc = documentBuilder.newDocument();
			root = doc.createElement(PROCESS);
			root.setAttribute(ATTR_NAME, process.getName());
			root.setAttribute(ATTR_DISPLAYNAME, process.getDisplayName());
			if(StringUtils.isNotEmpty(process.getExpireTime())) {
				root.setAttribute(ATTR_EXPIRETIME, process.getExpireTime());
			}
			if(StringUtils.isNotEmpty(process.getInstanceUrl())) {
				root.setAttribute(ATTR_INSTANCEURL, process.getInstanceUrl());
			}
			if(StringUtils.isNotEmpty(process.getInstanceNoClass())) {
				root.setAttribute(ATTR_INSTANCENOCLASS, process.getInstanceNoClass());
			}
			
			List<NodeElement> nodes = process.getNodeElements();
			for(NodeElement node : nodes) {
				XmlWriter writer = getWriter(node.getClass().getSimpleName().toLowerCase());
				if(writer == null) {
					writer = new SimpleWriter();
				}
				writer.writer(root, node);
			}
			doc.appendChild(root);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 从指定的文件file中读取Form对象
	 * @param file
	 * @return
	 */
	public static Form readerForm(String file) {
		DocumentBuilder documentBuilder = XmlUtils.createDocumentBuilder();
		Form form = new Form();
		if(documentBuilder != null) {
			Document doc = null;
			try {
				doc = documentBuilder.parse(new FileInputStream(file));
				Element root = doc.getDocumentElement();
				form.setName(root.getAttribute(ATTR_NAME));
				form.setDisplayName(root.getAttribute(ATTR_DISPLAYNAME));
				
				NodeList nodeList = root.getChildNodes();
				int nodeSize = nodeList.getLength();
				for(int i = 0; i < nodeSize; i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						XmlReader reader = getReader(FORM_FIELD);
						Field field = (Field)reader.parse((Element)node);
						form.addField(field);
					}
				}
			} catch(Exception e) {
			}
		}
		return form;
	}
	
	/**
	 * 从指定的文件file中读取Process对象
	 * @param file
	 * @return
	 */
	public static Process readerProcess(String file) {
		Process process = new Process();
		DocumentBuilder documentBuilder = XmlUtils.createDocumentBuilder();
		if(documentBuilder != null) {
			Document doc = null;
			try {
				doc = documentBuilder.parse(new FileInputStream(file));
				Element root = doc.getDocumentElement();
				process.setName(root.getAttribute(ATTR_NAME));
				process.setDisplayName(root.getAttribute(ATTR_DISPLAYNAME));
				process.setExpireTime(root.getAttribute(ATTR_EXPIRETIME));
				process.setInstanceUrl(root.getAttribute(ATTR_INSTANCEURL));
				process.setInstanceNoClass(root.getAttribute(ATTR_INSTANCENOCLASS));
				NodeList nodeList = root.getChildNodes();
				int nodeSize = nodeList.getLength();
				for(int i = 0; i < nodeSize; i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						NodeElement model = parseModel(node);
						process.getNodeElements().add(model);
					}
				}
				
				//循环节点模型，构造变迁输入、输出的source、target
				for(NodeElement node : process.getNodeElements()) {
					node.setParent(process);
					for(Transition transition : node.getOutputs()) {
						transition.setParent(process);
						String to = transition.getTo();
						for(NodeElement node2 : process.getNodeElements()) {
							if(to.equalsIgnoreCase(node2.getName())) {
								node2.getInputs().add(transition);
								transition.setTarget(node2);
							}
						}
					}
				}
			} catch(Exception e) {
			} catch(Error e) {
			}
		}
		return process;
	}
	
	/**
	 * 对流程定义xml的节点，根据其节点对应的解析器解析节点内容
	 * @param node
	 * @return
	 */
	private static NodeElement parseModel(Node node) {
		String nodeName = node.getNodeName();
		Element element = (Element)node;
		XmlReader nodeParser = null;
		try {
			nodeParser = getReader(nodeName);
			if(nodeParser != null) {
				return (NodeElement)nodeParser.parse(element);
			} else {
				return (NodeElement)new SimpleReader().parse(element);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
