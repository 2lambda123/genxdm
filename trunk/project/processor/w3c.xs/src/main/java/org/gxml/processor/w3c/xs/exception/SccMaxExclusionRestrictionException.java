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

import org.genxdm.xs.enums.SmOutcome;
import org.genxdm.xs.facets.SmFacetKind;
import org.genxdm.xs.facets.SmLimit;

@SuppressWarnings("serial")
public class SccMaxExclusionRestrictionException extends SccLimitRestrictionException
{
	public SccMaxExclusionRestrictionException(final SmFacetKind parentFacetKind, final SmLimit<?> restrictingLimit, final SmLimit<?> parentLimit)
	{
		super(SmOutcome.SCC_MaxExclusiveValidRestriction, parentFacetKind == SmFacetKind.MaxExclusive ? "1" : parentFacetKind == SmFacetKind.MaxInclusive ? "2" : parentFacetKind == SmFacetKind.MinInclusive ? "3" : "4", SmFacetKind.MaxExclusive, parentFacetKind, restrictingLimit, parentLimit);
	}
}
