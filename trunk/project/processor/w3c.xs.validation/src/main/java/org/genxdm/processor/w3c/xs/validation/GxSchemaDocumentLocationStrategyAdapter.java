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
package org.genxdm.processor.w3c.xs.validation;

import java.io.IOException;
import java.net.URI;

import org.genxdm.processor.w3c.xs.validation.api.VxSchemaDocumentLocationStrategy;


public final class GxSchemaDocumentLocationStrategyAdapter implements VxSchemaDocumentLocationStrategy
{
	private final GxSchemaDocumentLocationStrategy schemaDocumentLocationStrategy;

	public GxSchemaDocumentLocationStrategyAdapter(final GxSchemaDocumentLocationStrategy schemaDocumentLocationStrategy)
	{
		this.schemaDocumentLocationStrategy = schemaDocumentLocationStrategy;
	}

	public void noNamespaceSchemaLocation(final URI baseURI, final URI schemaLocation) throws IOException
	{
		if (null != schemaDocumentLocationStrategy)
		{
			schemaDocumentLocationStrategy.noNamespaceSchemaLocation(baseURI, schemaLocation);
		}
	}

	public void schemaLocation(final URI baseURI, final URI namespace, final URI schemaLocation) throws IOException
	{
		if (null != schemaDocumentLocationStrategy)
		{
			schemaDocumentLocationStrategy.schemaLocation(baseURI, namespace, schemaLocation);
		}
	}
}