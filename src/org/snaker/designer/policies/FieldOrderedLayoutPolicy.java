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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import org.snaker.designer.commands.CommandHelper;

/**
 * Field排序策略
 * @author yuqs
 * @version 1.0
 */
public class FieldOrderedLayoutPolicy extends OrderedLayoutEditPolicy {
	@Override
	public Command getCommand(Request request) {
		Object model = getHost().getModel();
		Object parent = getHost().getParent().getModel();
		Command cmd = CommandHelper.getCommand(request, model, parent);
		if (cmd != null) {
			return cmd;
		}
		return super.getCommand(request);
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

	@Override
	public boolean understandsRequest(Request request) {
		if (CommandHelper.understandCommand(request, getHost().getModel())) {
			return true;
		}
		return super.understandsRequest(request);
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		return null;
	}

	@Override
	protected EditPart getInsertionReference(Request request) {
		return null;
	}
}
