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
import org.genxdm.xs.exceptions.SmComponentConstraintException;

@SuppressWarnings("serial")
public abstract class SccAttributeGroupPropertiesException extends SmComponentConstraintException
{
    public static final String PART_PROPERTIES = "1";
    public static final String PART_MEMBER_NAMES = "2";
    public static final String PART_DERIVED_FROM_ID = "3";

    public SccAttributeGroupPropertiesException(final String partNumber)
    {
        super(SmOutcome.SCC_Attribute_Group_Definition_Properties_Correct, partNumber);
    }
}
