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
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 网关图形
 * @author yuqs
 * @version 1.0
 */
public abstract class GatewayFigure extends AbstractElementFigure {
	public GatewayFigure() {
		setSize(48, 48);
	}

	public void setText(String text) {
	}

	public void setBounds(Rectangle r) {
		r.setSize(48, 48);
		super.setBounds(r);
	}

	public ConnectionAnchor getSourceConnectionAnchor() {
		return new DiamondAnchor(this);
	}

	public ConnectionAnchor getTargetConnectionAnchor() {
		return new DiamondAnchor(this);
	}

}
