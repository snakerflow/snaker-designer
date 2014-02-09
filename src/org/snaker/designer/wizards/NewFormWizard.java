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
package org.snaker.designer.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * 新建Snaker Form向导
 * @author yuqs
 * @version 1.0
 */
public class NewFormWizard  extends Wizard implements INewWizard {
	private FormWizardPage page;
	private ISelection selection;

	public NewFormWizard() {
		setNeedsProgressMonitor(true);
	}

	/**
	 * 
	 */
	@Override
	public void init(IWorkbench arg0, IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * 向导的finish处理
	 */
	@Override
	public boolean performFinish() {
		final String containerName = this.page.getContainerName();
		final String fileName = this.page.getFileName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error",
					realException.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public void addPages() {
		this.page = new FormWizardPage(this.selection);
		addPage(this.page);
	}

	private void doFinish(String containerName, String fileName,
			IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if ((!resource.exists()) || (!(resource instanceof IContainer))) {
			throwCoreException("Container \"" + containerName
					+ "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
			InputStream stream = openContentStream();
			if (file.exists())
				file.setContents(stream, true, true, monitor);
			else {
				file.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException localIOException) {
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException localPartInitException) {
				}
			}
		});
		monitor.worked(1);
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(4, "ProcessDesigner", 0, message, null);
		throw new CoreException(status);
	}

	private InputStream openContentStream() {
		String contents = "This is the initial file contents for *.process file that should be word-sorted in the Preview page of the multi-page editor";
		return new ByteArrayInputStream(contents.getBytes());
	}
}
