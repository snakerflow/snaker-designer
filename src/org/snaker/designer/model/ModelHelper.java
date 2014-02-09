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
package org.snaker.designer.model;

import java.util.ArrayList;
import java.util.List;

import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.config.Property;

/**
 * 模型帮助类
 * 
 * @author yuqs
 * @version 1.0
 */
public class ModelHelper {
	public static String encoding = "GBK";

	/**
	 * 根据模型对象、模型对应的组件装载属性值
	 * 
	 * @param model
	 * @param component
	 */
	public static void loadAttribute(BaseElement model, Component component) {
		List<Property> listProps = component.getProperties();
		if (listProps == null)
			return;
		boolean isExists = false;
		for (int i = 0; i < listProps.size(); i++) {
			isExists = false;
			Property prop = (Property) listProps.get(i);
			if (prop.getName().equals("name")
					|| prop.getName().equals("displayName")) {
				continue;
			}
			for (Attr attr : model.getAttrs()) {
				if (attr.getName() != null
						&& attr.getName().equalsIgnoreCase(prop.getName())) {
					isExists = true;
					break;
				}
			}
			if (!isExists) {
				Attr attr = new Attr();
				attr.setName(prop.getName());
				attr.setValue(prop.getDefaultValue());
				model.addAttr(attr);
			}
		}
	}

	/**
	 * 动态表单的字段名称获取方法
	 * 
	 * @param field
	 * @param type
	 * @return
	 */
	public static String getModelName(Field field, String type) {
		BaseElement parent = field.getParent();
		if (parent != null && parent instanceof Form) {
			Form form = (Form) parent;
			List<Field> fields = form.getFields();
			if (fields != null && !fields.isEmpty()) {
				return getModelName(fields, type.toLowerCase(), 1);
			}
		} else if (parent instanceof Task) {
			Task task = (Task) parent;
			List<Field> fields = task.getFields();
			if (fields != null && !fields.isEmpty()) {
				return getModelName(fields, type.toLowerCase(), 1);
			}
		}
		return "";
	}

