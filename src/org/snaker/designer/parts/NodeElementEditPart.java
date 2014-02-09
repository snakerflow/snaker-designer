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

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.ui.views.properties.IPropertySource;
import org.snaker.designer.SnakerFlowDesignerEditor;
import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.figures.AbstractElementFigure;
import org.snaker.designer.figures.CustomFigure;
import org.snaker.designer.figures.ExclusiveGatewayFigure;
import org.snaker.designer.figures.ParallelGatewayFigure;
import org.snaker.designer.figures.SimpleFigure;
import org.snaker.designer.figures.StartEventFigure;
import org.snaker.designer.figures.SubProcessFigure;
import org.snaker.designer.figures.TaskFigure;
import org.snaker.designer.figures.TerminateEndEventFigure;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Custom;
import org.snaker.designer.model.Decision;
import org.snaker.designer.model.End;
import org.snaker.designer.model.Fork;
import org.snaker.designer.model.Join;
import org.snaker.designer.model.ModelHelper;
import org.snaker.designer.model.ModelPropertySource;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Simple;
import org.snaker.designer.model.Start;
import org.snaker.designer.model.SubProcess;
import org.snaker.designer.model.Task;
import org.snaker.designer.model.Transition;
import org.snaker.designer.policies.ElementDirectEditPolicy;
import org.snaker.designer.policies.ProcessGraphicalNodeEditPolicy;
import org.snaker.designer.policies.NodeComponentEditPolicy;

/**
 * 节点元素EditPart
 * @author yuqs
 * @version 1.0
 */
public class NodeElementEditPart extends AbstractGraphicalEditPart implements
		NodeEditPart, PropertyChangeListener {
	private DirectEditManager manager;

	private SnakerFlowDesignerEditor editor;

	private IPropertySource propertySource = null;

	private AbstractElementFigure figure = null;

	public NodeElementEditPart(SnakerFlowDesignerEditor editor,
			NodeElement model) {
		this.editor = editor;
		setModel(model);
	}

	@Override
	protected IFigure createFigure() {
		if (getElement() instanceof Start)
			figure = new StartEventFigure();
		else if (getElement() instanceof End)
			figure = new TerminateEndEventFigure();
		else if (getElement() instanceof Decision)
			figure = new ExclusiveGatewayFigure();
		else if (getElement() instanceof Fork)
			figure = new ParallelGatewayFigure();
		else if (getElement() instanceof Join)
			figure = new ParallelGatewayFigure();
		else if (getElement() instanceof SubProcess)
			figure = new SubProcessFigure((SubProcess) getElement());
		else if (getElement() instanceof Task)
			figure = new TaskFigure((Task) getElement());
		else if (getElement() instanceof Custom)
			figure = new CustomFigure((Custom) getElement());
		else if (getElement() instanceof Simple) {
			figure = new SimpleFigure((Simple) getElement());
		}
		return figure;
	}

	private NodeElement getElement() {
		return (NodeElement) getModel();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new ElementDirectEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new ProcessGraphicalNodeEditPolicy());
	}

	public void refreshVisuals() {
		AbstractElementFigure figure = (AbstractElementFigure) getFigure();
		figure.setText(getElement().getDisplayName());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure,
				getElement().getLayout());
		super.refreshVisuals();
	}

	@SuppressWarnings("rawtypes")
	public List<?> getModelChildren() {
		return new ArrayList();
	}

	public void activate() {
		if (isActive()) {
			return;
		}
		((NodeElement) getModel()).addPropertyChangeListener(this);
		super.activate();
	}

	public void deactivate() {
		if (!isActive()) {
			return;
		}
		((NodeElement) getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class key) {
		if (IPropertySource.class == key) {
			return getPropertySource();
		}
		return super.getAdapter(key);
	}

	protected IPropertySource getPropertySource() {
		if (this.propertySource == null) {
			Component component = ConfigManager.getComponentByModel(getElement());
			ModelHelper.loadAttribute((BaseElement) getModel(), component);
			this.propertySource = new ModelPropertySource(getElement(),
					component);
		}
		return this.propertySource;
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(getModel().getClass());
	}

	@Override
	protected List<Transition> getModelSourceConnections() {
		return getElement().getOutputs();
	}

	@Override
	protected List<Transition> getModelTargetConnections() {
		return getElement().getInputs();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	public SnakerFlowDesignerEditor getEditor() {
		return editor;
	}

	public void setEditor(SnakerFlowDesignerEditor editor) {
		this.editor = editor;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(NodeElement.PROP_LAYOUT))
			refreshVisuals();
		else if (evt.getPropertyName().equals(NodeElement.PROP_NAME))
			refreshVisuals();
		else if (evt.getPropertyName().equals(NodeElement.PROP_DISPLAYNAME))
			refreshVisuals();
		else if (evt.getPropertyName().equals(NodeElement.PROP_INPUTS))
			refreshTargetConnections();
		else if (evt.getPropertyName().equals(NodeElement.PROP_OUTPUTS))
			refreshSourceConnections();
	}

	@Override
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN)
			//doubleClicked();
			;
		else if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
			performDirectEdit();
		else
			super.performRequest(request);
	}

	protected void performDirectEdit() {
		AbstractElementFigure figure = (AbstractElementFigure) getFigure();
		Label label = figure.getLabel();
		if (label == null)
			return;
		if (manager == null)
			manager = new ElementDirectEditManager(this,
					new LabelCellEditorLocator(label));
		manager.show();
	}

	protected void doubleClicked() {
		if (getElement() instanceof Task) {
			if (-1 != this.editor.getFormEditPageID()) {
				this.editor.removeFormEditPage();
			}
			if (-1 == this.editor.getFormEditPageID()) {
				this.editor.createFormEditPage((Task) getElement());
			}
			this.editor.setActivePage(this.editor.getFormEditPageID());
		}
	}
}
