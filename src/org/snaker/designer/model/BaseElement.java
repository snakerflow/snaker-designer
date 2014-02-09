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

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 所有模型元素基类，该类提供通用的属性字段
 * 
 * @author yuqs
 * @version 1.0
 */
public class BaseElement extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1411896981775313634L;

	public static final String PROP_LOCATION = "LOCATION";

	public static final String PROP_SIZE = "SIZE";

	public static final String PROP_NAME = "NAME";

	public static final String PROP_LAYOUT = "LAYOUT";

	public static final String PROP_DISPLAYNAME = "DISPLAYNAME";

	public static final String PROP_ATTRS = "ATTRS";
	/**
	 * 英文名称
	 */
	protected String name;
	/**
	 * 显示名称
	 */
	protected String displayName;
	/**
	 * 元素范围
	 */
	private Rectangle layout;
	/**
	 * 元素属性
	 */
	private List<Attr> attrs = new ArrayList<Attr>();
	/**
	 * 父元素
	 */
	private BaseElement parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		firePropertyChange(PROP_NAME, null, name);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		firePropertyChange(PROP_DISPLAYNAME, null, displayName);
	}

	public Rectangle getLayout() {
		return layout;
	}

	public void setLayout(Rectangle layout) {
		this.layout = layout;
		firePropertyChange(PROP_LAYOUT, null, layout);
	}

	public List<Attr> getAttrs() {
		if (attrs == null) {
			attrs = new ArrayList<Attr>();
		}
		return attrs;
	}

	public void setAttrs(List<Attr> attrs) {
		this.attrs = attrs;
	}

	public void setAttr(int i, Attr attr) {
		getAttrs().set(i, attr);
		firePropertyChange(PROP_ATTRS, null, attr);
	}

	public void addAttr(Attr attr) {
		getAttrs().add(attr);
		firePropertyChange(PROP_ATTRS, null, attr);
	}

	public BaseElement getParent() {
		return parent;
	}

	public void setParent(BaseElement parent) {
		this.parent = parent;
	}
}
