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
package org.snaker.designer.model;

import org.snaker.designer.utils.StringUtils;

/**
 * 子流程节点模型类
 * @author yuqs
 * @version 1.0
 */
public class SubProcess extends NodeElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2862486260956391344L;
	private static final String PROP_PROCESSNAME = "processName";
	/**
	 * 子流程名称
	 */
	private String processName;
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
		firePropertyChange(PROP_PROCESSNAME, null, processName);
	}
	@Override
	public String validate() {
		StringBuffer error = new StringBuffer(super.validate());
		if(StringUtils.isEmpty(processName)) {
			error.append(this.getName() + " processName 不能为空");
		}
		return error.toString();
	}
}
