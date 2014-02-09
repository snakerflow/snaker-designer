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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.ui.views.properties.IPropertySource;
import org.snaker.designer.SnakerFlowDesignerEditor;
import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.ModelHelper;
import org.snaker.designer.model.ModelPropertySource;
import org.snaker.designer.model.Process;
import org.snaker.designer.policies.ProcessXYLayoutEditPolicy;

/**
 * 流程定義EditPart
 * @author yuqs
 * @version 1.0
 */
public class ProcessEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {
	private SnakerFlowDesignerEditor editor;

	private IPropertySource propertySource = null;

	public ProcessEditPart(SnakerFlowDesignerEditor editor, Process model) {
		this.editor = editor;
		setModel(model);
	}

	@Override
	protected IFigure createFigure() {
		FreeformLayer layer = new FreeformLayer();
		layer.setLayoutManager(new FreeformLayout());
		return layer;
	}

	public Process getProcess() {
		return (Process) getModel();
	}

	@Override
	public void activate() {
		if (isActive()) {
			return;
		}
		getProcess().addPropertyChangeListener(this);
		super.activate();
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			return;
		}
		getProcess().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new ProcessXYLayoutEditPolicy());
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy());
	}

	@Override
	protected List<BaseElement> getModelChildren() {
		List<BaseElement> l = new ArrayList<BaseElement>();
		l.addAll(getProcess().getNodeElements());
		return l;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (Process.PROP_NODES.equals(prop))
			refreshChildren();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			return getPropertySource();
		}
		if (key == SnapToHelper.class) {
			List<SnapToHelper> helpers = new ArrayList<SnapToHelper>();
			if (Boolean.TRUE.equals(getViewer().getProperty(
					SnapToGeometry.PROPERTY_SNAP_ENABLED))) {
				helpers.add(new SnapToGeometry(this));
			}
			if (Boolean.TRUE.equals(getViewer().getProperty(
					SnapToGrid.PROPERTY_GRID_ENABLED))) {
				helpers.add(new SnapToGrid(this));
			}
			if (helpers.size() == 0) {
				return null;
			} else {
				return new CompoundSnapToHelper(
						helpers.toArray(new SnapToHelper[0]));
			}
		}

		return super.getAdapter(key);
	}

	protected IPropertySource getPropertySource() {
		if (this.propertySource == null) {
			Component component = ConfigManager.getComponentByName(getProcess().getClass().getSimpleName());
			ModelHelper.loadAttribute((BaseElement) getModel(), component);
			this.propertySource = new ModelPropertySource(
					(BaseElement) getModel(), component);
		}
		return this.propertySource;
	}

	public SnakerFlowDesignerEditor getEditor() {
		return editor;
	}

	public void setEditor(SnakerFlowDesignerEditor editor) {
		this.editor = editor;
	}

	@Override
	public void performRequest(Request req) {
		super.performRequest(req);
	}
}
