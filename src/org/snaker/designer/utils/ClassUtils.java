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

/**
 * 类操作帮助类
 * @author yuqs
 * @version 1.0
 */
public class ClassUtils {
    /**
     * 根据指定的类名称加载类
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ex) {
                try {
                    return ClassLoader.class.getClassLoader().loadClass(className);
                } catch (ClassNotFoundException exc) {
                    throw exc;
                }
            }
        }
    }
    
    /**
     * 实例化指定的类名称（全路径）
     * @param clazzStr
     * @return
     * @throws Exception
     */
    public static Object newInstance(String clazzStr) {
        try {
            Class<?> clazz = loadClass(clazzStr);
            return instantiate(clazz);
        } catch (Exception ex) {
        }
        return null;
    }
    
    /**
     * 根据类的class实例化对象
     * @param clazz
     * @return
     */
	public static <T> T instantiate(Class<T> clazz) {
		if (clazz.isInterface()) {
			return null;
		}
		try {
			return clazz.newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
