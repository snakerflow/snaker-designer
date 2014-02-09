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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.io.Environment;
import org.snaker.designer.model.Attr;
import org.snaker.designer.model.BaseElement;
import org.snaker.designer.model.NodeElement;
import org.snaker.designer.model.Transition;
import org.snaker.designer.utils.ClassUtils;
import org.snaker.designer.utils.XmlUtils;
import org.w3c.dom.Element;

/**
 * 抽象dom节点解析类
 * 完成通用的属性、变迁解析
 * @author yuqs
 * @version 1.0
 */
public abstract class AbstractXmlReader implements XmlReader {
	/**
	 * 模型对象
	 */
	protected NodeElement model;
	
	/**
	 * 实现NodeParser接口的parse函数
	 * 由子类产生各自的模型对象，设置常用的名称属性，并且解析子节点transition，构造TransitionModel模型对象
	 */
	public BaseElement parse(Element element) {
		BaseElement model = newModel(element);
		model.setName(element.getAttribute(Environment.ATTR_NAME));
		model.setDisplayName(element.getAttribute(Environment.ATTR_DISPLAYNAME));
		model.setLayout(getRectangle(element.getAttribute(Environment.ATTR_LAYOUT)));
		if(model instanceof NodeElement) {
			NodeElement node = (NodeElement)model;
			List<Element> transitions = XmlUtils.elements(element, Environment.TRANSITION);
			for(Element te : transitions) {
				Transition transition = new Transition();
				transition.setName(te.getAttribute(Environment.ATTR_NAME));
				transition.setDisplayName(te.getAttribute(Environment.ATTR_DISPLAYNAME));
				transition.setTo(te.getAttribute(Environment.ATTR_TO));
				transition.setExpr(te.getAttribute(Environment.ATTR_EXPR));
				transition.setOffset(buildLabelOffsetPoint(te.getAttribute(Environment.ATTR_OFFSET)));
				transition.setBendpoints(buildBendPoints(te.getAttribute(Environment.ATTR_G)));
				transition.setSource(node);
				node.getOutputs().add(transition);
			}
			node.setPreInterceptors(element.getAttribute(Environment.ATTR_INTERCEPTORS_PRE));
			node.setPostInterceptors(element.getAttribute(Environment.ATTR_INTERCEPTORS_POST));
		}
		
		List<Element> attrs = XmlUtils.elements(element, Environment.ATTR);
		for(Element attrE : attrs) {
			Attr attr = new Attr();
			attr.setName(attrE.getAttribute(Environment.ATTR_NAME));
			attr.setValue(attrE.getAttribute(Environment.ATTR_VALUE));
			model.addAttr(attr);
		}
		
		parseNode(model, element);
		return model;
	}
	
	private Point buildLabelOffsetPoint(String s)
	{
		if(s == null || s.trim().length() == 0) return null;
		String[] xy = s.split(",");
		return new Point(new Integer(xy[0]), new Integer(xy[1]));
	}
	
	private List<Point> buildBendPoints(String s)
	{
        List<Point> result = new ArrayList<Point>();
        if (s == null || s.trim().length() == 0) {
            return result;
        }
        String[] bendpoints = s.split(";");
        for (String bendpoint: bendpoints) {
            String[] xy = bendpoint.split(",");
            result.add(new Point(new Integer(xy[0]), new Integer(xy[1])));
        }
        return result;
	}
	
	private Rectangle getRectangle(String v) {
		if(v != null && v.trim().length() > 0)
		{
			String[] ps = v.split(",");
			if(ps.length ==4)
			{
				int x = Integer.parseInt(ps[0]);
				int y = Integer.parseInt(ps[1]);
				int width = Integer.parseInt(ps[2]);
				int height = Integer.parseInt(ps[3]);
				return new Rectangle(x, y, width, height);
			}
		}
		return new Rectangle(0, 0, 0, 0);
	}
	
	/**
	 * 子类可覆盖此方法，完成特定的解析
	 * @param model
	 * @param element
	 */
	protected void parseNode(BaseElement model, Element element) {
		
	}
	
	/**
	 * 抽象方法，由子类产生各自的模型对象
	 * @return
	 */
	protected BaseElement newModel(Element element) {
		String nodeName = element.getNodeName();
		Component component = ConfigManager.getComponentByName(nodeName);
		return (BaseElement)ClassUtils.newInstance(component.getClazz());
	}
}
