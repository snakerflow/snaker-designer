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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * 椭圆元素图形
 * @author yuqs
 * @version 1.0
 */
public class EllipseElementFigure extends AbstractElementFigure {
	protected Ellipse ellipse;

	protected void customizeFigure() {
		this.ellipse = new Ellipse();
		add(this.ellipse, 0);
		this.ellipse.setBounds(getBounds());
		setSize(40, 40);
	}

	public void setColor(Color color) {
		this.ellipse.setBackgroundColor(color);
	}

	public void setBounds(Rectangle rectangle) {
		super.setBounds(rectangle);
		this.ellipse.setBounds(rectangle);
	}

	public void setSelected(boolean b) {
		super.setSelected(b);
		this.ellipse.setLineWidth(b ? 3 : 1);
		repaint();
	}

	public ConnectionAnchor getSourceConnectionAnchor() {
		return new EllipseAnchor(this);
	}

	public ConnectionAnchor getTargetConnectionAnchor() {
		return new EllipseAnchor(this);
	}
}
