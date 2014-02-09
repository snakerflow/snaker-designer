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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * 所有模型元素抽象基类，主要提供属性改变监听器
 * @author yuqs
 * @version 1.0
 */
public abstract class Element implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8601132591065517723L;

	PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.addPropertyChangeListener(l);
	}

	protected void firePropertyChange(String prop, Object old, Object newValue) {
		listeners.firePropertyChange(prop, old, newValue);
	}

	protected void fireStructureChange(String prop, Object child) {
		listeners.firePropertyChange(prop, null, child);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.removePropertyChangeListener(l);
	}

}