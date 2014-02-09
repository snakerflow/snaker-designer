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
package org.snaker.designer.utils;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * bean的工具类
 * @author yuqs
 * @version 1.0
 */
public class BeanUtil {
	/**
	 * 获得给定对象与属性名所对应的值
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @return 返回该属性对应的值
	 */
	public static Object getPropertyValue(Object bean, String propertyName)
			throws Exception {
		try {
			return PropertyUtils.getProperty(bean, propertyName);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 判断给定的bean下是否包括给定的属性名,属性名可以不限制深度.
	 * 例如给定Bean有个部门属性,名为org,org.manager.id是深度属性名
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @return 如果存在给定的属性名则返回true,否则返回false
	 */
	public static boolean hasPropertyName(Object bean, String propertyName) {
		String[] propertyNames = StringUtils.strToStrArray(propertyName, ".");
		Class<?> clzz = bean.getClass();
		Field field = null;

		for (int i = 0; i < propertyNames.length; i++) {

			// 类型中是否有给定名称的属性
			field = hasClassField(clzz, propertyNames[i]);
			if (field == null)
				return false;

			clzz = field.getType();
		}

		return true;
	}

	public static Class<?> getProperyClass(Object bean, String propertyName) {
		try {
			return PropertyUtils.getPropertyType(bean, propertyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 递归(直至调用的Object)判断clazz中是否有给定域名(fieldName)的域,如果有则返回对该域对应的
	 * @param clzz
	 * @param fieldName
	 * @return
	 */
	private static Field hasClassField(Class<?> clazz, String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (Exception e) {
			if (clazz.equals(Object.class))
				return null;
			return hasClassField(clazz.getSuperclass(), fieldName); // �ݹ鴦��,ȥ�����������
		}
	}

	/**
	 * 获得给定对象与属性名所对应的值
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @return 返回该属性对应的值,并将该值造型为String类型
	 */
	public static String getPropertyValueToStr(Object bean, String propertyName)
			throws Exception {
		Object val = getPropertyValue(bean, propertyName);

		if (val == null)
			return null;

		return val.toString();
	}

	/**
	 * 为给定对象属性设置值
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @param value 值
	 */
	public static void setPropertyValue(Object bean, String propertyName,
			Object value) throws Exception {
		try {
			PropertyUtils.setProperty(bean, propertyName, value);
		} catch (Exception e) {
			throw e;
		}
	}
}