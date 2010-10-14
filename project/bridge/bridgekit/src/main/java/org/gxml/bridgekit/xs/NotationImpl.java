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
package org.gxml.bridgekit.xs;

import java.net.URI;

import javax.xml.namespace.QName;

import org.genxdm.xs.components.SmNotation;
import org.genxdm.xs.enums.SmScopeExtent;

public final class NotationImpl<A> extends NamedComponentImpl<A> implements SmNotation<A>
{
	private final String publicId;
	private final URI systemId;

	public NotationImpl(final QName name, final String publicId, final URI systemId)
	{
		super(name, false, SmScopeExtent.Global);
		this.publicId = publicId;
		this.systemId = systemId;
	}

	public String getPublicId()
	{
		return publicId;
	}

	public URI getSystemId()
	{
		return systemId;
	}

	public String toString()
	{
		return "notation name=" + getName() + " public=" + getPublicId() + " system=" + getSystemId();
	}
}
