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
package org.snaker.designer.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.model.Field;

/**
 * field图形
 * @author yuqs
 * @version 1.0
 */
public class FieldFigure extends Figure {
	private Field model;

	private int placeHolder = 1;

	private Component component;

	public FieldFigure(Field field) {
		this.model = field;
		this.component = ConfigManager.getComponentByGroupAndType(
				ConfigManager.COMPONENT_TYPE_FIELD, field.getType());
		setLayoutManager(new FlowLayout());

		setForegroundColor(ColorConstants.blue);
		setBackgroundColor(ColorConstants.blue);
		setBorder(new FieldBorder(field.getDisplayName()));
		setOpaque(true);
		SimpleLabel sl = new SimpleLabel();
		sl.setIcon(getImageByType());
		add(sl, ToolbarLayout.ALIGN_CENTER);

		placeHolder = ConfigManager.getPlaceHolderValue(component);
		int iWidth = 200 * placeHolder + 1;
		setSize(iWidth, 22);
	}

	public Field getModel() {
		return this.model;
	}

	private Image getImageByType() {
		return ImageDescriptor.createFromFile(FieldFigure.class,
				this.component.getIconSmall()).createImage();
	}

	public void setText(String text) {
		FieldBorder border = (FieldBorder) getBorder();
		border.setText(text);
	}
}
