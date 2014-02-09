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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.graphics.Color;
import org.snaker.designer.model.Custom;

/**
 * 自定义节点图形
 * @author yuqs
 * @version 1.0
 */
public class CustomFigure extends RoundedRectangleElementFigure {
	private static final Color veryLightBlue = new Color(null, 246, 247, 255);
	private static final Color lightBlue = new Color(null, 3, 104, 154);

	public CustomFigure(Custom custom) {
		setText(custom.getDisplayName());
		super.repaint();
	}

	protected void paintChildren(Graphics graphics) {
		Color foregroundColor = graphics.getForegroundColor();
		Color backgroundColor = graphics.getBackgroundColor();
		graphics.setBackgroundColor(veryLightBlue);
		graphics.setForegroundColor(lightBlue);

		super.paintChildren(graphics);
		graphics.setBackgroundColor(backgroundColor);
		graphics.setForegroundColor(foregroundColor);
	}

	protected void customizeFigure() {
		super.customizeFigure();
		getLabel().setForegroundColor(ColorConstants.darkGray);
		rectangle.setLineWidth(2);
	}
}
