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
import java.util.Collections;
import java.util.List;

/**
 * 表单form模型
 * @author yuqs
 * @version 1.0
 */
public class Form extends BaseElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1933407324643767583L;

	public static final String PROP_FORM = "Form";

	public static final String PROP_LIST = "FormFields";
	/**
	 * 表单的字段列表
	 */
	private List<Field> fields = new ArrayList<Field>();

	public void addField(Field field) {
		fields.add(field);
		fireStructureChange(PROP_FORM, fields);
	}

	public void removeField(Field field) {
		fields.remove(field);
		fireStructureChange(PROP_FORM, fields);
	}

	public void swapIndex(int i, int j) {
		Collections.swap(fields, i, j);
		fireStructureChange(PROP_LIST, fields);
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
