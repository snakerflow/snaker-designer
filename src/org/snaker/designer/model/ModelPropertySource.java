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

import java.util.List;
import java.util.Vector;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.config.Property;
import org.snaker.designer.utils.BeanUtil;
import org.snaker.designer.validators.NameUniqueValidator;

/**
 * 模型对应的属性
 * 
 * @author yuqs
 * @version 1.0
 */
public class ModelPropertySource implements IPropertySource {
	/**
	 * 模型對象
	 */
	private BaseElement object;
	/**
	 * 模型組建
	 */
	private Component component;

	public ModelPropertySource(BaseElement object, Component component) {
		this.object = object;
		this.component = component;
	}

	@Override
	public Object getEditableValue() {
		return this.object;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		Vector<IPropertyDescriptor> descriptors = new Vector<IPropertyDescriptor>();

		try {
			return getPropertyDescriptors(component, descriptors);
		} catch (Exception e) {
			e.printStackTrace();
			return (IPropertyDescriptor[]) descriptors
					.toArray(new IPropertyDescriptor[0]);
		}
	}

	private IPropertyDescriptor[] getPropertyDescriptors(Component component,
			Vector<IPropertyDescriptor> descriptors) throws Exception {
		List<Property> listProps = component.getProperties();
		if (listProps == null) {
			return (IPropertyDescriptor[]) descriptors
					.toArray(new IPropertyDescriptor[0]);
		}
		int index = 1;
		for (int i = 0; i < listProps.size(); i++) {
			Property prop = (Property) listProps.get(i);
			addPropertyDescriptor(index, prop.getName(), descriptors, prop);
			index++;
		}
		return (IPropertyDescriptor[]) descriptors
				.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	public Object getPropertyValue(Object id) {
		Object value = null;
		Property prop = ConfigManager.getProperty(component, (String) id);
		try {
			value = BeanUtil.getPropertyValue(this.object, (String) id);
			if (value == null && prop != null) {
				value = prop.getDefaultValue();
			}
		} catch (Exception e) {
			List<Attr> attrs = this.object.getAttrs();
			if (attrs != null && !attrs.isEmpty()) {
				for (Attr attr : attrs) {
					if (attr.getName().equalsIgnoreCase((String) id)) {
						value = attr.getValue();
					}
				}
			}
			if(this.object instanceof Simple) {
				Simple model = (Simple)object;
				value = model.getAttributes().get((String) id);
			}
			if (value == null && prop != null)
				value = prop.getDefaultValue();
		}

		if (prop.getType() != null
				&& Property.TYPE_LIST.equalsIgnoreCase(prop.getType())) {
			String values = prop.getValues();
			String[] valueArr = values.split(";");
			value = getItemIndex(valueArr, (String) value);
		} else {
			if (value == null)
				value = "";
		}
		return value;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		try {
			Property prop = ConfigManager.getProperty(component, (String) id);
			String v = "";
			if (prop.getType() != null
					&& Property.TYPE_LIST.equalsIgnoreCase(prop.getType())) {
				String values = prop.getValues();
				String[] valueArr = values.split(";");
				v = valueArr[(Integer) value];
			} else {
				v = (String) value;
			}
			if (BeanUtil.hasPropertyName(this.object, (String) id)) {
				try {
					BeanUtil.setPropertyValue(this.object, (String) id, v);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if(this.object instanceof Simple) {
				Simple model = (Simple)object;
				model.setAttribute((String)id, (String)value);
			} else {
				List<Attr> attrs = this.object.getAttrs();
				boolean isExists = false;
				for (int i = 0; i < attrs.size(); i++) {
					Attr attr = attrs.get(i);
					if (attr.getName().equalsIgnoreCase((String) id)) {
						attr.setValue(v);
						this.object.setAttr(i, attr);
						isExists = true;
					}
				}
				if (!isExists) {
					Attr attr = new Attr();
					attr.setName((String) id);
					attr.setValue(v);
					this.object.addAttr(attr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean addPropertyDescriptor(int index, Object id,
			Vector<IPropertyDescriptor> descriptors, final Property prop) {
		if ((prop.getVisible() != null)
				&& (prop.getVisible().equalsIgnoreCase("false"))) {
			return false;
		}

		if (prop.getValues() != null && prop.getValues().indexOf(";") >= 0) {
			ComboBoxPropertyDescriptor descriptor = new ComboBoxPropertyDescriptor(
					id, formatNumber(index) + "." + prop.getName(),
					getListTypeItems(prop.getValues())) {
				public String getCategory() {
					return prop.getGroup();
				}
			};
			descriptors.add(descriptor);
		} else {
			TextPropertyDescriptor descriptor = new TextPropertyDescriptor(id,
					formatNumber(index) + "." + prop.getName()) {
				public String getCategory() {
					return prop.getGroup();
				}
			};
			if ("name".equalsIgnoreCase(prop.getName())) {
				descriptor.setValidator(new NameUniqueValidator(this.object));
			}
			descriptors.add(descriptor);
		}
		return true;
	}

	/**
	 * 对给定的value以分号;分隔返回字符串数组
	 * 
	 * @param value
	 * @return
	 */
	public String[] getListTypeItems(String value) {
		if (value == null)
			value = "";
		if (value.lastIndexOf(";") == value.length() - 1) {
			value = value.substring(0, value.length() - 1);
		}
		String[] items = value.split(";");
		if (items == null) {
			items = new String[] { "" };
		}
		return items;
	}

	/**
	 * 返回value在数组items的位置
	 * 
	 * @param items
	 * @param value
	 * @return
	 */
	private static int getItemIndex(String[] items, String value) {
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 格式化序号1->01
	 * 
	 * @param number
	 * @return
	 */
	private static String formatNumber(int number) {
		if (number < 10) {
			return "0" + String.valueOf(number);
		}
		return String.valueOf(number);
	}
}
