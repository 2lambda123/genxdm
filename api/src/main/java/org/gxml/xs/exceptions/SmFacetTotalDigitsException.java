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
package org.gxml.xs.exceptions;

import org.gxml.exceptions.PreCondition;
import org.gxml.xs.facets.SmTotalDigits;


/**
 * xs:totalDigits
 */
@SuppressWarnings("serial")
public final class SmFacetTotalDigitsException extends SmFacetException
{
	private final int actualValue;
	private final String displayString;
	private final SmTotalDigits<?> expectedValue;

	public SmFacetTotalDigitsException(final int actualValue, final String value, final SmTotalDigits<?> totalDigits)
	{
		this.actualValue = actualValue;
		this.displayString = PreCondition.assertArgumentNotNull(value, "value");
		this.expectedValue = totalDigits;
	}

	@Override
	public String getMessage()
	{
		return "The total number of digits(" + actualValue + ") in '" + displayString + "' exceeds the number allowed for the type(" + expectedValue.getTotalDigits() + ").";
	}
}
