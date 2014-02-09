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
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.snaker.designer.SnakerFlowDesignerEditor;

/**
 * 抽象设计器页面
 * @author yuqs
 * @version 1.0
 */
public abstract class AbstractEditorPage extends GraphicalEditorWithPalette {
	private final SnakerFlowDesignerEditor parent;

	protected Object model = null;

	public AbstractEditorPage(SnakerFlowDesignerEditor parent) {
		this.parent = parent;
	}

	public AbstractEditorPage(SnakerFlowDesignerEditor parent, Object model) {
		this.parent = parent;
		this.model = model;
		DefaultEditDomain defaultEditDomain = new DefaultEditDomain(this);
		setEditDomain(defaultEditDomain);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getParentEditor().doSave(monitor);
	}

	public final void doSaveAs() {
		getParentEditor().doSaveAs();
	}

	public final boolean isDirty() {
		return getParentEditor().isDirty();
	}

	public final CommandStack getCommandStack() {
		return getEditDomain().getCommandStack();
	}

	public final boolean isSaveAsAllowed() {
		return getParentEditor().isSaveAsAllowed();
	}

	public abstract String getPageName();

	protected final SnakerFlowDesignerEditor getParentEditor() {
		return this.parent;
	}

	protected void registerEditPartViewer(EditPartViewer viewer) {
		getEditDomain().addViewer(viewer);

		getParentEditor().getSelectionSynchronizer().addViewer(viewer);

		getSite().setSelectionProvider(viewer);
	}

	public PropertySheetPage getPropertySheetPage() {
		return null;
	}

	public DefaultEditDomain getEditDomain() {
		return super.getEditDomain();
	}

	public GraphicalViewer getGraphicalViewer() {
		return super.getGraphicalViewer();
	}

	/**
	 * 产生Actions
	 */
	protected void createActions() {
		super.createActions();
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
}
