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

import org.snaker.designer.model.*;

/**
 * 重新连接源节点命令
 * 
 * @author yuqs
 * @version 1.0
 */
public class ReconnectSourceCommand extends Command {
	/**
	 * 源节点
	 */
	protected NodeElement source;
	/**
	 * 目标节点
	 */
	protected NodeElement target;
	/**
	 * 连接变迁
	 */
	protected Transition transition;
	/**
	 * 未重新连接前的源节点
	 */
	protected NodeElement oldSource;

	/**
	 * 判断是否能够执行
	 */
	public boolean canExecute() {
		if (transition.getTarget().equals(source)) {
			return false;
		}

		List<Transition> transitions = source.getOutputs();
		for (int i = 0; i < transitions.size(); i++) {
			Transition trans = ((Transition) (transitions.get(i)));
			if (trans.getTarget().equals(target)
					&& !trans.getTarget().equals(oldSource))
				return false;
		}
		return true;
	}

	/**
	 * 执行过程： 1、未重连之前的源节点删除变迁 2、设置变迁的新源节点 3、设置变迁的父元素 4、向新源节点添加变迁
	 */
	public void execute() {
		if (source != null) {
			oldSource.removeOutput(transition);
			transition.setSource(source);
			transition.setParent(source.getParent());
			source.addOutput(transition);
		}
	}

	public NodeElement getSource() {
		return source;
	}

	public NodeElement getTarget() {
		return target;
	}

	public Transition getTransition() {
		return transition;
	}

	public void setSource(NodeElement activity) {
		source = activity;
	}

	public void setTarget(NodeElement activity) {
		target = activity;
	}

	public void setTransition(Transition trans) {
		transition = trans;
		target = trans.getTarget();
		oldSource = trans.getSource();
	}

	public void undo() {
		source.removeOutput(transition);
		transition.setSource(oldSource);
		oldSource.addOutput(transition);
	}

}
