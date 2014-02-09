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
package org.snaker.designer.io.writer.impl;

import java.util.List;
import java.util.Map;

import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.config.Property;
import org.snaker.designer.io.writer.AbstractXmlWriter;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Simple;
import org.snaker.designer.utils.BeanUtil;
import org.snaker.designer.utils.StringUtils;
import org.w3c.dom.Element;

/**
 * 简单的xml输出
 * @author yuqs
 * @version 1.0
 */
public class SimpleWriter extends AbstractXmlWriter {
	@Override
	protected void addAttributes(BaseElement model, Element element) {
		Component component = ConfigManager.getComponentByModel(model);
		List<Property> props = component.getProperties();
		try {
			for(Property prop : props) {
				if(BeanUtil.hasPropertyName(model, prop.getName())) {
					String value = (String)BeanUtil.getPropertyValue(model, prop.getName());
					if(StringUtils.isNotEmpty(value)) {
						element.setAttribute(prop.getName(), value);
					}
				}
			}
			if(model instanceof Simple) {
				for(Map.Entry<String, String> entry : ((Simple)model).getAttributes().entrySet()) {
					element.setAttribute(entry.getKey(), entry.getValue());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
