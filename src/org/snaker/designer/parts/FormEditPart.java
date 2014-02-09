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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import org.snaker.designer.SnakerFormDesignerEditor;
import org.snaker.designer.model.Field;
import org.snaker.designer.model.Form;
import org.snaker.designer.policies.FormXYEditLayoutPolicy;

/**
 * Form模型的EditPart
 * @author yuqs
 * @version 1.0
 */
public class FormEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {
	private SnakerFormDesignerEditor editor;

	public FormEditPart(SnakerFormDesignerEditor editor, Form model) {
		setModel(model);
		this.editor = editor;
	}

	@Override
	protected IFigure createFigure() {
		IFigure f = new FreeformLayer();
		f.setLayoutManager(new FreeformLayout());
		f.setOpaque(true);
		return f;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FormXYEditLayoutPolicy());
	}

	@Override
	protected List<Field> getModelChildren() {
		return getForm().getFields();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (this.editor != null)
			this.editor.setDirty(true);
		if (Form.PROP_FORM.equals(prop)) {
			refreshChildren();
		} else if (Form.PROP_LIST.equals(prop)) {
			refreshChildren();
			refreshVisuals();
			EditPartHelper.refreshAllChildren(this);
		}
	}

	public Form getForm() {
		return (Form) getModel();
	}

	@Override
	public void activate() {
		if (isActive()) {
			return;
		}
		getForm().addPropertyChangeListener(this);
		super.activate();
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			return;
		}
		getForm().removePropertyChangeListener(this);
		super.deactivate();
	}
}
