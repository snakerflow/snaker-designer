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
package org.snaker.designer.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import org.snaker.designer.model.Transition;

/**
 * 折点命令
 * @author yuqs
 * @version 1.0
 */
public class BendpointCommand extends Command {
	protected int index;

	protected Transition connection;

	protected Dimension d1, d2;

	protected Point p1, p2;

	public void setConnection(Transition connection) {
		this.connection = connection;
	}

	public void redo() {
		execute();
	}

	public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
		d1 = dim1;
		d2 = dim2;
	}

	public void setRelativePoints(Point dim1, Point dim2) {
		p1 = dim1;
		p2 = dim2;
	}

	public void setIndex(int i) {
		index = i;
	}

}
