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
package org.snaker.designer.policies;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.handles.MoveHandleLocator;

/**
 * 连接的编辑策略
 * @author yuqs
 * @version 1.0
 */
public class ConnectionEndpointEditPolicy extends
		org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List createSelectionHandles() {
		List handles = super.createSelectionHandles();
		List<IFigure> children = (List<IFigure>) getHostFigure().getChildren();
		for (IFigure figure : children) {
			if (figure instanceof Label)
				handles.add(new MoveHandle((GraphicalEditPart) getHost(),
						new MoveHandleLocator(figure)));
		}
		return handles;
	}
}
