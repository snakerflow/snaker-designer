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
package org.snaker.designer.palette;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.snaker.designer.config.Component;
import org.snaker.designer.config.ConfigManager;
import org.snaker.designer.figures.TaskFigure;
import org.snaker.designer.model.ModelCreationFactory;
import org.snaker.designer.model.Simple;
import org.snaker.designer.model.Transition;

/**
 * 工具板工厂类
 * 
 * @author yuqs
 * @version 1.0
 */
public class PaletteFactory {
	public PaletteRoot getPaletteRoot(String type) {
		PaletteRoot palette = new PaletteRoot();
		palette.addAll(createCategories(palette, type));
		return palette;
	}

	private List<PaletteEntry> createCategories(PaletteRoot root, String type) {
		List<PaletteEntry> categories = new ArrayList<PaletteEntry>();
		categories.add(createControlGroup(root));
		if (type.equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_FIELD)) {
			categories.add(createFieldDrawer());
		} else if (type.equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_PROCESS)) {
			categories.add(createTaskDrawer(root));
		}
		return categories;
	}

	private PaletteContainer createTaskDrawer(PaletteRoot root) {
		String group = "流程组件";

		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();
		PaletteDrawer drawer = new PaletteDrawer(group,
				ImageDescriptor.createFromFile(TaskFigure.class,
						"icons/home.png"));

		ConnectionCreationToolEntry connectionToolEntry = new ConnectionCreationToolEntry(
				"transition", "创建连接", new ModelCreationFactory(null,
						Transition.class), ImageDescriptor.createFromFile(
						TaskFigure.class, "icons/flow_sequence.png"),
				ImageDescriptor.createFromFile(TaskFigure.class,
						"icons/flow_sequence.png"));

		drawer.add(connectionToolEntry);

		List<Component> listComp = ConfigManager.getComponents();

		for (int i = 0; i < listComp.size(); i++) {
			Component comp = (Component) listComp.get(i);
			if (!validate(ConfigManager.COMPONENT_TYPE_PROCESS, comp)) {
				continue;
			}
			PaletteEntry paletteEntry = createCombinedEntry(comp);
			if (paletteEntry == null) {
				continue;
			}
			entries.add(paletteEntry);
		}

		drawer.addAll(entries);
		return drawer;
	}

	private PaletteContainer createControlGroup(PaletteRoot root) {
		PaletteGroup controlGroup = new PaletteGroup("Control Group");

		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();

		ToolEntry tool = new PanningSelectionToolEntry();
		entries.add(tool);
		root.setDefaultEntry(tool);

		tool = new MarqueeToolEntry();
		entries.add(tool);

		PaletteSeparator sep = new PaletteSeparator(
				"org.snaker.designer.editor.separator");
		sep.setUserModificationPermission(1);
		entries.add(sep);

		controlGroup.addAll(entries);
		return controlGroup;
	}

	private PaletteContainer createFieldDrawer() {
		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();
		String group = "表单控件";
		PaletteDrawer drawer = new PaletteDrawer(group,
				ImageDescriptor.createFromFile(TaskFigure.class,
						"icons/home.png"));

		List<Component> listComp = ConfigManager.getComponents();

		for (int i = 0; i < listComp.size(); i++) {
			Component comp = (Component) listComp.get(i);
			if (!validate(ConfigManager.COMPONENT_TYPE_FIELD, comp)) {
				continue;
			}
			PaletteEntry paletteEntry = createCombinedEntry(comp);
			if (paletteEntry == null) {
				continue;
			}
			entries.add(paletteEntry);
		}

		drawer.addAll(entries);
		drawer.setInitialState(2);
		return drawer;
	}

	private boolean validate(String groupName, Component comp) {
		if (comp.getGroup() == null || !comp.getGroup().equalsIgnoreCase(groupName)) {
			return false;
		}

		return (comp.getVisible() == null)
				|| (!"false".equalsIgnoreCase(comp.getVisible()));
	}

	private CombinedTemplateCreationEntry createCombinedEntry(
			Component compponent) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(compponent.getClazz());
		} catch (Exception e) {
			clazz = Simple.class;
		}
		if (clazz == null) return null;

		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				compponent.getDisplayName(), compponent.getDescript(), clazz,
				new ModelCreationFactory(compponent, clazz),
				ImageDescriptor.createFromFile(TaskFigure.class,
						compponent.getIconSmall()),
				ImageDescriptor.createFromFile(TaskFigure.class,
						compponent.getIconLarge()));
		return combined;
	}
}
