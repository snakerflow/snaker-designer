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

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 菱形
 * @author yuqs
 * @version 1.0
 */
public class DiamondAnchor extends AbstractConnectionAnchor {

	public DiamondAnchor(IFigure owner) {
		super(owner);
	}

	public Point getLocation(Point reference) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getOwner().getBounds());
		r.translate(-1, -1);
		r.resize(1, 1);
		getOwner().translateToAbsolute(r);

		Point ref = r.getCenter().negate().translate(reference);

		float centerX = r.x + 0.5f * r.width;
		float centerY = r.y + 0.5f * r.height;
		float dx, dy;

		if (Math.abs(ref.x * r.height) == Math.abs(Math.abs(ref.y) * r.width)) {
			dx = ref.x > 0 ? r.width / 4 : -r.width / 4;
			dy = ref.y > 0 ? r.height / 4 : -r.height / 4;
		} else if (ref.x == 0) {
			dx = 0;
			dy = ref.y > 0 ? r.height / 2 : -r.height / 2;
		} else {

			float numerator = ref.x * r.height * r.width / 2;
			float firstDenominator = Math.abs(ref.y) * r.width + ref.x
					* r.height;
			float secondDenominator = Math.abs(ref.y) * r.width - ref.x
					* r.height;

			dx = (ref.x > 0) ? numerator / firstDenominator : numerator
					/ secondDenominator;
			dy = dx * ref.y / ref.x;

		}

		return new Point(Math.round(centerX + dx), Math.round(centerY + dy));
	}

}