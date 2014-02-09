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

import org.eclipse.draw2d.geometry.Point;

/**
 * 变迁模型类
 * @author yuqs
 * @version 1.0
 */
public class Transition extends BaseElement 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1100956209119361413L;
	
	public static final String PROP_BENDPOINT = "BENDPOINTS";
	public static final String PROP_OFFSET = "OFFSET";
	public static final String PROP_TO = "TO";
	public static final String PROP_EXPR = "expr";
	/**
	 * 变迁的源节点
	 */
	private NodeElement source;
	/**
	 * 变迁的目标节点
	 */
	private NodeElement target;
	/**
	 * 目标节点name
	 */
	private String to;
	/**
	 * 变迁的表达式，一般用于decision
	 */
	private String expr;
	/**
	 * 变迁的偏移位置
	 */
	private Point offset;
	/**
	 * 变迁的折线点集合
	 */
	private List<Point> bendpoints = new ArrayList<Point>();
	
	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
		firePropertyChange(PROP_OFFSET, null, null);
	}

	public List<Point> getBendpoints()
	{
		return bendpoints;
	}

	public void setBendpoints(List<Point> bendpoints)
	{
		this.bendpoints = bendpoints;
	}

	public void addBendpoint(int index, Point point)
	{
		getBendpoints().add(index, point);
		firePropertyChange(PROP_BENDPOINT, null, null);
	}

	public void removeBendpoint(int index) {
		getBendpoints().remove(index);
		firePropertyChange(PROP_BENDPOINT, null, null);
	}
	
    public void replaceBendpoint(int index, Point point) {
        bendpoints.set(index, point);
        firePropertyChange(PROP_BENDPOINT, null, null);
    }

	public NodeElement getSource() {
		return source;
	}

	public void setSource(NodeElement source) {
		this.source = source;
	}

	public NodeElement getTarget() {
		return target;
	}

	public void setTarget(NodeElement target) {
		this.target = target;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
		firePropertyChange(PROP_TO, null, to);
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
		firePropertyChange(PROP_EXPR, null, expr);
	}
}
