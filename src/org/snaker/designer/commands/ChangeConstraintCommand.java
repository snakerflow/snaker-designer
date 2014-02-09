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
import org.snaker.designer.model.NodeElement;

/**
 * 约束改变命令
 * @author yuqs
 * @version 1.0
 */
public class ChangeConstraintCommand extends Command {
	private Object obj;
	private Rectangle oldConstraint;
	private Rectangle newConstraint;

	public ChangeConstraintCommand(Object element, Rectangle newConstraint) {
		this.newConstraint = newConstraint;
		this.obj = element;
	}

	public void execute() {
		if ((this.obj instanceof NodeElement)) {
			this.oldConstraint = ((NodeElement) this.obj).getLayout();
			((NodeElement) this.obj).setLayout(newConstraint);
		}
	}

	public void redo() {
		if ((this.obj instanceof NodeElement)) {
			((NodeElement) this.obj).setLayout(newConstraint);
		}
	}

	public void undo() {
		if ((this.obj instanceof NodeElement)) {
			((NodeElement) this.obj).setLayout(oldConstraint);
		}
	}
}
