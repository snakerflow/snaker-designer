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
package org.snaker.designer.io.writer;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.snaker.designer.io.Environment;
import org.snaker.designer.model.*;
import org.snaker.designer.utils.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 抽象的模型对象与xml转换
 * @author yuqs
 * @version 1.0
 */
public abstract class AbstractXmlWriter implements XmlWriter {
	@Override
	public void writer(Element root, BaseElement model) {
		Element element = createElement(model, root.getOwnerDocument());
		element.setAttribute(Environment.ATTR_NAME, model.getName());
		element.setAttribute(Environment.ATTR_DISPLAYNAME, model.getDisplayName());
		element.setAttribute(Environment.ATTR_LAYOUT, getLayout(model.getLayout()));
		if(model instanceof NodeElement) {
			NodeElement node = ((NodeElement)model);
			if(StringUtils.isNotEmpty(node.getPreInterceptors())) {
				element.setAttribute(Environment.ATTR_INTERCEPTORS_PRE, node.getPreInterceptors());
			}
			if(StringUtils.isNotEmpty(node.getPostInterceptors())) {
				element.setAttribute(Environment.ATTR_INTERCEPTORS_POST, node.getPostInterceptors());
			}
		}
		addAttributes(model, element);
		addTransitions(model, element.getOwnerDocument(), element);
		addProperties(model, element.getOwnerDocument(), element);
		addChildNodes(model, element.getOwnerDocument(), element);
		root.appendChild(element);
	}
	
	/**
	 * 获取矩形的数字信息
	 * @param layout
	 * @return
	 */
	protected String getLayout(Rectangle layout) {
		StringBuffer buf = new StringBuffer(20);
		buf.append(layout.x).append(",");
		buf.append(layout.y).append(",");
		buf.append(layout.width).append(",");
		buf.append(layout.height);
		return buf.toString();
	}
	
	/**
	 * 添加输出连接的xml
	 * @param element
	 * @return
	 */
	protected void addTransitions(BaseElement model, Document doc, Element element) {
		if(model instanceof NodeElement) {
			NodeElement node = (NodeElement)model;
			for(Transition transition : node.getOutputs()) {
				Element tranE = doc.createElement(Environment.TRANSITION);
				tranE.setAttribute(Environment.ATTR_NAME, transition.getName());
				tranE.setAttribute(Environment.ATTR_TO, transition.getTarget().getName());
				tranE.setAttribute(Environment.ATTR_G, buildSplitConnection(transition));
				tranE.setAttribute(Environment.ATTR_OFFSET, buildLabelOffset(transition));
				if(StringUtils.isNotEmpty(transition.getDisplayName())) {
					tranE.setAttribute(Environment.ATTR_DISPLAYNAME, transition.getDisplayName());
				}
				if(StringUtils.isNotEmpty(transition.getExpr())) {
					tranE.setAttribute(Environment.ATTR_EXPR, transition.getExpr());
				}
				element.appendChild(tranE);
			}
		}
	}
	
	/**
	 * 变迁的折点坐标点
	 * @param t
	 * @return
	 */
	private String buildSplitConnection(Transition t)
	{
		String result = "";
        for (Iterator<Point> iterator = t.getBendpoints().iterator(); iterator.hasNext(); ) {
        	Point point = iterator.next();
            result += point.x + "," + point.y + (iterator.hasNext() ? ";" : "");
        }
		return result;
	}
	
	/**
	 * 变迁的label说明位置
	 * @param t
	 * @return
	 */
	private String buildLabelOffset(Transition t)
	{
		Point offset = t.getOffset();
		if(offset == null) return "0,0";
		return offset.x + "," + offset.y;
	}
	
	/**
	 * 添加属性的xml
	 * @param model
	 * @param doc
	 * @param element
	 */
	protected void addProperties(BaseElement model, Document doc, Element element) {
		if(model instanceof Field) {
			for(Attr attr : model.getAttrs()) {
				Element attrE = doc.createElement(Environment.ATTR);
				attrE.setAttribute(Environment.ATTR_NAME, attr.getName());
				attrE.setAttribute(Environment.ATTR_VALUE, attr.getValue());
				element.appendChild(attrE);
			}
		}
	}
	
	/**
	 * 创建节点元素
	 * @param doc
	 * @return
	 */
	protected Element createElement(BaseElement model, Document doc) {
		if(model instanceof Simple) return doc.createElement(((Simple)model).getNodeName().toLowerCase());
		return doc.createElement(model.getClass().getSimpleName().toLowerCase());
	}
	
	/**
	 * 节点属性
	 * @param buffer
	 * @param element
	 */
	protected void addAttributes(BaseElement model, Element element) {}
	
	/**
	 * 添加子节点
	 * @param model
	 * @param doc
	 * @param element
	 */
	protected void addChildNodes(BaseElement model, Document doc, Element element){}
}
