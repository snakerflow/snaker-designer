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
package org.snaker.designer.io.reader.impl;

import java.util.List;

import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.config.Property;
import org.snaker.designer.io.reader.AbstractXmlReader;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Simple;
import org.snaker.designer.utils.BeanUtil;
import org.snaker.designer.utils.ClassUtils;
import org.w3c.dom.Element;

/**
 * 简单类型的xml读取
 * @author yuqs
 * @version 1.0
 */
public class SimpleReader extends AbstractXmlReader {
	@Override
	protected BaseElement newModel(Element element) {
		String nodeName = element.getNodeName();
		Component component = ConfigManager.getComponentByName(nodeName);
		Object model = ClassUtils.newInstance(component.getClazz());
		if(model == null) return new Simple(nodeName);
		return (BaseElement)model;
	}

	@Override
	protected void parseNode(BaseElement model, Element element) {
		Component component = ConfigManager.getComponentByModel(model);
		List<Property> props = component.getProperties();
		try {
			for(Property prop : props) {
				if(BeanUtil.hasPropertyName(model, prop.getName())) {
					BeanUtil.setPropertyValue(model, prop.getName(), element.getAttribute(prop.getName()));
				} else {
					if(model instanceof Simple) {
						((Simple)model).setAttribute(prop.getName(), element.getAttribute(prop.getName()));
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
