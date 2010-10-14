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
package org.gxml.processor.w3c.xs.validation.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.genxdm.typed.types.AtomBridge;
import org.genxdm.xs.constraints.SmIdentityConstraint;
import org.genxdm.xs.constraints.SmRestrictedXPath;
import org.genxdm.xs.exceptions.SmAbortException;
import org.genxdm.xs.exceptions.SmExceptionHandler;
import org.genxdm.xs.resolve.SmLocation;
import org.genxdm.xs.types.SmSimpleType;
import org.genxdm.xs.types.SmType;

/**
 * {@link IdentityScope} corresponds to an identity-constraint. An {@link IdentityScope} is created for each constraint
 * each time an element information item is associated with a declaration containing the identity constraint. That
 * element information item represents the scope over which the identity constraint applies. <br/>
 * Not surprisingly, this evaluation class mimicks the identity-constraint by having a selector and a list of fields
 * that are of type {@link IdentitySelector}. The selector is established immediately (upon initialization)
 */
abstract class IdentityScope<A>
{
	private final SmIdentityConstraint<A> m_constraint;
	protected final SmExceptionHandler m_errorHandler;

	/**
	 * The element information that is the context element for the xs:selector.
	 */
	@SuppressWarnings("unused")
	private final int m_elementIndex;

	/**
	 * There is one {selector} evaluator for this identity scope. However, the {selector} evaluator matches zero-or-more
	 * target nodes (element information items) and for each element information item we will have a set of {field}
	 * evaluators. So from a scope perspective the {field} evaluators are part of a map keyed by the element information
	 * item.
	 */
	private final IdentitySelector<A> m_selectorEval;
	final HashMap<Integer, Integer> m_boundFields = new HashMap<Integer, Integer>();
	final HashMap<Integer, ArrayList<IdentityField<A>>> m_fieldEvals = new HashMap<Integer, ArrayList<IdentityField<A>>>();

	/**
	 * A map from a key (tuple of values) to Boolean.TRUE | list of {@link IdentityDanglingReference}.
	 */
	// protected HashMap<IdentityTuple<A>, IdentityVariant<A>>
	// m_qualifiedTargets;
	protected final SmLocation m_location;

	protected IdentityScope(final int elementIndex, final SmIdentityConstraint<A> constraint, final SmExceptionHandler errorHandler, final SmLocation location)
	{
		m_elementIndex = elementIndex;
		m_constraint = PreCondition.assertArgumentNotNull(constraint);
		m_errorHandler = PreCondition.assertArgumentNotNull(errorHandler);

		m_location = location;

		m_selectorEval = new IdentitySelector<A>(this, m_constraint.getSelector());
	}

	/**
	 * Called when its full complement of keys have been completed.
	 * 
	 * @param elementIndex
	 *            The index of the element that is the context for xs:field evaluation.
	 * @param keyValues
	 *            The key values.
	 */
	protected abstract void onKeysComplete(final ArrayList<IdentityKey<A>> keyValues, final int elementIndex) throws SmAbortException;

	/**
	 * Called when the selector goes out of scope.
	 * 
	 * @param elementIndex
	 *            The index of the element that is the context for xs:field evaluation.
	 */
	protected abstract void onScopeEnd(final int elementIndex, final Locatable location) throws SmAbortException;

	public void startElement(final QName elementName, final int elementIndex, final SmType<A> elementType, final Locatable locatable) throws SmAbortException
	{
		for (final ArrayList<IdentityField<A>> contextFields : m_fieldEvals.values())
		{
			for (final IdentityField<A> field : contextFields)
			{
				field.startElement(elementName, elementIndex, elementType, locatable);
			}
		}

		m_selectorEval.startElement(elementName, elementIndex);
	}

