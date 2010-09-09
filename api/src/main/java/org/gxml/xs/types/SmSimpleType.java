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
package org.gxml.xs.types;

import java.util.List;

import org.gxml.xs.components.SmEnumeration;
import org.gxml.xs.enums.SmWhiteSpacePolicy;
import org.gxml.xs.exceptions.SmDatatypeException;
import org.gxml.xs.facets.SmFacet;
import org.gxml.xs.facets.SmFacetKind;
import org.gxml.xs.facets.SmPattern;
import org.gxml.xs.resolve.SmPrefixResolver;

/**
 * A Simple Type definition.
 * 
 * @param <A>
 *            The atom handle.
 */
public interface SmSimpleType<A> extends SmSimpleMarkerType<A>
{
	Iterable<SmEnumeration<A>> getEnumerations();

	SmFacet<A> getFacetOfKind(SmFacetKind facetKind);

	Iterable<SmFacet<A>> getFacets();

	SmNativeType getNativeType();

	SmSimpleType<A> getNativeTypeDefinition();

	/**
	 * Returns the pattern facets for this derivation step, may be <code>null</code>.
	 */
	Iterable<SmPattern> getPatterns();

	SmWhiteSpacePolicy getWhiteSpacePolicy();

	boolean hasEnumerations();

	boolean hasFacetOfKind(SmFacetKind facetKind);

	boolean hasFacets();

	/**
	 * Determines whether this derivation step has pattern facets.
	 */
	boolean hasPatterns();

	/**
	 * Determines whether the {variety} property is <b>atomic</b>.
	 */
	boolean isAtomicType();

	/**
	 * Returns whether this type is derived from xs:ID
	 */
	boolean isID();

	/**
	 * Returns whether this type is derived from xs:IDREF
	 */
	boolean isIDREF();

	/**
	 * Returns whether this type is derived from xs:IDREFS
	 */
	boolean isIDREFS();

	/**
	 * Determines whether the {variety} property is <b>list</b>.
	 */
	boolean isListType();

	/**
	 * Determines whether the {variety} property is <b>union</b>.
	 */
	boolean isUnionType();

	/**
	 * Normalize this type (simple types only). This applies the whitespace pseudo-facet for the type in question. List
	 * does collapse processing; atomic types delegate to the atom manager's own normalization routines (typically also
	 * collapse, but possibly replace or preserve).
	 * 
	 * @param initialValue
	 *            The lexical value (in XML Schema terms, the initial value). Must not be null.
	 * @return A normalized string, which may be identical to the initial value, and is never null (in XML Schema terms,
	 *         the normalized value).
	 */
	String normalize(String initialValue);

	List<A> validate(List<? extends A> value) throws SmDatatypeException;

	/**
	 * Determines whether the supplied string literal is valid with respect to this type definition and, if successful,
	 * calculates the value space representation of the literal.
	 * <p/>
	 * Applies only to simple type definitions.
	 * <p/>
	 * The lexical facets of the value are first checked and then it is parsed and expanded to bring it into the value
	 * space defined by this type. Validation then proceeds by checking facets appropriate to this type.
	 * 
	 * @param initialValue
	 *            The string literal to be validated.
	 * @throws SmDatatypeException
	 *             If the string literal is not valid with respect to this type definition.
	 */
	List<A> validate(String initialValue) throws SmDatatypeException;

	List<A> validate(String initialValue, final SmPrefixResolver resolver) throws SmDatatypeException;
}
