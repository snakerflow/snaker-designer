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
package org.snaker.designer.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Field;
import org.snaker.designer.model.Form;
import org.snaker.designer.model.ModelHelper;
import org.snaker.designer.model.Task;

/**
 * field后移命令
 * @author yuqs
 * @version 1.0
 */
public class FieldBackwardCommand extends Command {
	private BaseElement parent;

	private Field model;

	public Field getModel() {
		return this.model;
	}

	public void setModel(Field model) {
		this.model = model;
	}

	public BaseElement getParent() {
		return this.parent;
	}

	public void setParent(BaseElement parent) {
		this.parent = parent;
	}

	@Override
	public void execute() {
		List<Field> list = null;
		if (this.parent instanceof Form) {
			list = ((Form) getParent()).getFields();
			int index = list.indexOf(this.model);
			if (-1 == index) {
				return;
			}
			((Form) getParent()).swapIndex(index + 1, index);
			Field before = list.get(index);
			Field after = list.get(index + 1);
			ModelHelper.exchangePlaceHolder(before, after);
		} else if (this.parent instanceof Task) {
			list = ((Task) getParent()).getFields();
			int index = list.indexOf(this.model);
			if (-1 == index) {
				return;
			}
			((Task) getParent()).swapIndex(index + 1, index);
			Field before = list.get(index);
			Field after = list.get(index + 1);
			ModelHelper.exchangePlaceHolder(before, after);
		}
	}
}
