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

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.snaker.designer.actions.ActionHelper;
import org.snaker.designer.editors.AbstractEditorPage;
import org.snaker.designer.editors.ProcessDesignerEditor;
import org.snaker.designer.editors.TaskFieldDesignerEditor;
import org.snaker.designer.model.BaseElement;

/**
 * Snaker流程设计器（多页面设计）
 * 活动页面为流程设计器、双击流程的任务节点，可打开表单设计器
 * @author yuqs
 * @version 1.0
 */
public class SnakerFlowDesignerEditor extends MultiPageEditorPart implements
		IAdaptable {
	public static final String ID = "org.snaker.designer.SnakerFlowDesignerEditor";

	private int pageId = -1;

	private int formEditPageID = -1;

	private List<String> editPartActionIDs = new ArrayList<String>();

	private List<String> stackActionIDs = new ArrayList<String>();

	private List<String> editorActionIDs = new ArrayList<String>();

	private boolean isDirty = false;

	private MultiPageCommandStackListener multiPageCommandStackListener;

	public BaseElement model = null;

	private SelectionSynchronizer synchronizer;

	private ActionRegistry actionRegistry;

	private CommandStackListener delegatingCommandStackListener = new CommandStackListener() {
		public void commandStackChanged(EventObject event) {
			SnakerFlowDesignerEditor.this
					.updateActions(SnakerFlowDesignerEditor.this.stackActionIDs);
		}
	};

	private ISelectionListener selectionListener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			SnakerFlowDesignerEditor.this
					.updateActions(SnakerFlowDesignerEditor.this.editPartActionIDs);
		}
	};

	public SnakerFlowDesignerEditor() {
	}

	@Override
	protected void createPages() {
		try {
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			int page = addPage(new ProcessDesignerEditor(this, file),
					getEditorInput());
			setProcessDesignerPage(page);
			setPageText(page, getProcessDesignerPage().getPageName());
			getMultiPageCommandStackListener().addCommandStack(
					getProcessDesignerPage().getCommandStack());
			setActivePage(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSaveAs() {
		getProcessDesignerPage().doSaveAs();
		getMultiPageCommandStackListener().markSaveLocations();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			getProcessDesignerPage().doSave(monitor);
			getMultiPageCommandStackListener().markSaveLocations();
		} catch(RuntimeException e) {
			setDirty(true);
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void setActivePage(int pageIndex) {
		super.setActivePage(pageIndex);

		currentPageChanged();
	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);

		currentPageChanged();
	}

	protected void currentPageChanged() {
		if (getProcessDesignerPage().getPageName().equalsIgnoreCase(
				getCurrentPage().getPageName())) {
			removeFormEditPage();
		}
	}

	public void setFormEditPageID(int editPageID) {
		this.formEditPageID = editPageID;
	}

	public int getFormEditPageID() {
		return this.formEditPageID;
	}

	public List<String> getEditActionList() {
		return this.editPartActionIDs;
	}

	public void registerAction(SelectionAction action) {
		getActionRegistry().registerAction(action);
		this.editPartActionIDs.add(action.getId());
	}

	public void removeFormEditPage() {
		if (-1 == getFormEditPageID()) {
			return;
		}
		removePage(getFormEditPageID());
		setFormEditPageID(-1);
	}

	public AbstractEditorPage getFormEditPage() {
		return (AbstractEditorPage) getEditor(this.formEditPageID);
	}

	public void createFormEditPage(Object obj) {
		try {
			int formPageId = addPage(new TaskFieldDesignerEditor(this, obj),
					getEditorInput());
			setFormEditPageID(formPageId);
			setPageText(formPageId, getFormEditPage().getPageName());
			getMultiPageCommandStackListener().addCommandStack(
					getFormEditPage().getCommandStack());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDirty(boolean dirty) {
		if (this.isDirty != dirty) {
			this.isDirty = dirty;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	public boolean isDirty() {
		return this.isDirty;
	}

	private void setProcessDesignerPage(int id) {
		this.pageId = id;
	}

	protected void createActions() {
		ActionHelper.createCommonAction(this, this.editPartActionIDs,
				getActionRegistry());
	}

	private void updateActions(List<String> actionIds) {
		for (Iterator<String> ids = actionIds.iterator(); ids.hasNext();) {
			IAction action = getActionRegistry().getAction(ids.next());
			if (action == null) {
				continue;
			}
			if (!(action instanceof UpdateAction)) {
				continue;
			}
			((UpdateAction) action).update();
		}
	}

	protected ISelectionListener getSelectionListener() {
		return this.selectionListener;
	}

	protected void firePropertyChange(int propertyId) {
		super.firePropertyChange(propertyId);
		updateActions(this.editorActionIDs);
	}

	private ProcessDesignerEditor getProcessDesignerPage() {
		return (ProcessDesignerEditor) getEditor(this.pageId);
	}

	protected CommandStackListener getDelegatingCommandStackListener() {
		return this.delegatingCommandStackListener;
	}

	public MultiPageCommandStackListener getMultiPageCommandStackListener() {
		if (this.multiPageCommandStackListener == null) {
			this.multiPageCommandStackListener = new MultiPageCommandStackListener(
					this);
		}
		return this.multiPageCommandStackListener;
	}

	public AbstractEditorPage getCurrentPage() {
		if (getActivePage() == -1) {
			return null;
		}
		return (AbstractEditorPage) getEditor(getActivePage());
	}

	public SelectionSynchronizer getSelectionSynchronizer() {
		if (this.synchronizer == null)
			this.synchronizer = new SelectionSynchronizer();
		return this.synchronizer;
	}

	public ActionRegistry getActionRegistry() {
		if (this.actionRegistry == null) {
			this.actionRegistry = new ActionRegistry();
		}
		return this.actionRegistry;
	}

	public void setActionRegistry(ActionRegistry actionRegistry) {
		this.actionRegistry = actionRegistry;
	}

	@Override
	public void dispose() {
		getMultiPageCommandStackListener().dispose();

		getSite().getWorkbenchWindow().getSelectionService()
				.removeSelectionListener(getSelectionListener());

		getActionRegistry().dispose();

		super.dispose();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);

		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(getSelectionListener());

		createActions();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return super.getAdapter(adapter);
	}

}