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
package org.snaker.designer.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;

import org.snaker.designer.SnakerFlowDesignerEditor;
import org.snaker.designer.actions.ActionHelper;
import org.snaker.designer.actions.FormContextMenuProvider;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.palette.PaletteFactory;
import org.snaker.designer.parts.TaskFieldEditPartFactory;

/**
 * 任务字段设计器（与表单设计器功能一致）
 * 
 * @author yuqs
 * @version 1.0
 */
public class TaskFieldDesignerEditor extends AbstractEditorPage {
	public TaskFieldDesignerEditor(SnakerFlowDesignerEditor parent, Object model) {
		super(parent, model);
	}

	@Override
	public String getPageName() {
		return "表单定义";
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteFactory factory = new PaletteFactory();
		return factory.getPaletteRoot(ConfigManager.COMPONENT_TYPE_FIELD);
	}

	@Override
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setContents(this.model);
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setEditPartFactory(new TaskFieldEditPartFactory(
				getParentEditor()));
		ContextMenuProvider provider = new FormContextMenuProvider(viewer,
				getParentEditor().getEditActionList(), getParentEditor()
						.getActionRegistry());
		viewer.setContextMenu(provider);

		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

		((FigureCanvas) viewer.getControl())
				.setScrollBarVisibility(FigureCanvas.ALWAYS);
	}

	@Override
	protected void createActions() {
		ActionHelper.registerFormActions(getParentEditor(), getParentEditor()
				.getEditActionList(), getParentEditor().getActionRegistry());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
	}

}
