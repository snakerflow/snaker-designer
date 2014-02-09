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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import org.snaker.designer.commands.RenameElementCommand;
import org.snaker.designer.figures.AbstractElementFigure;
import org.snaker.designer.model.BaseElement;

/**
 * 元素直接编辑策略
 * @author yuqs
 * @version 1.0
 */
public class ElementDirectEditPolicy extends DirectEditPolicy {
	protected Command getDirectEditCommand(DirectEditRequest request) {
		RenameElementCommand cmd = new RenameElementCommand();
		cmd.setSource((BaseElement) getHost().getModel());
		cmd.setOldName(((BaseElement) getHost().getModel()).getDisplayName());
		cmd.setName((String) request.getCellEditor().getValue());
		return cmd;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		IFigure figure = getHostFigure();
		if (figure instanceof Label) {
			((Label) figure).setText(value);
		} else if (figure instanceof AbstractElementFigure) {
			((AbstractElementFigure) figure).setText(value);
		}
	}

}
