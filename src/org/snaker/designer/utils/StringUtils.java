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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

import org.eclipse.jdt.internal.corext.util.JavaConventionsUtil;

public class StringUtils {

	/**
	 * 前导标识
	 */
	public static final int BEFORE = 1;

	/**
	 * 后继标识
	 */
	public static final int AFTER = 2;

	public static final String DEFAULT_PATH_SEPARATOR = ",";

	/**
	 * 将一个中间带逗号分隔符的字符串转换为字符型数组对象
	 * @param str 待转换的符串对象
	 * @return 字符型数组
	 */
	public static String[] strToStrArray(String str) {
		return strToStrArrayManager(str, DEFAULT_PATH_SEPARATOR);
	}

	/**
	 * 将字符串对象按给定的分隔符separator转象为字符型数组对象
	 * @param str 待转换的符串对象
	 * @param separator 字符型分隔符
	 * @return 字符型数组
	 */
	public static String[] strToStrArray(String str, String separator) {
		return strToStrArrayManager(str, separator);
	}

	/**
	 * 将字符串对象按给定的分隔符separator转象为字符型数组对象
	 * @param str
	 * @param separator
	 * @return
	 */
	private static String[] strToStrArrayManager(String str, String separator) {
		StringTokenizer strTokens = new StringTokenizer(str, separator);
		String[] strArray = new String[strTokens.countTokens()];
		int i = 0;

		while (strTokens.hasMoreTokens()) {
			strArray[i] = strTokens.nextToken().trim();
			i++;
		}

		return strArray;
	}

	/**
	 * 字符串替换
	 * @param str 源字符串
	 * @param pattern 待替换的字符串
	 * @param replace 替换为的字符串
	 * @return
	 */
	public static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));

		return result.toString();
	}

	/**
	 * 首字母大写
	 * @param string
	 * @return
	 */
	public static String upperFrist(String string) {
		if (string == null)
			return null;
		String upper = string.toUpperCase();
		return upper.substring(0, 1) + string.substring(1);
	}

	/**
	 * 首字母小写
	 * @param string
	 * @return
	 */
	public static String lowerFrist(String string) {
		if (string == null)
			return null;
		String lower = string.toLowerCase();
		return lower.substring(0, 1) + string.substring(1);
	}

	/**
	 * 将一个日期类型的对象，转换为指定格式的字符串
	 * @param date 待转换的日期
	 * @param format 转换为字符串的相应格式 例如：DateToStr(new Date() ,"yyyy.MM.dd G 'at' hh:mm:ss a zzz");
	 * @return
	 */
	public static String DateToStr(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 生成一个指定长度的随机字符串
	 * @param length 返回的字符串长度
	 * @return 返回一个随机
	 */
	public static String randomString(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = ("abcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(51)];
		}
		return new String(randBuffer);
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 判断字符串是否为非空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断标识符是否为关键字
	 * @param identifier
	 * @return
	 */
	public static boolean isKeyValue(String identifier) {
		char[] ch = identifier.toCharArray();

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!isChinese(c))
				continue;
			return true;
		}
		return JavaConventionsUtil.validateFieldName(identifier, null)
				.getSeverity() == 4;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
				|| (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
	}
}
