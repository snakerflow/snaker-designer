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
package org.snaker.designer.validators;

import java.util.List;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Field;
import org.snaker.designer.model.Form;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Process;
import org.snaker.designer.model.Task;
import org.snaker.designer.model.Transition;
import org.snaker.designer.utils.StringUtils;

/**
 * 名称属性的验证器
 * @author yuqs
 * @version 1.0
 */
public class NameUniqueValidator implements ICellEditorValidator {
	private BaseElement object = null;

	public NameUniqueValidator(BaseElement object) {
		this.object = object;
	}

	@Override
	public String isValid(Object value) {
		String input = (String) value;

		boolean bRet = isModelExist(this.object, input);
		if (bRet) {
			return "指定模型的名称已存在";
		}
		String regString = "";
		String errorInfo = "";
		if ((this.object instanceof Field)) {
			regString = "[a-z][a-z][a-zA-Z0-9]*";
			errorInfo = "名字前两位必须为小写字母a-z";
		} else {
			regString = "[a-zA-Z][a-zA-Z0-9][a-zA-Z0-9]*";
			errorInfo = "名字前两位必须为字母a-z或A-Z";
		}

		if (!input.matches(regString)) {
			return "指定的名字不合法" + errorInfo;
		}

		if (isKeyValue(input)) {
			return input  + " is keywords";
		}
		return null;
	}

	private boolean isKeyValue(String input) {
		return StringUtils.isKeyValue(input);
	}

	/**
	 * 判断名称是否已存在
	 * 
	 * @param object
	 * @param value
	 * @return
	 */
	private boolean isModelExist(BaseElement object, String value) {
		if (object instanceof Field) {
			BaseElement element = object.getParent();
			if (element == null)
				return false;
			if (element instanceof Form) {
				List<Field> fields = ((Form) element).getFields();
				for (Field field : fields) {
					if (object != field && field.getName().equals(value)) {
						return true;
					}
				}
			}
			if (element instanceof Task) {
				List<Field> fields = ((Task) element).getFields();
				for (Field field : fields) {
					if (object != field && field.getName().equals(value)) {
						return true;
					}
				}
			}
		} else if (object instanceof Transition) {
			NodeElement node = ((Transition) object).getSource();
			for (Transition t : node.getOutputs()) {
				if (object != t && t.getName().equals(value)) {
					return true;
				}
			}
		} else if (object instanceof Task) {
			BaseElement process = ((Task) object).getParent();
			List<NodeElement> nodes = ((Process) process).getNodeElements();
			for (NodeElement node : nodes) {
				if (object != node && node.getName().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
}
