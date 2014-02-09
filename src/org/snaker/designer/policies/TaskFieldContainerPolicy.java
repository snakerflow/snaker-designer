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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import org.snaker.designer.commands.CommandHelper;
import org.snaker.designer.parts.EditPartHelper;
import org.snaker.designer.parts.TaskFieldEditPart;

/**
 * TaskField容器策略
 * @author yuqs
 * @version 1.0
 */
public class TaskFieldContainerPolicy extends ContainerEditPolicy {
	@Override
	protected Command getCreateCommand(CreateRequest request)
	{
	    Object parent = EditPartHelper.getParentModel((TaskFieldEditPart)getHost());
	    Command command = CommandHelper.getCreateCommand(request, parent);
	    return command;
	}
}
