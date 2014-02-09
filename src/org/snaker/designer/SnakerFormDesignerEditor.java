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

import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.snaker.designer.actions.ActionHelper;
import org.snaker.designer.actions.FormContextMenuProvider;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.io.Environment;
import org.snaker.designer.model.Form;
import org.snaker.designer.palette.PaletteFactory;
import org.snaker.designer.parts.FormDesignerEditPartFactory;

/**
 * Snaker表单设计器（单页面设计） 该设计器一般作为自定义单个表单时使用，不涉及流程逻辑
 * 
 * @author yuqs
 * @version 1.0
 */
public class SnakerFormDesignerEditor extends GraphicalEditorWithPalette
		implements IAdaptable {
	private boolean isDirty = false;

	private Form model;

	private List<String> actionIds = new ArrayList<String>();

	public SnakerFormDesignerEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		IFile file = ((IFileEditorInput) input).getFile();
		try {
			File location = new File(file.getLocationURI());
			model = Environment.readerForm(location.getAbsolutePath());
		} catch (Exception e) {
			model = new Form();
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			File location = new File(file.getLocationURI());
			Environment.writer((Form) this.model, location.getAbsolutePath());
			getCommandStack().markSaveLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteFactory factory = new PaletteFactory();
		return factory.getPaletteRoot(ConfigManager.COMPONENT_TYPE_FIELD);
	}

	@Override
	protected void initializeGraphicalViewer() {
		if (model == null) {
			model = new Form();
		}
		getGraphicalViewer().setContents(this.model);
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setEditPartFactory(new FormDesignerEditPartFactory(this));
		ContextMenuProvider provider = new FormContextMenuProvider(viewer,
				actionIds, getActionRegistry());
		viewer.setContextMenu(provider);

		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

		((FigureCanvas) viewer.getControl())
				.setScrollBarVisibility(FigureCanvas.ALWAYS);
	}

	@Override
	public void commandStackChanged(EventObject event) {
		if (((CommandStack) event.getSource()).isDirty()) {
			setDirty(true);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} else {
			setDirty(false);
		}
		super.commandStackChanged(event);
	}

	@Override
	public SelectionSynchronizer getSelectionSynchronizer() {
		return super.getSelectionSynchronizer();
	}

	@Override
	public ActionRegistry getActionRegistry() {
		return super.getActionRegistry();
	}

	public Object getModel() {
		return this.model;
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean dirty) {
		if (this.isDirty != dirty) {
			this.isDirty = dirty;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		super.createActions();
		ActionHelper.registerFormActions(this, actionIds, getActionRegistry());
		for (String actionId : actionIds) {
			getSelectionActions().add(actionId);
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part.getSite().getWorkbenchWindow().getActivePage() == null) {
			return;
		}
		super.selectionChanged(part, selection);
	}
}
