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

import org.snaker.designer.io.Environment;
import org.snaker.designer.io.writer.XmlWriter;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Field;
import org.snaker.designer.model.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Task节点的xml输出
 * @author yuqs
 * @version 1.0
 */
public class TaskWriter extends SimpleWriter {
	@Override
	protected void addChildNodes(BaseElement model, Document doc, Element element) {
		Task task = (Task)model;
		XmlWriter writer = Environment.getWriter(Environment.FORM_FIELD);
		for(Field field : task.getFields()) {
			writer.writer(element, field);
		}
	}
}
