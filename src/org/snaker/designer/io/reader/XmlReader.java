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
package org.snaker.designer.io.reader;

import org.snaker.designer.model.BaseElement;
import org.w3c.dom.Element;

/**
 * 节点解析接口
 * @author yuqs
 * @version 1.0
 */
public interface XmlReader {
	/**
	 * 节点dom元素解析方法，由实现类完成解析
	 * @param element
	 */
	public BaseElement parse(Element element);
}
