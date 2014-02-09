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
 * 重连目标节点命令
 * 
 * @author yuqs
 * @version 1.0
 */
public class ReconnectTargetCommand extends Command {
	/**
	 * 源节点
	 */
	protected NodeElement source;
	/**
	 * 目标节点
	 */
	protected NodeElement target;
	/**
	 * 变迁
	 */
	protected Transition transition;
	/**
	 * 未重连前的目标节点
	 */
	protected NodeElement oldTarget;

	public boolean canExecute() {
		if (transition.getSource().equals(target))
			return false;

		List<Transition> transitions = source.getOutputs();
		for (int i = 0; i < transitions.size(); i++) {
			Transition trans = ((Transition) (transitions.get(i)));
			if (trans.getTarget().equals(target)
					&& !trans.getTarget().equals(oldTarget))
				return false;
		}
		return true;
	}

	public void execute() {
		if (target != null) {
			oldTarget.removeInput(transition);
			transition.setTarget(target);
			target.addInput(transition);
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
		source = trans.getSource();
		oldTarget = trans.getTarget();
	}

	public void undo() {
		target.removeInput(transition);
		transition.setTarget(oldTarget);
		oldTarget.addInput(transition);
	}

}
