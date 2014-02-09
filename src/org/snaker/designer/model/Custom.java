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
 * 自定义节点模型
 * @author yuqs
 * @version 1.0
 */
public class Custom extends NodeElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2039331179065431864L;
    public static final String PROP_CLAZZ = "clazz";
    public static final String PROP_METHODNAME = "methodName";
    public static final String PROP_ARGS = "args";
    public static final String PROP_VAR = "var";
	/**
	 * 需要执行的class类路径
	 */
	private String clazz;
	/**
	 * 需要执行的class对象的方法名称
	 */
	private String methodName;
	/**
	 * 执行方法时传递的参数表达式变量名称
	 */
	private String args;
	/**
	 * 执行的返回值变量
	 */
	private String var;
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		firePropertyChange(PROP_CLAZZ, null, clazz);
		this.clazz = clazz;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		firePropertyChange(PROP_METHODNAME, null, methodName);
		this.methodName = methodName;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		firePropertyChange(PROP_ARGS, null, args);
		this.args = args;
	}
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		firePropertyChange(PROP_VAR, null, var);
		this.var = var;
	}
	@Override
	public String validate() {
		StringBuffer error = new StringBuffer(super.validate());
		if(StringUtils.isEmpty(clazz)) {
			error.append(this.getName() + " clazz 不能为空\n");
		}
		if(StringUtils.isEmpty(displayName)) {
			error.append(this.getName() + " displayName 不能为空\n");
		}
		return error.toString();
	}
}
