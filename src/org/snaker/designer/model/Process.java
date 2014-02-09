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
import java.util.List;

import org.snaker.designer.utils.StringUtils;

/**
 * 流程定义节点模型类
 * @author yuqs
 * @version 1.0
 */
public class Process extends BaseElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3354375166425842591L;
	public static final String PROP_NODES = "nodes";
	public static final String PROP_EXPIRETIME = "expireTime";
	public static final String PROP_INSTANCEURL = "instanceUrl";

	/**
	 * 流程包含的任务模型集合
	 */
	private List<NodeElement> nodes = new ArrayList<NodeElement>();
	private String expireTime;
	private String instanceUrl;
	private String instanceNoClass;
	
	public String getInstanceNoClass() {
		return instanceNoClass;
	}

	public void setInstanceNoClass(String instanceNoClass) {
		this.instanceNoClass = instanceNoClass;
	}

	/**
	 * 添加任务
	 * @param task
	 */
    public void addNodeElement(NodeElement node) {
    	nodes.add(node);
        fireStructureChange(PROP_NODES, nodes);
    }

    /**
     * 删除任务
     * @param task
     */
    public void removeNodeElement(NodeElement node) {
    	nodes.remove(node);
        fireStructureChange(PROP_NODES, nodes);
    }

    /**
     * 返回任务集合
     * @return
     */
	public List<NodeElement> getNodeElements() {
		return nodes;
	}

	/**
	 * 设置任务集合
	 * @param tasks
	 */
	public void setNodeElements(List<NodeElement> nodes) {
		this.nodes = nodes;
		fireStructureChange(PROP_NODES, nodes);
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
		firePropertyChange(PROP_EXPIRETIME, null, expireTime);
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
		firePropertyChange(PROP_INSTANCEURL, null, instanceUrl);
	}

	public String validate() {
		StringBuffer error = new StringBuffer();
		if(StringUtils.isEmpty(name)) {
			error.append("流程的name属性不能为空\n");
		}
		if(StringUtils.isEmpty(displayName)) {
			error.append("流程的displayName属性不能为空\n");
		}
		for(NodeElement node : getNodeElements()) {
			error.append(node.validate());
		}
		return error.toString();
	}
}
