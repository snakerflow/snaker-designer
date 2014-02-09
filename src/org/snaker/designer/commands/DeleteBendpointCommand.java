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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import org.snaker.designer.model.Transition;

/**
 * 删除折点命令
 * @author yuqs
 * @version 1.0
 */
public class DeleteBendpointCommand extends Command {
	private Transition connection;
	private Point oldLocation;
	private int index;

	public void execute() {
		oldLocation = (Point) connection.getBendpoints().get(index);
		connection.removeBendpoint(index);
	}

	public void setConnectionModel(Object model) {
		connection = (Transition) model;
	}

	public void setIndex(int i) {
		index = i;
	}

	public void undo() {
		connection.addBendpoint(index, oldLocation);
	}
}