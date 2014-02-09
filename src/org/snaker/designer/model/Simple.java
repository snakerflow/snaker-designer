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

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuqs
 * @version 1.0
 */
public class Simple extends NodeElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6175476298948033990L;
	public static final String PROP_ATTR = "attributes";
	
	private String nodeName;
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	public Simple() {
	}
	
	public Simple(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public void setAttribute(String key, String value) {
		this.attributes.put(key, value);
		firePropertyChange(PROP_ATTR, null, value);
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
