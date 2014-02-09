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
package org.snaker.designer.config;

import java.util.List;

import org.snaker.designer.model.Simple;

/**
 * 配置管理
 * @author yuqs
 * @version 1.0
 */
public class ConfigManager {
	public static final String COMPONENT_TYPE_FIELD = "field";

	public static final String COMPONENT_TYPE_TASK = "task";

	public static final String COMPONENT_TYPE_PROCESS = "process";

	public static final String COMPONENT_TYPE_TRANSITION = "transition";

	public static final String COMPONENT_TYPE_SUBPROCESS = "sub-process";

	public static final String COMPONENT_TYPE_START = "start";

	public static final String COMPONENT_TYPE_END = "end";

	public static final String COMPONENT_TYPE_DECISION = "decision";

	public static final String COMPONENT_TYPE_FORK = "fork";

	public static final String COMPONENT_TYPE_JOIN = "join";

	public static final String PROPERTIES_PLACEHOLDER = "placeholder";

	public static final String PROPERTIES_LINEBR = "lineBR";

	public static final String VALUE_LIST_TRUE = "true";

	public static final String VALUE_LIST_FALSE = "false";

	public static List<Component> getComponents() {
		return Configuration.getInstance().getComponents();
	}

	/**
	 * 根据model.xml中group、type获取对应的组件对象Component
	 * 
	 * @param group
	 * @param type
	 * @return
	 */
	public static Component getComponentByGroupAndType(String group, String type) {
		List<Component> listComps = getComponents();
		for (int i = 0; i < listComps.size(); i++) {
			Component comp = (Component) listComps.get(i);
			if (comp.getGroup() != null && comp.getGroup().equalsIgnoreCase(group)) {
				if (type != null && type.equalsIgnoreCase(comp.getType())) {
					return comp;
				}
			}
		}

		return null;
	}

	public static Component getComponentByName(String name) {
		List<Component> listComps = getComponents();
		for (int i = 0; i < listComps.size(); i++) {
			Component comp = (Component) listComps.get(i);
			if (comp.getName().equalsIgnoreCase(name)) {
				return (Component) listComps.get(i);
			}
		}
		return null;
	}
	
	public static Component getComponentByModel(Object model) {
		if(model == null) return null;
		if(model instanceof Simple) return getComponentByName(((Simple)model).getNodeName());
		return getComponentByName(model.getClass().getSimpleName());
	}

	/**
	 * 根据type获取组件对象
	 * 
	 * @param type
	 * @return
	 */
	public static Component getComponent(String type) {
		List<Component> listComps = getComponents();
		for (int i = 0; i < listComps.size(); i++) {
			Component comp = (Component) listComps.get(i);
			if (comp.getType().equalsIgnoreCase(type)) {
				return (Component) listComps.get(i);
			}
		}
		return null;
	}

	public static Component getComponent(String name, String type,
			String superType) {
		List<Component> listComps = getComponents();
		for (int i = 0; i < listComps.size(); i++) {
			Component comp = (Component) listComps.get(i);
			if (comp.getName().equalsIgnoreCase(name)
					&& comp.getType().equalsIgnoreCase(type)) {
				return comp;
			}
		}

		return null;
	}

	public static Component getComponent(String name, String type) {
		return getComponent(name, type, null);
	}

	/**
	 * 根据组件、属性名称获取对应的属性配置
	 * 
	 * @param component
	 * @param name
	 * @return
	 */
	public static Property getProperty(Component component, String name) {
		List<Property> list = component.getProperties();
		if ((list == null) || (list.isEmpty())) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			Property property = (Property) list.get(i);
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	/**
	 * 根据组件对象获取占位数（1、2）
	 * 
	 * @param component
	 * @return
	 */
	public static int getPlaceHolderValue(Component component) {
		int placeHolder = 1;
		String length = getProperty(component, PROPERTIES_PLACEHOLDER)
				.getDefaultValue();
		try {
			placeHolder = Integer.valueOf(length);
		} catch (Exception e) {
			placeHolder = 1;
		}
		return placeHolder;
	}
}
