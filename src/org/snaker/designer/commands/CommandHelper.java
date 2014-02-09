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

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import org.snaker.designer.commands.FieldBackwardCommand;
import org.snaker.designer.commands.FieldDeleteCommand;
import org.snaker.designer.commands.FieldForwardCommand;
import org.snaker.designer.commands.LineBRCancelCommand;
import org.snaker.designer.commands.LineBRSetCommand;
import org.snaker.designer.commands.PlaceHolderDecreaseCommand;
import org.snaker.designer.commands.PlaceHolderIncreaseCommand;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.Field;

/**
 * 命令帮助类
 * @author yuqs
 * @version 1.0
 */
public class CommandHelper {
	public static final String CREATE_CHILD = "create child";

	public static final String REQUEST_TYPE_ADDOPTION = "添加选项";

	public static final String REQUEST_TYPE_DELETE = "删除";

	public static final String REQUEST_TYPE_FORWARD = "前移";

	public static final String REQUEST_TYPE_BACKWARD = "后移";

	public static final String REQUEST_TYPE_ADDPLACEHOLDER = "增加占位符";

	public static final String REQUEST_TYPE_DELPLACEHOLDER = "减少占位符";

	public static final String REQUEST_TYPE_SETLINEBR = "设置换行";

	public static final String REQUEST_TYPE_CANCELLINEBR = "取消换行";

	public static boolean understandCommand(Request request, Object obj) {
		if (REQUEST_TYPE_ADDOPTION.equals(request.getType())) {
			return false;
		} else {
			if (REQUEST_TYPE_DELETE.equals(request.getType())) {
				return true;
			}
			if (REQUEST_TYPE_FORWARD.equals(request.getType())) {
				return true;
			}
			if (REQUEST_TYPE_BACKWARD.equals(request.getType())) {
				return true;
			}
			if (REQUEST_TYPE_ADDPLACEHOLDER.equals(request.getType())) {
				return true;
			}
			if (REQUEST_TYPE_DELPLACEHOLDER.equals(request.getType())) {
				return true;
			}
			if (REQUEST_TYPE_SETLINEBR.equals(request.getType())) {
				return true;
			}
			if (REQUEST_TYPE_CANCELLINEBR.equals(request.getType())) {
				return true;
			}
		}
		return false;
	}

	public static Command getCommand(Request request, Object model,
			Object parent) {
		if (REQUEST_TYPE_ADDOPTION.equals(request.getType())) {
			// return createChoiceFieldCommand(request, (Field) model);
		}
		if (REQUEST_TYPE_DELETE.equals(request.getType())) {
			return createDeleteCommand(request, model, parent);
		}
		if (REQUEST_TYPE_FORWARD.equals(request.getType())) {
			return createForwardCommand(request, model, parent);
		}
		if (REQUEST_TYPE_BACKWARD.equals(request.getType())) {
			return createBackwardCommand(request, model, parent);
		}
		if (REQUEST_TYPE_ADDPLACEHOLDER.equals(request.getType())) {
			return createPlaceholderIncreaseCommand(request, model, parent);
		}
		if (REQUEST_TYPE_DELPLACEHOLDER.equals(request.getType())) {
			return createPlaceholderDecreaseCommand(request, model, parent);
		}
		if (REQUEST_TYPE_SETLINEBR.equals(request.getType())) {
			return createLineBRSetCommand(request, model, parent);
		}
		if (REQUEST_TYPE_CANCELLINEBR.equals(request.getType())) {
			return createLineBRCancelCommand(request, model, parent);
		}
		return null;
	}

	private static Command createBackwardCommand(Request request, Object model,
			Object parent) {
		if ((model instanceof Field)) {
			FieldBackwardCommand cmd = new FieldBackwardCommand();
			cmd.setModel((Field) model);
			cmd.setParent((BaseElement) parent);
			return cmd;
		}
		return null;
	}

	private static Command createForwardCommand(Request request, Object model,
			Object parent) {
		if ((model instanceof Field)) {
			FieldForwardCommand cmd = new FieldForwardCommand();
			cmd.setModel((Field) model);
			cmd.setParent((BaseElement) parent);
			return cmd;
		}
		return null;
	}

	private static Command createDeleteCommand(Request request, Object model,
			Object parent) {
		if ((model instanceof Field)) {
			FieldDeleteCommand cmd = new FieldDeleteCommand();
			cmd.setModel((Field) model);
			cmd.setParent((BaseElement) parent);
			return cmd;
		}
		return null;
	}

	private static Command createLineBRSetCommand(Request request,
			Object model, Object parent) {
		if ((model instanceof Field)) {
			LineBRSetCommand cmd = new LineBRSetCommand();
			cmd.setModel((Field) model);
			return cmd;
		}
		return null;
	}

	private static Command createLineBRCancelCommand(Request request,
			Object model, Object parent) {
		if ((model instanceof Field)) {
			LineBRCancelCommand cmd = new LineBRCancelCommand();
			cmd.setModel((Field) model);
			return cmd;
		}
		return null;
	}

	private static Command createPlaceholderIncreaseCommand(Request request,
			Object model, Object parent) {
		if ((model instanceof Field)) {
			PlaceHolderIncreaseCommand cmd = new PlaceHolderIncreaseCommand();
			cmd.setModel((Field) model);
			cmd.setParent((BaseElement) parent);
			return cmd;
		}
		return null;
	}

	private static Command createPlaceholderDecreaseCommand(Request request,
			Object model, Object parent) {
		if ((model instanceof Field)) {
			PlaceHolderDecreaseCommand cmd = new PlaceHolderDecreaseCommand();
			cmd.setModel((Field) model);
			return cmd;
		}
		return null;
	}

	private static Command getCreateFieldCommand(CreateRequest request,
			BaseElement parent) {
		String newObjectType = (String) request.getNewObjectType();
		FieldCreateCommand create = new FieldCreateCommand();
		create.setField((Field) request.getNewObject());
		create.setParent(parent);
		create.setLabel(newObjectType);
		return create;
	}

	public static Command getCreateCommand(Request request, Object model) {
		if (CREATE_CHILD.equals(request.getType())) {
			try {
				CreateRequest req = (CreateRequest) request;
				if ((req.getNewObject() instanceof Field)) {
					return getCreateFieldCommand(req, (BaseElement) model);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
