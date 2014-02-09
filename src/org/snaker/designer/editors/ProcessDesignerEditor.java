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

import java.io.File;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.snaker.designer.SnakerFlowDesignerEditor;
import org.snaker.designer.actions.ProcessContextMenuProvider;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.io.Environment;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Process;
import org.snaker.designer.palette.PaletteFactory;
import org.snaker.designer.parts.DesignerEditPartFactory;
import org.snaker.designer.utils.StringUtils;

/**
 * 流程设计器
 * @author yuqs
 * @version 1.0
 */
public class ProcessDesignerEditor extends AbstractEditorPage {
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		try {
			IFile file = ((IFileEditorInput) input).getFile();
			this.model = create(file);
			if (this.model == null) {
				throw new PartInitException(
						"The specified input is not a valid network.");
			}
		} catch (CoreException e) {
			throw new PartInitException(e.getStatus());
		} catch (ClassCastException e) {
			throw new PartInitException(
					"The specified input is not a valid network.", e);
		}

		setSite(site);
		setInput(input);
	}

	public ProcessDesignerEditor(SnakerFlowDesignerEditor parent, IFile file) {
		super(parent);
		try {
			parent.model = ((BaseElement) this.model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DefaultEditDomain defaultEditDomain = new DefaultEditDomain(this);
		setEditDomain(defaultEditDomain);
	}

	private Process create(IFile file) throws CoreException {
		Process process = null;

		try {
			File location = new File(file.getLocationURI());
			process = Environment.readerProcess(location.getAbsolutePath());
		} catch (Exception e) {
			process = new Process();
		}
		return process;
	}

	@Override
	public String getPageName() {
		return "流程定义";
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		try {
			PaletteFactory factory = new PaletteFactory();
			PaletteRoot root = factory
					.getPaletteRoot(ConfigManager.COMPONENT_TYPE_PROCESS);
			return root;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setContents(this.model);
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();

		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setRootEditPart(rootEditPart);
		getGraphicalViewer().setEditPartFactory(
				new DesignerEditPartFactory(getParentEditor()));
		configureEditPartViewer(viewer);
		((FigureCanvas) viewer.getControl())
				.setScrollBarVisibility(FigureCanvas.ALWAYS);
		getActionRegistry().registerAction(
				new ToggleGridAction(getGraphicalViewer()));
		getActionRegistry().registerAction(
				new ToggleSnapToGeometryAction(getGraphicalViewer()));
	}

	protected void configureEditPartViewer(EditPartViewer viewer) {
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		getGraphicalViewer().setContextMenu(
				new ProcessContextMenuProvider(getParentEditor()
						.getActionRegistry(), getGraphicalViewer()));

		((FigureCanvas) viewer.getControl())
				.setScrollBarVisibility(FigureCanvas.ALWAYS);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		IFile file = null;
		try {
			file = ((IFileEditorInput) getEditorInput()).getFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(file != null) {
			File location = new File(file.getLocationURI());
			Process process = (Process)this.model;
			String error = process.validate();
			if(StringUtils.isEmpty(error)) {
				Environment.writer(process, location.getAbsolutePath());
				getCommandStack().markSaveLocation();
			} else {
				MessageDialog.openWarning(null, "错误", error);
				throw new RuntimeException(error);
			}
		}
	}

	protected void save(IFile file, IProgressMonitor progressMonitor)
			throws CoreException {
		if (progressMonitor == null) {
			progressMonitor = new NullProgressMonitor();
		}
		progressMonitor.beginTask("Saving " + file, 2);
		try {
			progressMonitor.worked(1);
			file.refreshLocal(0, new SubProgressMonitor(progressMonitor, 1));
			progressMonitor.done();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part.getSite().getWorkbenchWindow().getActivePage() == null) {
			return;
		}
		super.selectionChanged(part, selection);
	}
}
