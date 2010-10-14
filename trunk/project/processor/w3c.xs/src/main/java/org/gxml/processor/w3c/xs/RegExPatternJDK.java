/**
 * Copyright (c) 2009-2010 TIBCO Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gxml.processor.w3c.xs;

import java.util.regex.Pattern;

import org.genxdm.exceptions.PreCondition;
import org.genxdm.xs.facets.SmRegExPattern;

final class RegExPatternJDK implements SmRegExPattern
{
	private final Pattern m_pattern;

	public RegExPatternJDK(final Pattern pattern)
	{
		m_pattern = PreCondition.assertArgumentNotNull(pattern, "pattern");
	}

	public boolean matches(final String input)
	{
		PreCondition.assertArgumentNotNull(input, "input");
		return m_pattern.matcher(input).matches();
	}
}
