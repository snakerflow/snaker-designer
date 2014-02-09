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
package org.snaker.designer;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;

/**
 * 多页面命令监听器
 * @author yuqs
 * @version 1.0
 */
public class MultiPageCommandStackListener implements CommandStackListener {
	private List<CommandStack> commandStacks = new ArrayList<CommandStack>(2);

	private SnakerFlowDesignerEditor editor;

	public MultiPageCommandStackListener(SnakerFlowDesignerEditor editor) {
		this.editor = editor;
	}

	public void addCommandStack(CommandStack commandStack) {
		this.commandStacks.add(commandStack);
		commandStack.addCommandStackListener(this);
	}

	public void commandStackChanged(EventObject event) {
		if (((CommandStack) event.getSource()).isDirty()) {
			this.editor.setDirty(true);
		} else {
			boolean oneIsDirty = false;
			for (Iterator<CommandStack> stacks = this.commandStacks.iterator(); stacks
					.hasNext();) {
				CommandStack stack = (CommandStack) stacks.next();
				if (!stack.isDirty()) {
					continue;
				}
				oneIsDirty = true;
				break;
			}

			this.editor.setDirty(oneIsDirty);
		}
	}

	public void dispose() {
		for (Iterator<CommandStack> stacks = this.commandStacks.iterator(); stacks
				.hasNext();) {
			((CommandStack) stacks.next()).removeCommandStackListener(this);
		}
		this.commandStacks.clear();
	}

	public void markSaveLocations() {
		for (Iterator<CommandStack> stacks = this.commandStacks.iterator(); stacks
				.hasNext();) {
			CommandStack stack = (CommandStack) stacks.next();
			stack.markSaveLocation();
		}
	}
}
