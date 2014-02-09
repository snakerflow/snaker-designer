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
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Process;

/**
 * 删除节点命令
 * @author yuqs
 * @version 1.0
 */
public class DeleteNodeCommand extends Command {
	private Process process;

	private NodeElement node;

	public DeleteNodeCommand(Process process, Object obj) {
		this.process = process;
		this.node = (NodeElement) obj;
	}

	public void execute() {
		this.process.removeNodeElement(this.node);
	}

	@Override
	public void undo() {
		this.process.addNodeElement(this.node);
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

}
