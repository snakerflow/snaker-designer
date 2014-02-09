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

import org.snaker.designer.model.Attr;
import org.snaker.designer.model.Field;

/**
 * 换行设置命令
 * @author yuqs
 * @version 1.0
 */
public class LineBRSetCommand extends Command {
	private Field model;

	public Field getModel() {
		return this.model;
	}

	public void setModel(Field model) {
		this.model = model;
	}

	public void execute() {
		List<Attr> attrs = this.model.getAttrs();
		boolean isExists = false;
		for (int i = 0; i < attrs.size(); i++) {
			Attr attr = (Attr) attrs.get(i);
			if (!"lineBR".equalsIgnoreCase(attr.getName())) {
				continue;
			}
			isExists = true;
			if ("true".equalsIgnoreCase(attr.getValue())) {
				return;
			}
			attr.setValue("true");
			this.model.setAttr(i, attr);
			return;
		}

		if (!isExists) {
			Attr attr = new Attr();
			attr.setName("lineBR");
			attr.setValue("true");
			this.model.addAttr(attr);
		}
	}
}
