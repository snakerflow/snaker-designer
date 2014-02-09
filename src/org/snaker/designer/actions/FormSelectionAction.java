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
package org.snaker.designer.actions;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * form选择action
 * @author yuqs
 * @version 1.0
 */
public class FormSelectionAction extends SelectionAction {
	private String commandType;

	public FormSelectionAction(IWorkbenchPart part, String commandType) {
		super(part);
		this.commandType = commandType;
		setText(getCommandType());
		setToolTipText(getCommandType());
		setId(commandType);
	}

	@Override
	protected boolean calculateEnabled() {
		Command cmd = createFormDesignerCommand(getSelectedObjects());
		if (cmd == null) {
			return false;
		}
		return cmd.canExecute();
	}

	protected boolean isAgree(Object model) {
		return true;
	}

	protected Command createFormDesignerCommand(List<?> selectedEditParts) {
		if (selectedEditParts.isEmpty()) {
			return null;
		}
		if (!(selectedEditParts.get(0) instanceof EditPart)) {
			return null;
		}
		Request request = new Request(getCommandType());
		CompoundCommand compoundCmd = new CompoundCommand(getCommandType());

		for (int i = 0; i < selectedEditParts.size(); i++) {
			EditPart editPart = (EditPart) selectedEditParts.get(i);
			if (!editPart.understandsRequest(request)) {
				continue;
			}
			if (!isAgree(editPart.getModel())) {
				continue;
			}
			Command cmd = editPart.getCommand(request);
			if (cmd == null) {
				continue;
			}
			compoundCmd.add(cmd);
		}

		return compoundCmd;
	}

	@Override
	public void run() {
		execute(createFormDesignerCommand(getSelectedObjects()));
	}

	@Override
	protected void init() {
		super.init();
		setText(getCommandType());
		setToolTipText(getCommandType());
		setId(getCommandType());
		setEnabled(false);
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
