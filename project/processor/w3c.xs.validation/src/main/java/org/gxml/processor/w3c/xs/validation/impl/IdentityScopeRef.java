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

import org.genxdm.xs.constraints.SmIdentityConstraint;
import org.genxdm.xs.exceptions.SmAbortException;
import org.genxdm.xs.exceptions.SmExceptionHandler;
import org.genxdm.xs.resolve.SmLocation;
import org.gxml.processor.w3c.xs.exception.CvcDanglingKeyReferenceException;


/**
 * Specialization of a scope for xs:keyref.
 */
final class IdentityScopeRef<A> extends IdentityScope<A>
{
	private final IdentityScopeKey<A> keyScope;

	public IdentityScopeRef(final int elementIndex, final IdentityScopeKey<A> keyScope, final SmIdentityConstraint<A> constraint, final SmExceptionHandler errorHandler, final SmLocation location)
	{
		super(elementIndex, constraint, errorHandler, location);
		this.keyScope = keyScope;
	}

	@Override
	protected void onKeysComplete(final ArrayList<IdentityKey<A>> keyValues, final int elementIndex) throws SmAbortException
	{
		final IdentityTuple<A> key = new IdentityTuple<A>(keyValues);

		final IdentityVariant<A> lookup = keyScope.m_qualifiedTargets.get(key);

		if (lookup == null)
		{
			final ArrayList<IdentityDanglingReference<A>> dangles = new ArrayList<IdentityDanglingReference<A>>();
			dangles.add(new IdentityDanglingReference<A>(getConstraint(), keyValues, m_location));
			keyScope.m_qualifiedTargets.put(key, new IdentityVariant<A>(dangles));
		}
		else if (lookup.isValue())
		{
			// The corresponding key has already been found.
		}
		else
		{
			lookup.getDanglingRefs().add(new IdentityDanglingReference<A>(getConstraint(), keyValues, m_location));
		}
	}

	@Override
	protected void onScopeEnd(final int elementIndex, final Locatable locatable)
	{
		// Do nothing.
	}

	/**
	 * This method should only be called on scopes that correspond to xs:keyref.
	 */
	public void reportUnmatchedRefs() throws SmAbortException
	{
		for (final IdentityVariant<A> next : keyScope.m_qualifiedTargets.values())
		{
			if (next.isDanglingRefs())
			{
				for (final IdentityDanglingReference<A> dangling : next.getDanglingRefs())
				{
					final CvcDanglingKeyReferenceException dkre = new CvcDanglingKeyReferenceException(dangling.getConstraint().getName(), dangling.getKeys(), m_location);
					m_errorHandler.error(dkre);
				}
			}
			else
			{
				// The polymorphic type of the identity variant is a
				// Boolean.TRUE
				// which indicates that there are no dangling references.
			}
		}
	}
}
