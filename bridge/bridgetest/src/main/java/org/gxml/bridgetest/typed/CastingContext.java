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
package org.gxml.bridgetest.typed;

import java.math.BigDecimal;

import org.gxml.exceptions.PreCondition;
import org.gxml.exceptions.SpillagePolicy;
import org.gxml.typed.TypedContext;
import org.gxml.typed.types.Emulation;

/**
 * A casting context providing a constant current-dateTime: 1964-04-21T03:13:27Z
 */
final class CastingContext<N, A> 
    implements org.gxml.typed.types.CastingContext<A>
{
	private final TypedContext<N, A> pcx;

	public CastingContext(final TypedContext<N, A> pcx)
	{
		this.pcx = PreCondition.assertArgumentNotNull(pcx, "pcx");
	}

	public Emulation getEmulation()
	{
		return Emulation.MODERN;
	}

	public SpillagePolicy getSpillagePolicy()
	{
		return SpillagePolicy.DO_THE_RIGHT_THING;
	}

	public A getCurrentDateTime()
	{
		return pcx.getAtomBridge().createDateTime(2009, 10, 18, 3, 13, 20, 0, BigDecimal.ZERO, 0);
	}
}