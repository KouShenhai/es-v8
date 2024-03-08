/*
 * Copyright (c) 2022-2024 KCloud-Platform-Alibaba Author or Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.laokoutech.demoes8.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * 字符串工具类.
 *
 * @author laokou
 */
public class StringUtil {

	public static boolean isNotEmpty(String str) {
		return hasText(str);
	}

	public static String collectionToDelimitedString(Collection<?> coll, String delim) {
		return StringUtils.collectionToDelimitedString(coll, delim);
	}

	public static Long parseLong(String str) {
		if (isEmpty(str)) {
			return null;
		}
		return Long.parseLong(str);
	}

	public static String empty(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return str;
	}

	public static Set<String> commaDelimitedListToSet(String str) {
		return StringUtils.commaDelimitedListToSet(str);
	}

	public static boolean isEmpty(String str) {
		return !hasText(str);
	}

	public static boolean isEmpty(CharSequence sequence) {
		return !hasText(sequence);
	}

	public static boolean isNotEmpty(CharSequence sequence) {
		return hasText(sequence);
	}

	public static String removeStart(String str, String remove) {
		if (!isEmpty(str) && !isEmpty(remove)) {
			return str.startsWith(remove) ? str.substring(remove.length()) : str;
		}
		else {
			return str;
		}
	}

	public static boolean allNotNull(Object... values) {
		if (ObjectUtil.isNull(values)) {
			return false;
		}
		else {
			for (Object val : values) {
				if (ObjectUtil.isNull(val)) {
					return false;
				}
			}
			return true;
		}
	}

	public static String substringBetween(String str, String open, String close) {
		if (allNotNull(str, open, close)) {
			int start = str.indexOf(open);
			if (start != -1) {
				int end = str.indexOf(close, start + open.length());
				if (end != -1) {
					return str.substring(start + open.length(), end);
				}
			}
		}
		return null;
	}

	public static boolean hasText(@Nullable String str) {
		return StringUtils.hasText(str);
	}

	public static boolean hasText(@Nullable CharSequence sequence) {
		return StringUtils.hasText(sequence);
	}

}
