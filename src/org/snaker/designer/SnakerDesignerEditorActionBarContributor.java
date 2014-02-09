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
package org.snaker.designer;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

/**
 * Snaker设计器的工具栏，命令菜单构造器
 * @author yuqs
 * @version 1.0
 */
public class SnakerDesignerEditorActionBarContributor extends
		ActionBarContributor {
	@Override
	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
		addRetargetAction(new RetargetAction(
				GEFActionConstants.TOGGLE_GRID_VISIBILITY, "网格",
				IAction.AS_CHECK_BOX));
		addRetargetAction(new RetargetAction(
				GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY, "几何对齐",
				IAction.AS_CHECK_BOX));
	}

	@Override
	protected void declareGlobalActionKeys() {

	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
		toolBarManager.add(new Separator());
		toolBarManager
				.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
		toolBarManager
				.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));
	}
}
