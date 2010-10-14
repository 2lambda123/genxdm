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
package org.gxml.processor.w3c.xs.exception;

import javax.xml.namespace.QName;

import org.genxdm.exceptions.PreCondition;
import org.genxdm.xs.resolve.SmLocation;

@SuppressWarnings("serial")
public final class CvcUnexpectedTextInEmptyContentException extends SmComplexTypeException
{
	private final String m_text;

	public CvcUnexpectedTextInEmptyContentException(final QName elementName, final String text, final SmLocation location)
	{
		super(PART_CONTENT_TYPE_IS_EMPTY, elementName, location);
		m_text = PreCondition.assertArgumentNotNull(text, "text");
	}

	public String getText()
	{
		return m_text;
	}

	@Override
	public String getMessage()
	{
		return "Unexpected text, '" + m_text + "', in empty content model for element information item " + getElementName() + ".";
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof CvcUnexpectedTextInEmptyContentException)
		{
			final CvcUnexpectedTextInEmptyContentException other = (CvcUnexpectedTextInEmptyContentException)obj;
			return m_text.equals(other.m_text);
		}
		else
		{
			return false;
		}
	}
}
