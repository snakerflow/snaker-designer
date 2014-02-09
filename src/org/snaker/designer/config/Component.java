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

import java.util.ArrayList;
import java.util.List;

/**
 * 组件bean
 * @author yuqs
 * @version 1.0
 */
public class Component {
	private static final String MODEL_PACKAGE = "org.snaker.designer.model.";
	/*
	 * 组件名称
	 */
	private String name;
	/*
	 * 组件中文名称
	 */
	private String displayName;
	/*
	 * 组件类型
	 */
	private String type;
	/*
	 * 组件分组
	 */
	private String group;
	/*
	 * 组件描述
	 */
	private String descript;
	/*
	 * 组件小图标
	 */
	private String iconSmall;
	/*
	 * 组件大图标
	 */
	private String iconLarge;
	/*
	 * 组件是否可见
	 */
	private String visible;
	/*
	 * 组件类型
	 */
	private String clazz;
	/*
	 * 组件包含的属性元素集合
	 */
	private List<Property> properties = new ArrayList<Property>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getIconSmall() {
		return iconSmall;
	}

	public void setIconSmall(String iconSmall) {
		this.iconSmall = iconSmall;
	}

	public String getIconLarge() {
		return iconLarge;
	}

	public void setIconLarge(String iconLarge) {
		this.iconLarge = iconLarge;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public String getClazz() {
		return clazz == null || clazz.equals("") ? MODEL_PACKAGE + name : clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
