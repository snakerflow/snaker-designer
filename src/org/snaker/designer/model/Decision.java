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

/**
 * 分支节点模型类
 * @author yuqs
 * @version 1.0
 */
public class Decision extends NodeElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4624639776217020911L;
	private static final String PROP_EXPR = "expr";
	private static final String PROP_HANDLE = "handle";
	/**
	 * 表达式属性
	 */
	private String expr;
	/**
	 * 决策分支处理类
	 */
	private String handleClass;
	public String getExpr() {
		return expr;
	}
	public void setExpr(String expr) {
		this.expr = expr;
		firePropertyChange(PROP_EXPR, null, expr);
	}
	public String getHandleClass() {
		return handleClass;
	}
	public void setHandleClass(String handleClass) {
		this.handleClass = handleClass;
		firePropertyChange(PROP_HANDLE, null, handleClass);
	}
}
