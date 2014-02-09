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
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import org.snaker.designer.commands.FieldCreateCommand;
import org.snaker.designer.model.BaseElement;

/**
 * Form模型的编辑策略
 * @author yuqs
 * @version 1.0
 */
public class FormXYEditLayoutPolicy extends XYLayoutEditPolicy {
	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getType() == REQ_CREATE) {
			String newObjectType = (String) request.getNewObjectType();
			FieldCreateCommand cmd = new FieldCreateCommand();
			cmd.setParent((BaseElement) getHost().getModel());
			cmd.setField(request.getNewObject());
			cmd.setLabel(newObjectType);

			Rectangle constraint = (Rectangle) getConstraintFor(request);
			constraint.x = (constraint.x < 0) ? 0 : constraint.x;
			constraint.y = (constraint.y < 0) ? 0 : constraint.y;
			constraint.width = (constraint.width <= 0) ? 120 : constraint.width;
			constraint.height = (constraint.height <= 0) ? 40
					: constraint.height;
			cmd.setLayout(constraint);
			return cmd;
		}
		return null;
	}

}
