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
 * 节点模型元素的基类
 * @author yuqs
 * @version 1.0
 */
public class NodeElement extends BaseElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6579924821230727291L;
    public static final String PROP_INPUTS = "INPUTS";
    public static final String PROP_OUTPUTS = "OUTPUTS";
    public static final String PROP_PRE_INTERCEPTORS = "PRE_INTERCEPTORS";
    public static final String PROP_POST_INTERCEPTORS = "POST_INTERCEPTORS";
    /**
     * 节点输入变迁
     */
	private List<Transition> inputs = new ArrayList<Transition>();
	/**
	 * 节点输出变迁
	 */
	private List<Transition> outputs = new ArrayList<Transition>();
	/**
	 * 局部前置拦截器
	 */
	private String preInterceptors;
	/**
	 * 局部后置拦截器
	 */
	private String postInterceptors;
	/**
	 * 添加输入变迁
	 * @param connection
	 */
    public void addInput(Transition connection) {
        this.inputs.add(connection);
        fireStructureChange(PROP_INPUTS, connection);
    }

    /**
     * 添加输出变迁
     * @param connection
     */
    public void addOutput(Transition connection) {
        this.outputs.add(connection);
        fireStructureChange(PROP_OUTPUTS, connection);
    }
    
    /**
     * 删除输入变迁
     * @param connection
     */
	public void removeInput(Transition connection) {
        this.inputs.remove(connection);
        fireStructureChange(PROP_INPUTS, connection);
    }

	/**
	 * 删除输出变迁
	 * @param connection
	 */
    public void removeOutput(Transition connection) {
        this.outputs.remove(connection);
        fireStructureChange(PROP_OUTPUTS, connection);
    }
    
    /**
     * 返回輸入变迁
     * @return
     */
	public List<Transition> getInputs() {
		return inputs;
	}
	/**
	 * 设置输入变迁
	 * @param inputs
	 */
	public void setInputs(List<Transition> inputs) {
		this.inputs = inputs;
	}

	/**
	 * 返回输出变迁
	 * @return
	 */
	public List<Transition> getOutputs() {
		return outputs;
	}

	/**
	 * 设置输出变迁
	 * @param outputs
	 */
	public void setOutputs(List<Transition> outputs) {
		this.outputs = outputs;
	}
	
	public String validate() {
		StringBuffer buffer = new StringBuffer();
		if(StringUtils.isEmpty(name)) {
			buffer.append("节点的name属性不能为空\n");
		}

		return buffer.toString();
	}

	public String getPreInterceptors() {
		return preInterceptors;
	}

	public void setPreInterceptors(String preInterceptors) {
		this.preInterceptors = preInterceptors;
		firePropertyChange(PROP_PRE_INTERCEPTORS, null, preInterceptors);
	}

	public String getPostInterceptors() {
		return postInterceptors;
	}

	public void setPostInterceptors(String postInterceptors) {
		this.postInterceptors = postInterceptors;
		firePropertyChange(PROP_POST_INTERCEPTORS, null, postInterceptors);
	}
}