	public void attribute(final QName attributeName, final List<? extends A> actualValue, final int attributeIndex, final SmSimpleType<A> attributeType, final Locatable locatable, final AtomBridge<A> atomBridge) throws SmAbortException
	{
		for (final ArrayList<IdentityField<A>> contextFields : m_fieldEvals.values())
		{
			for (final IdentityField<A> field : contextFields)
			{
				field.attribute(attributeName, actualValue, attributeIndex, attributeType, locatable, atomBridge);
			}
		}
	}

	public void text(final List<? extends A> actualValue, final SmSimpleType<A> actualType, final int textIndex, final Locatable locatable, final AtomBridge<A> atomBridge) throws SmAbortException
	{
		for (final ArrayList<IdentityField<A>> contextFields : m_fieldEvals.values())
		{
			for (final IdentityField<A> field : contextFields)
			{
				field.text(actualValue, actualType, textIndex, locatable, atomBridge);
			}
		}
	}

	public void endElement(final QName elementName, final int elementIndex, final Locatable locatable) throws SmAbortException
	{
		m_selectorEval.endElement(elementName, elementIndex, locatable);

		for (final ArrayList<IdentityField<A>> contextFields : m_fieldEvals.values())
		{
			for (final IdentityField<A> field : contextFields)
			{
				field.endElement(elementName, elementIndex, locatable);
			}
		}
	}

	public SmIdentityConstraint<A> getConstraint()
	{
		return m_constraint;
	}

	/**
	 * We've found and element that matches the {selector}, so install handlers for the fields. This only needs to be
	 * done once for a given selected element, therby accounting for duplicates caused by the union(s) in the XPath
	 * {selector} expression.
	 * 
	 * @param elementName
	 *            The name of the selected element.
	 * @param elementIndex
	 *            The index of the selected element.
	 */
	public void startSelectorElement(final QName elementName, final int elementIndex)
	{
		// Avoid duplication caused by XPath unions.
		if (!m_boundFields.containsKey(elementIndex))
		{
			// No fields have been bound yet for this element.
			m_boundFields.put(elementIndex, 0);

			final ArrayList<IdentityField<A>> fieldEvals = new ArrayList<IdentityField<A>>();
			for (final SmRestrictedXPath path : getConstraint().getFields())
			{
				fieldEvals.add(new IdentityField<A>(path, elementIndex, this, m_errorHandler));
			}
			m_fieldEvals.put(elementIndex, fieldEvals);
		}
		else
		{
			// A redundant union in the XPath expression caused duplication.
			// Ignore.
		}
	}

	public void endSelectorElement(final QName elementName, final int elementIndex, final Locatable location) throws SmAbortException
	{
		if (m_boundFields.containsKey(elementIndex))
		{
			try
			{
				onScopeEnd(elementIndex, location);
			}
			finally
			{
				m_fieldEvals.remove(elementIndex);
				m_boundFields.remove(elementIndex);
			}
		}
		else
		{
			// We must have duplicates and removed it already.
		}
	}

	/**
	 * When all fields have been gathered, report back up to the scope. This method is called by an IdentityField when
	 * its's value has been set.
	 * 
	 * @param changedField
	 *            The {@link IdentityField} that had its value set.
	 */
	void onFieldValueSet(final IdentityField<A> changedField, final int elementIndex, final AtomBridge<A> atomBridge) throws SmAbortException
	{
		// Note: We don't currently use the "changedField" parameter.
		final ArrayList<IdentityField<A>> elementHandlers = m_fieldEvals.get(elementIndex);

		// Increment the number of fields that have been bound.
		final int boundFields = m_boundFields.get(elementIndex) + 1;
		m_boundFields.put(elementIndex, boundFields);

		// When all the fields have been set, gather the values together and
		// notify the scope.
		if (boundFields == elementHandlers.size())
		{
			final ArrayList<IdentityKey<A>> fieldValues = new ArrayList<IdentityKey<A>>(boundFields);
			for (final IdentityField<A> field : elementHandlers)
			{
				fieldValues.add(new IdentityKey<A>(field.getTypedValue()));
			}
			onKeysComplete(fieldValues, elementIndex);
		}
	}
}