	/**
	 * 根据模型对象、模型类型，获取当前模型的名称（一般名称规则为：task1,task2...）
	 * 
	 * @param model
	 * @param type
	 * @return
	 */
	public static String getModelName(BaseElement model) {
		try {
			if (model == null)
				return "";
			if (model instanceof NodeElement) {
				Process process = (Process) model.getParent();
				List<NodeElement> nodes = process.getNodeElements();
				if (nodes != null && !nodes.isEmpty()) {
					if(model instanceof Simple) {
						return getModelName(nodes, ((Simple)model).getNodeName()
							.toLowerCase(), 1);
					} else {
						return getModelName(nodes, model.getClass().getSimpleName()
								.toLowerCase(), 1);
					}
				}
			} else if (model instanceof Transition) {
				Process process = (Process) model.getParent();
				List<NodeElement> nodes = process.getNodeElements();
				if (nodes != null && !nodes.isEmpty()) {
					List<Transition> ts = new ArrayList<Transition>();
					for (NodeElement node : nodes) {
						ts.addAll(node.getOutputs());
					}
					return getModelName(ts, model.getClass().getSimpleName()
							.toLowerCase(), 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 模型名称的算法为：获取该元素的父元素，从父元素中查找所有子类型的元素，然后根据序列依次递归
	 * 
	 * @param objects
	 * @param type
	 * @param idx
	 * @return
	 */
	private static String getModelName(List<?> objects, String type, int idx) {
		for (int i = 0; i < objects.size(); i++) {
			BaseElement element = (BaseElement) objects.get(i);
			if (element.getName() != null
					&& element.getName().equals(type + idx)) {
				return getModelName(objects, type, ++idx);
			}
		}
		return type + idx;
	}

	/**
	 * 得到field模型的占位符位置
	 * 
	 * @param field
	 * @return
	 */
	public static int getPlaceHolderAttrIndex(Field field) {
		List<Attr> list = field.getAttrs();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Attr attr = (Attr) list.get(i);
				if (ConfigManager.PROPERTIES_PLACEHOLDER.equalsIgnoreCase(attr
						.getName())) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 得到field模型的换行标识
	 * 
	 * @param element
	 * @return
	 */
	public static String getElementLineBR(BaseElement element) {
		List<Attr> list = element.getAttrs();
		for (int i = 0; (list != null) && (i < list.size()); i++) {
			Attr attr = (Attr) list.get(i);
			if (attr.getName()
					.equalsIgnoreCase(ConfigManager.PROPERTIES_LINEBR)) {
				return attr.getValue();
			}
		}
		return "false";
	}

	/**
	 * 交换field模型前后位置
	 * 
	 * @param before
	 * @param after
	 */
	public static void exchangePlaceHolder(Field before, Field after) {
		String beforeValue = "";
		String afterValue = "";
		int beforeIndex = 0;
		int afterIndex = 0;
		Attr beforeAttr = null;
		Attr afterAttr = null;
		List<Attr> beforeAttrs, afterAttrs;
		beforeAttrs = before.getAttrs();
		for (int i = 0; i < beforeAttrs.size(); i++) {
			Attr attr = (Attr) beforeAttrs.get(i);
			if (!ConfigManager.PROPERTIES_LINEBR.equalsIgnoreCase(attr
					.getName())) {
				continue;
			}
			beforeValue = attr.getValue();
			beforeIndex = i;
			beforeAttr = attr;
		}

		afterAttrs = after.getAttrs();
		for (int i = 0; i < afterAttrs.size(); i++) {
			Attr attr = (Attr) afterAttrs.get(i);
			if (!ConfigManager.PROPERTIES_LINEBR.equalsIgnoreCase(attr
					.getName())) {
				continue;
			}
			afterValue = attr.getValue();
			afterIndex = i;
			afterAttr = attr;
		}
		beforeAttr.setValue(afterValue);
		beforeAttrs.set(beforeIndex, beforeAttr);
		afterAttr.setValue(beforeValue);
		afterAttrs.set(afterIndex, afterAttr);
	}

	/**
	 * 增加占位符
	 * 
	 * @param field
	 */
	public static void IncreasePlaceHolder(Field field) {
		List<Attr> attrs = field.getAttrs();
		boolean isExists = false;
		for (int i = 0; i < attrs.size(); i++) {
			Attr attr = (Attr) attrs.get(i);
			if (!ConfigManager.PROPERTIES_PLACEHOLDER.equalsIgnoreCase(attr
					.getName())) {
				continue;
			}
			isExists = true;
			int len = Integer.parseInt(attr.getValue());
			attr.setValue(String.valueOf(len + 1));
			field.setAttr(i, attr);
			return;
		}

		if (!isExists) {
			Attr attr = new Attr();
			attr.setName(ConfigManager.PROPERTIES_PLACEHOLDER);
			attr.setValue(String.valueOf(2));
			field.addAttr(attr);
		}
	}

	/**
	 * 减少占位符
	 * 
	 * @param field
	 */
	public static void DecreasePlaceHolder(Field field) {
		List<Attr> attrs = field.getAttrs();
		boolean isExists = false;
		for (int i = 0; i < attrs.size(); i++) {
			Attr attr = (Attr) attrs.get(i);
			if (!ConfigManager.PROPERTIES_PLACEHOLDER.equalsIgnoreCase(attr
					.getName())) {
				continue;
			}
			isExists = true;
			int len = Integer.parseInt(attr.getValue());
			if (1 == len) {
				break;
			}
			attr.setValue(String.valueOf(len - 1));
			field.setAttr(i, attr);
			return;
		}

		if (!isExists) {
			Attr attr = new Attr();
			attr.setName(ConfigManager.PROPERTIES_PLACEHOLDER);
			attr.setValue(String.valueOf(1));
			field.addAttr(attr);
		}
	}
}
