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
package org.snaker.designer.parts;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Text;

import org.snaker.designer.model.BaseElement;

/**
 * 元素編輯管理器
 * @author yuqs
 * @version 1.0
 */
public class ElementDirectEditManager extends DirectEditManager {
	private String displayName;

	public ElementDirectEditManager(GraphicalEditPart source,
			CellEditorLocator locator) {
		super(source, TextCellEditor.class, locator);
		Object object = source.getModel();
		if (object instanceof BaseElement) {
			displayName = ((BaseElement) object).getDisplayName();
		}
	}

	protected void initCellEditor() {
		String initialValue = null;
		if (displayName != null && displayName.trim().length() > 0) {
			initialValue = displayName;
		}
		getCellEditor().setValue(initialValue == null ? "" : initialValue);
		Text text = (Text) getCellEditor().getControl();
		text.selectAll();
	}
}
