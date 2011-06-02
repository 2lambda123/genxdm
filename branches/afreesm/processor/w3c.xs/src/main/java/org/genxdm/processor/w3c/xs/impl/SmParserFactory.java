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
package org.genxdm.processor.w3c.xs.impl;

import org.genxdm.exceptions.PreCondition;
import org.genxdm.processor.w3c.xs.SmRegExCompiler;
import org.genxdm.typed.types.AtomBridge;
import org.genxdm.xs.components.ComponentProvider;
import org.genxdm.xs.resolve.SchemaCatalog;
import org.genxdm.xs.resolve.CatalogResolver;

final class SmParserFactory<A>
{
	private SchemaCatalog m_catalog = DefaultSmCatalog.SINGLETON;
	private CatalogResolver m_resolver = DefaultSmResolver.SINGLETON;
	private SmRegExCompiler m_regexc = new RegExCompilerJDK();
	private boolean m_processRepeatedNamespaces = true;

	public SmParserFactory()
	{
	}

	public void setCatalog(final SchemaCatalog catalog)
	{
		m_catalog = PreCondition.assertArgumentNotNull(catalog, "catalog");
	}

	public void setResolver(final CatalogResolver resolver)
	{
		m_resolver = PreCondition.assertArgumentNotNull(resolver, "resolver");
	}

	public void setRegExCompiler(final SmRegExCompiler regexc)
	{
		m_regexc = PreCondition.assertArgumentNotNull(regexc, "regexc");
	}

	/**
	 * Sets processRepeatedNamespaces policy for all parsers created by this factory. If true, schemas whose targetnamespaces have been previsouly encountered by a given parser (this situation can occur when using imports, includes, or redefines) will be parsed. If
	 * false those schemas will be ignored; hence, their components will not be loaded.
	 * <p/>
	 * <em>The default value is true.</em>
	 * 
	 * @param processRepeatedNamespaces
	 */
	public void setProcessRepeatedNamespaces(final boolean processRepeatedNamespaces)
	{
		m_processRepeatedNamespaces = processRepeatedNamespaces;
	}

	public XMLParserImpl<A> newInstance(final ComponentProvider<A> cache, final AtomBridge<A> atomBridge)
	{
		final XMLParserImpl<A> parser = new XMLParserImpl<A>(cache, atomBridge);
		parser.setCatalog(m_catalog);
		parser.setResolver(m_resolver);
		parser.setRegExCompiler(m_regexc);
		parser.setProcessRepeatedNamespaces(m_processRepeatedNamespaces);
		return parser;
	}
}
