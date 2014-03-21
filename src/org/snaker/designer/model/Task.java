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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.snaker.designer.utils.StringUtils;

/**
 * 任务元素模型类
 * @author yuqs
 * @version 1.0
 */
public class Task extends NodeElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 862281580390510339L;
    public static final String PROP_FIELDS = "FIELDS";
    public static final String PROP_FORM = "form";
    public static final String PROP_ASSIGNEE = "assignee";
    public static final String PROP_TYPE = "performType";
    public static final String PROP_EXPIRETIME = "expireTime";
    public static final String PROP_ASSIGNEMENT_HANDLER = "assignmentHandler";
	/**
	 * 表单url
	 */
	private String form;
	/**
	 * 参与者变量名称
	 */
	private String assignee;
	/**
	 * 参与者处理类
	 */
	private String assignmentHandler;
	/**
	 * 参与方式：0普通any方式；1特殊的all方式
	 * any：任何一个参与者处理完即执行下一步
	 * all：所有参与者都完成，才可执行下一步
	 */
	private String performType = "ANY";
	/**
	 * 期望完成时间
	 */
	private String expireTime;
	/**
	 * 提醒时间
	 */
	private String reminderTime;
	/**
	 * 提醒次数
	 */
	private String reminderRepeat;
	/**
	 * 是否自动执行
	 */
	private String autoExecute = "Y";
	/**
	 * 任务执行后回调方法
	 */
	private String action;
	/**
	 * 任务可包含表单的字段
	 */
	private List<Field> fields = new ArrayList<Field>();
	
    public void addField(Field field)
    {
    	this.fields.add(field);
    	fireStructureChange(PROP_FIELDS, fields);
    }
    
    public void removeField(Field field)
    {
    	this.fields.remove(field);
    	fireStructureChange(PROP_FIELDS, fields);
    }
    
    public void swapIndex(int i, int j)
    {
    	Collections.swap(fields, i, j);
    	fireStructureChange(PROP_FIELDS, fields);
    }

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
		fireStructureChange(PROP_FIELDS, fields);
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
		firePropertyChange(PROP_FORM, null, form);
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
		firePropertyChange(PROP_ASSIGNEE, null, assignee);
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
		firePropertyChange(PROP_EXPIRETIME, null, expireTime);
	}

	public String getPerformType() {
		return performType;
	}

	public void setPerformType(String performType) {
		this.performType = performType;
		firePropertyChange(PROP_TYPE, null, performType);
	}

	@Override
	public String validate() {
		StringBuffer error = new StringBuffer(super.validate());
		if(StringUtils.isEmpty(displayName)) {
			error.append(this.getName() + " displayName 不能为空\n");
		}
		return error.toString();
	}

	public String getAssignmentHandler() {
		return assignmentHandler;
	}

	public void setAssignmentHandler(String assignmentHandler) {
		this.assignmentHandler = assignmentHandler;
		firePropertyChange(PROP_ASSIGNEMENT_HANDLER, null, assignmentHandler);
	}

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getReminderRepeat() {
		return reminderRepeat;
	}

	public void setReminderRepeat(String reminderRepeat) {
		this.reminderRepeat = reminderRepeat;
	}

	public String getAutoExecute() {
		return autoExecute;
	}

	public void setAutoExecute(String autoExecute) {
		this.autoExecute = autoExecute;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
