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

import org.genxdm.exceptions.PreCondition;

final class XMLAttributeUse<A>
{
	private final boolean m_isRequired;
	private final XMLAttribute<A> m_attribute;
	private final XMLValueConstraint m_valueConstraint;

	public XMLAttributeUse(final boolean isRequired, final XMLAttribute<A> attribute, final XMLValueConstraint valueConstraint)
	{
		m_isRequired = isRequired;
		m_attribute = PreCondition.assertArgumentNotNull(attribute);
		m_valueConstraint = valueConstraint;
	}

	public XMLAttribute<A> getDeclaration()
	{
		return m_attribute;
	}

	public boolean isRequired()
	{
		return m_isRequired;
	}

	public XMLValueConstraint getValueConstraint()
	{
		return m_valueConstraint;
	}
}