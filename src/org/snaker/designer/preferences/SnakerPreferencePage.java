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
package org.snaker.designer.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.snaker.designer.Activator;
import org.snaker.designer.config.Configuration;

/**
 * Snaker流程引擎模式化模型配置页面
 * @author yuqs
 * @version 1.0
 * @since 1.2.3
 */
public class SnakerPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	public static final String MODEL_PROCESS = "model-process";
	public static final String MODEL_FORM = "model-form";
	public SnakerPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Snaker流程设计器用于定义流程逻辑.");
	}
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
		FileFieldEditor editor = new FileFieldEditor(MODEL_PROCESS, "流程模型配置", getFieldEditorParent());
		editor.setEmptyStringAllowed(true);
		editor.setFileExtensions(new String[]{"*.xml"});
		addField(editor);
	}
	@Override
	public boolean performOk() {
		super.performOk();
		Configuration.getInstance().refresh();
		return true;
	}
	@Override
	protected void performApply() {
		super.performApply();
		Configuration.getInstance().refresh();
	}
}
