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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.snaker.designer.commands.CreateConnectionCommand;
import org.snaker.designer.commands.ReconnectSourceCommand;
import org.snaker.designer.commands.ReconnectTargetCommand;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Transition;

/**
 * 图形节点的编辑策略
 * @author yuqs
 * @version 1.0
 */
public class ProcessGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		NodeElement target = getNodeElement();
		CreateConnectionCommand command = (CreateConnectionCommand) request
				.getStartCommand();
		command.setTarget(target);
		return command;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		NodeElement source = getNodeElement();
		CreateConnectionCommand command = new CreateConnectionCommand();
		command.setTransition((Transition) request.getNewObject());
		command.setSource(source);
		command.point = request.getLocation();
		request.setStartCommand(command);
		return command;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ReconnectSourceCommand cmd = new ReconnectSourceCommand();
		cmd.setTransition((Transition) request.getConnectionEditPart()
				.getModel());
		cmd.setSource(getNodeElement());
		return cmd;
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ReconnectTargetCommand cmd = new ReconnectTargetCommand();
		cmd.setTransition((Transition) request.getConnectionEditPart()
				.getModel());
		cmd.setTarget(getNodeElement());
		return cmd;
	}

	protected NodeElement getNodeElement() {
		return (NodeElement) getHost().getModel();
	}
}
