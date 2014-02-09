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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Field;
import org.snaker.designer.model.Form;
import org.snaker.designer.model.ModelHelper;
import org.snaker.designer.model.Task;
import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;

/**
 * field创建命令
 * @author yuqs
 * @version 1.0
 */
public class FieldCreateCommand extends Command {
	private BaseElement parent;

	private Field field;

	public FieldCreateCommand() {
		super();
		field = null;
		parent = null;
	}

	public void setField(Object o) {
		if (o instanceof Field)
			this.field = (Field) o;
	}

	public void setParent(BaseElement o) {
		this.parent = o;
	}

	public void setLayout(Rectangle rect) {
		if (this.field == null)
			return;
		// this.field.setLocation(rect.getLocation());
		// this.field.setSize(rect.getSize());
		this.field.setLayout(rect);
	}

	@Override
	public boolean canExecute() {
		if (this.field == null || this.parent == null)
			return false;
		return true;
	}

	@Override
	public boolean canUndo() {
		if (this.field == null || this.parent == null)
			return false;
		if (this.parent instanceof Form) {
			return ((Form) this.parent).getFields().contains(this.field);
		} else if (this.parent instanceof Task) {
			return ((Task) this.parent).getFields().contains(this.field);
		} else {
			return false;
		}
	}

	@Override
	public void execute() {
		if (getLabel() == null || getLabel().trim().length() == 0) {
			return;
		}
		Component component = ConfigManager.getComponent(
				ConfigManager.COMPONENT_TYPE_FIELD, getLabel());
		this.field.setType(getLabel());
		this.field.setDisplayName("新建" + component.getDisplayName());
		if (this.parent instanceof Form) {
			((Form) this.parent).addField(this.field);
		} else if (this.parent instanceof Task) {
			((Task) this.parent).addField(this.field);
		}
		this.field.setParent(this.parent);
		field.setName(ModelHelper.getModelName(field, component.getType()));
	}

	@Override
	public void undo() {
		if (this.parent instanceof Form) {
			((Form) this.parent).removeField(this.field);
		} else if (this.parent instanceof Task) {
			((Task) this.parent).removeField(this.field);
		}
	}

}
