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

import org.snaker.designer.model.*;
import org.snaker.designer.model.Process;

/**
 * 在process容器中创建task命令
 * 
 * @author Administrator
 * 
 */
public class CreateNodeCommand extends Command {
	/**
	 * 保持process容器对象
	 */
	private Process process;
	/**
	 * 创建的task对象
	 */
	private NodeElement node;

	public CreateNodeCommand(Process process, Object obj) {
		this.process = process;
		this.node = (NodeElement) obj;
	}

	/**
	 * 执行过程 1、向容器中添加task 2、设置task图形的名称、显示名称
	 */
	public void execute() {
		this.process.addNodeElement(this.node);
		this.node.setParent(process);
		String nodeName = ModelHelper.getModelName(this.node);
		this.node.setName(nodeName);
		this.node.setDisplayName(nodeName);
	}

	@Override
	public void undo() {
		this.process.removeNodeElement(node);
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		if (this.process == null || this.node == null) {
			return false;
		}
		return this.process.getNodeElements().contains(this.node);
	}

}
