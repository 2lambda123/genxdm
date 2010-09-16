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

import javax.xml.namespace.QName;

import org.gxml.names.NameSource;
import org.gxml.processor.w3c.xs.validation.regex.api.RegExBridge;
import org.gxml.xs.components.SmParticleTerm;


final class ValidationRegExBridge<A> implements RegExBridge<ValidationExpr<A, SmParticleTerm<A>>, QName>
{
	@SuppressWarnings("unused")
	private final NameSource nameBridge;

	public ValidationRegExBridge(final NameSource nameBridge)
	{
		this.nameBridge = nameBridge;
	}

	public Iterable<ValidationExpr<A, SmParticleTerm<A>>> getSubTerms(final ValidationExpr<A, SmParticleTerm<A>> expression)
	{
		return expression.getSubTerms();
	}

	public boolean intersects(final ValidationExpr<A, SmParticleTerm<A>> e1, final ValidationExpr<A, SmParticleTerm<A>> e2)
	{
		return e1.intersects(e2);
	}

	public boolean isChoice(final ValidationExpr<A, SmParticleTerm<A>> expression)
	{
		return expression.isChoice();
	}

	public boolean isInterleave(final ValidationExpr<A, SmParticleTerm<A>> expression)
	{
		return expression.isInterleave();
	}

	public boolean isSequence(final ValidationExpr<A, SmParticleTerm<A>> expression)
	{
		return expression.isSequence();
	}

	public boolean matches(final ValidationExpr<A, SmParticleTerm<A>> expression, final QName token)
	{
		return expression.matches(token);
	}

	public int maxOccurs(final ValidationExpr<A, SmParticleTerm<A>> expression)
	{
		if (expression.isMaxOccursUnbounded())
		{
			return RegExBridge.UNBOUNDED;
		}
		else
		{
			return expression.maxOccurs();
		}
	}

	public int minOccurs(final ValidationExpr<A, SmParticleTerm<A>> expression)
	{
		return expression.minOccurs();
	}

	public ValidationExpr<A, SmParticleTerm<A>> prime(final ValidationExpr<A, SmParticleTerm<A>> expression)
	{
		return expression;
	}
}