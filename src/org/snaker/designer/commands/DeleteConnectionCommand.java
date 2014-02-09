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
import org.snaker.designer.model.Transition;

/**
 * 连接删除命令
 * 
 * @author yuqs
 * @version 1.0
 */
public class DeleteConnectionCommand extends Command {
	/**
	 * 源节点
	 */
	private NodeElement source;
	/**
	 * 目标节点
	 */
	private NodeElement target;
	/**
	 * 变迁
	 */
	private Transition transition;

	public void execute() {
		source.removeOutput(transition);
		target.removeInput(transition);
		transition.setSource(null);
		transition.setTarget(null);
	}

	public void setSource(NodeElement activity) {
		source = activity;
	}

	public void setTarget(NodeElement activity) {
		target = activity;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

	public void undo() {
		transition.setSource(source);
		transition.setTarget(target);
		source.addOutput(transition);
		target.addInput(transition);
	}

}
