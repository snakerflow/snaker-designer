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
package org.snaker.designer.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.snaker.designer.commands.DeleteNodeCommand;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Process;

/**
 * 节点组件编辑策略
 * @author yuqs
 * @version 1.0
 */
public class NodeComponentEditPolicy extends ComponentEditPolicy {
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Command command = null;
		if ((getHost().getModel() instanceof NodeElement)) {
			command = new DeleteNodeCommand((Process) getHost().getParent()
					.getModel(), (NodeElement) getHost().getModel());
		}

		return command;
	}

	protected BaseElement getModel() {
		return (BaseElement) getHost().getModel();
	}

	@Override
	public Command getCommand(Request request) {
		return super.getCommand(request);
	}

	@Override
	public boolean understandsRequest(Request request) {
		return true;
	}
}
