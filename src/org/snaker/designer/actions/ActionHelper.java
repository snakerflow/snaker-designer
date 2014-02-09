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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorPart;

import org.snaker.designer.commands.CommandHelper;

/**
 * action帮助类
 * @author yuqs
 * @version 1.0
 */
public class ActionHelper {
	private static List<IAction> formActions = new ArrayList<IAction>();

	private static void loadFormActions(IWorkbenchPart part) {
		if (!formActions.isEmpty()) {
			return;
		}
		formActions.add(new FieldDeleteAction(part,
				CommandHelper.REQUEST_TYPE_DELETE));

		formActions.add(new PlaceHolderIncreaseAction(part,
				CommandHelper.REQUEST_TYPE_ADDPLACEHOLDER));
		formActions.add(new PlaceHolderDecreaseAction(part,
				CommandHelper.REQUEST_TYPE_DELPLACEHOLDER));

		formActions.add(new LineBRSetAction(part,
				CommandHelper.REQUEST_TYPE_SETLINEBR));
		formActions.add(new LineBRCancelAction(part,
				CommandHelper.REQUEST_TYPE_CANCELLINEBR));

		formActions.add(new FieldForwardAction(part,
				CommandHelper.REQUEST_TYPE_FORWARD));
		formActions.add(new FieldBackwardAction(part,
				CommandHelper.REQUEST_TYPE_BACKWARD));
	}

	public static void createCommonAction(EditorPart part,
			List<String> actions, ActionRegistry registry) {
		IAction action = null;
		action = new UndoAction(part);
		registry.registerAction(action);
		actions.add(action.getId());

		action = new RedoAction(part);
		registry.registerAction(action);
		actions.add(action.getId());

		action = new DeleteAction(part.getEditorSite().getPart());
		registry.registerAction(action);
		actions.add(action.getId());
	}

	public static void buildContextMenu(IMenuManager menu,
			List<String> actionIds, ActionRegistry actionRegistry) {
		for (String id : actionIds) {
			IAction action = actionRegistry.getAction(id);
			if (action != null && action.isEnabled()) {
				menu.appendToGroup("group.add", action);
			}
		}
	}

	public static void registerFormActions(IWorkbenchPart part,
			List<String> actionIds, ActionRegistry actionRegistry) {
		loadFormActions(part);
		if (formActions == null || formActions.isEmpty()) {
			return;
		}
		for (IAction action : formActions) {
			actionRegistry.registerAction(action);
			actionIds.add(action.getId());
		}
	}
}
