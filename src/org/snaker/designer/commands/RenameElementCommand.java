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

import org.eclipse.gef.commands.Command;

import org.snaker.designer.model.BaseElement;

/**
 * 重命名元素名稱的命令
 * 
 * @author yuqs
 * @version 1.0
 */
public class RenameElementCommand extends Command {
	private BaseElement source;
	private String name;
	private String oldName;

	public void execute() {
		if (source != null) {
			source.setDisplayName(name);
		}
	}

	public void setName(String string) {
		name = string;
	}

	public void setOldName(String string) {
		oldName = string;
	}

	public void setSource(BaseElement source) {
		this.source = source;
	}

	public void undo() {
		if (source != null) {
			source.setDisplayName(oldName);
		}
	}
}
