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

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

import org.snaker.designer.utils.ColorUtils;

/**
 * field border
 * @author yuqs
 * @version 1.0
 */
public class FieldBorder extends LineBorder {
	protected int grabBarWidth = -1;

	private String text = "";
	private int BLANK_SPACES = 2;
	private int WORD_HEIGHT = 6;

	protected Dimension grabBarSize = null;

	public FieldBorder(String text) {
		this.text = text;
		this.grabBarWidth = 100;
		this.grabBarSize = new Dimension(this.grabBarWidth, 18);
	}

	public void setText(String text) {
		this.text = text;
	}

	public Insets getInsets(IFigure figure) {
		return new Insets(getWidth() + 2, this.grabBarWidth + 2,
				getWidth() + 2, getWidth() + 2);
	}

	public Dimension getPreferredSize() {
		return this.grabBarSize;
	}

	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		Rectangle bounds = figure.getBounds();
		Rectangle r = new Rectangle(bounds.x, bounds.y, this.grabBarWidth,
				bounds.height);
		AbstractBorder.tempRect.setBounds(r);
		graphics.setBackgroundColor(ColorUtils.Blue);

		graphics.setForegroundColor(ColorUtils.xorGate);
		graphics.fillRectangle(AbstractBorder.tempRect);
		figure.setOpaque(false);

		int i = AbstractBorder.tempRect.bottom()
				- AbstractBorder.tempRect.height / 2;
		graphics.drawText(getText(), AbstractBorder.tempRect.x
				+ this.BLANK_SPACES, i - this.WORD_HEIGHT);
		super.paint(figure, graphics, insets);
	}

	public void setGrabBarWidth(int width) {
		this.grabBarWidth = width;
	}

	public String getText() {
		if (this.text == null)
			this.text = "";
		return this.text;
	}
}
