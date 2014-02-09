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
import org.snaker.designer.model.Process;

/**
 * EditPart工厂类
 * @author yuqs
 * @version 1.0
 */
public class DesignerEditPartFactory implements EditPartFactory {
	private SnakerFlowDesignerEditor editor;

	public DesignerEditPartFactory(SnakerFlowDesignerEditor editor) {
		this.editor = editor;
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		if ((model instanceof Process)) {
			part = new ProcessEditPart(this.editor, (Process) model);
		} else if ((model instanceof NodeElement)) {
			part = new NodeElementEditPart(this.editor, (NodeElement) model);
		} else if ((model instanceof Transition)) {
			part = new TransitionEditPart((Transition) model);
		} else if ((model instanceof Field)) {
			part = new FieldEditPart((Field) model);
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}

}
