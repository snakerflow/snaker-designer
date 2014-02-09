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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import org.snaker.designer.SnakerFlowDesignerEditor;
import org.snaker.designer.model.*;

/**
 * 包含Field的Task EditPart工厂类
 * @author yuqs
 * @version 1.0
 */
public class TaskFieldEditPartFactory implements EditPartFactory {
	private SnakerFlowDesignerEditor editor;

	public TaskFieldEditPartFactory(SnakerFlowDesignerEditor editor) {
		this.editor = editor;
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		if ((model instanceof Field)) {
			part = new FieldEditPart((Field) model);
		} else if (model instanceof Task) {
			part = new TaskFieldEditPart(this.editor, (Task) model);
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}

}
