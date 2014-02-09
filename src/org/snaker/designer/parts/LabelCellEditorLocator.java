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

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 元素编辑label的位置
 * @author yuqs
 * @version 1.0
 */
public class LabelCellEditorLocator implements CellEditorLocator {
	private Label label;

	public LabelCellEditorLocator(Label label) {
		setLabel(label);
	}

	public void relocate(CellEditor cellEditor) {
		Text text = (Text) cellEditor.getControl();
		Point pref = text.computeSize(-1, -1);
		Rectangle rect = label.getTextBounds().getCopy();
		label.translateToAbsolute(rect);
		text.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
	}

	protected Label getLabel() {
		return label;
	}

	protected void setLabel(Label label) {
		this.label = label;
	}

}