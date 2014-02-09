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

/**
 * 属性bean
 * @author yuqs
 * @version 1.0
 */
public class Property {
	public static final String TYPE_LIST = "list";
	/*
	 * 属性分组
	 */
	private String group;
	/*
	 * 属性名称
	 */
	private String name;
	/*
	 * 属性类型
	 */
	private String type;
	/*
	 * 属性默认值
	 */
	private String defaultValue;
	/*
	 * 属性值集合
	 */
	private String values;
	/*
	 * 属性是否可见
	 */
	private String visible;
	/*
	 * 属性校验器
	 */
	private String validator;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValues() {
		return values == null ? "" : values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getVisible() {
		return visible == null ? "" : visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getValidator() {
		return validator == null ? "" : validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getDefaultValue() {
		return defaultValue == null ? "" : defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
