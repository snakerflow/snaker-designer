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
package org.snaker.designer.figures;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * 抽象元素图形
 * @author yuqs
 * @version 1.0
 */
public abstract class AbstractElementFigure extends Panel implements IFigure {
	private boolean selected;
	private Label label = new Label();

	public AbstractElementFigure() {
		add(this.label);
		customizeFigure();
	}

	public void setIcon(Image icon) {
		this.label.setIcon(icon);
	}

	public void setText(String text) {
		this.label.setText(text);
	}

	public Label getLabel() {
		return this.label;
	}

	public void setColor(Color color) {
		setBackgroundColor(color);
	}

	public void setBounds(Rectangle bounds) {
		super.setBounds(bounds);
		this.label.setBounds(bounds);
	}

	protected abstract void customizeFigure();

	public void setSelected(boolean b) {
		this.selected = b;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setFocus(boolean b) {
		repaint();
	}

	public ConnectionAnchor getSourceConnectionAnchor() {
		return new ChopboxAnchor(this);
	}

	public ConnectionAnchor getTargetConnectionAnchor() {
		return new ChopboxAnchor(this);
	}

	public void paint(Graphics g) {
		int saved = g.getAntialias();
		g.setAntialias(1);
		super.paint(g);
		g.setAntialias(saved);
	}
}
