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
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 菱形元素图形
 * @author yuqs
 * @version 1.0
 */
public class DiamondElementFigure extends AbstractElementFigure {
	protected Polygon diamond;

	@Override
	protected void customizeFigure() {
		diamond = new Polygon();
		diamond.setLineWidth(1);
		add(diamond, 0);
		setSize(50, 50);
		diamond.setPoints(calculatePointList());
	}

	public void setBounds(Rectangle rectangle) {
		super.setBounds(rectangle);
		diamond.setPoints(calculatePointList());
	}

	private PointList calculatePointList() {
		PointList result = new PointList();
		Rectangle bounds = getBounds();
		result.addPoint(bounds.x + bounds.width / 2 - diamond.getLineWidth()
				/ 4, bounds.y + diamond.getLineWidth() / 2);
		result.addPoint(bounds.x + bounds.width - diamond.getLineWidth(),
				bounds.y + bounds.height / 2 - diamond.getLineWidth() / 4);
		result.addPoint(bounds.x + bounds.width / 2 - diamond.getLineWidth()
				/ 4, bounds.y + bounds.height - diamond.getLineWidth());
		result.addPoint(bounds.x + diamond.getLineWidth() / 2, bounds.y
				+ bounds.height / 2 - diamond.getLineWidth() / 4);
		return result;
	}

	public ConnectionAnchor getSourceConnectionAnchor() {
		return new DiamondAnchor(this);
	}

	public ConnectionAnchor getTargetConnectionAnchor() {
		return new DiamondAnchor(this);
	}

}
