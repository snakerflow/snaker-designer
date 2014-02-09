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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.geometry.Point;

/**
 * 分割点位置
 * @author yuqs
 * @version 1.0
 */
public class LabelMidpointOffsetLocator extends MidpointLocator {
	private Point offset;

	public LabelMidpointOffsetLocator(Connection c, int i, Point offset) {
		super(c, i);
		this.offset = offset == null ? new Point(0, 0) : offset;
	}

	@Override
	protected Point getReferencePoint() {
		Point point = super.getReferencePoint();
		return point.getTranslated(offset);
	}

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}
}
