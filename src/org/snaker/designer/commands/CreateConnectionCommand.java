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
import org.snaker.designer.model.End;
import org.snaker.designer.model.ModelHelper;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Start;
import org.snaker.designer.model.Transition;

/**
 * 创建连接命令
 * 
 * @author yuqs
 * @version 1.0
 */
public class CreateConnectionCommand extends Command {
	/**
	 * 连接的源节点
	 */
	private NodeElement source;
	/**
	 * 连接的目标节点
	 */
	private NodeElement target;
	/**
	 * 连接对象
	 */
	private Transition connection;

	public Object point;

	public boolean canExecute() {
		if (source.equals(target))
			return false;
		if (source != null && source instanceof End) {
			return false;
		}

		if (target != null && target instanceof Start) {
			return false;
		}

		List<Transition> transistions = source.getOutputs();
		for (int i = 0; i < transistions.size(); i++) {
			if (((Transition) transistions.get(i)).getTarget().equals(target))
				return false;
		}
		return true;
	}

	public void execute() {
		this.target.addInput(this.connection);
		this.source.addOutput(this.connection);
		this.connection.setSource(this.source);
		this.connection.setTarget(this.target);
		this.connection.setParent(this.source.getParent());
		String name = ModelHelper.getModelName(this.connection);
		this.connection.setName(name);
		/*
		 * displayName默认不显示
		 */
		//this.connection.setDisplayName("to " + this.target.getDisplayName());
	}

	public void redo() {
		execute();
	}

	public void undo() {
		this.source.removeOutput(this.connection);
		this.target.removeInput(this.connection);
	}

	public void setTarget(NodeElement target) {
		this.target = target;
	}

	public void setTransition(Transition r) {
		this.connection = r;
	}

	public Transition getTransition() {
		return this.connection;
	}

	public void setSource(NodeElement source) {
		this.source = source;
		if (this.connection != null) {
			this.connection.setSource(this.source);
		}
	}
}
