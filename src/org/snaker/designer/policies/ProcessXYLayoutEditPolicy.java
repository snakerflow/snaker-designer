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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.snaker.designer.commands.ChangeConstraintCommand;
import org.snaker.designer.commands.CreateNodeCommand;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Process;

/**
 * Process模型编辑策略
 * @author yuqs
 * @version 1.0
 */
public class ProcessXYLayoutEditPolicy extends XYLayoutEditPolicy {
	@Override
	public Command getCommand(Request request) {
		return super.getCommand(request);
	}

	@Override
	protected Command createAddCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint) {
		return super.createAddCommand(request, child, constraint);
	}

	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		return new ChangeConstraintCommand(child.getModel(),
				(Rectangle) constraint);
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		if ((request.getNewObject() instanceof NodeElement)) {
			NodeElement node = (NodeElement) request.getNewObject();
			node.setLayout(constraint);
		}
		return new CreateNodeCommand((Process) getHost().getModel(),
				request.getNewObject());
	}

	@Override
	public boolean understandsRequest(Request req) {
		return true;
	}
}
