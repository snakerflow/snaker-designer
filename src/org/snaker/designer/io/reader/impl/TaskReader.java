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

import org.snaker.designer.io.Environment;
import org.snaker.designer.io.reader.XmlReader;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Field;
import org.snaker.designer.model.Task;
import org.snaker.designer.utils.XmlUtils;
import org.w3c.dom.Element;

/**
 * 任务节点解析类
 * @author yuqs
 * @version 1.0
 */
public class TaskReader extends SimpleReader {
	/**
	 * 由于任务节点需要解析form、assignee属性，这里覆盖抽象类方法实现
	 */
	@Override
	protected void parseNode(BaseElement node, Element element) {
		super.parseNode(node, element);
		Task task = (Task)node;
		List<Element> fields = XmlUtils.elements(element, Environment.FORM_FIELD);
		XmlReader reader = Environment.getReader(Environment.FORM_FIELD);
		for(Element fieldE : fields) {
			Field field = (Field)reader.parse(fieldE);
			task.addField(field);
		}
	}
}
