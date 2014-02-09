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

import org.eclipse.gef.requests.CreationFactory;
import org.snaker.designer.config.Component;

/**
 * 模型创建工厂
 * @author yuqs
 * @version 1.0
 */
public class ModelCreationFactory implements CreationFactory {
	/**
	 * 模型模板类型
	 */
	private Class<?> template;
	/**
	 * 模型对应的组件
	 */
	private Component component;

	/**
	 * 接收参数构造工厂对象
	 * @param component
	 * @param clazz
	 */
	public ModelCreationFactory(Component component, Class<?> clazz) {
		this.component = component;
		this.template = clazz;
	}

	/**
	 * 根据模板类型，返回模型对象
	 */
	@Override
	public Object getNewObject() {
		if (this.template == null) {
			return null;
		} else {
			if (this.template == Field.class) {
				Field field = new Field();
				field.setDisplayName(component.getDisplayName());
				field.setType(component.getType());
				return field;
			} else {
				try {
					Object model = this.template.newInstance();
					if(model instanceof Simple) {
						((Simple)model).setNodeName(component.getName());
					}
					return model;
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Object getObjectType() {
		return this.component == null ? "" : this.component.getType();
	}
}
