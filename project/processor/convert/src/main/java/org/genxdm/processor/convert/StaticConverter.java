/**
 * Copyright (c) 2010 TIBCO Software Inc.
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
package org.genxdm.processor.convert;

import org.genxdm.base.Cursor;
import org.genxdm.base.io.FragmentBuilder;
import org.genxdm.exceptions.GxmlException;

public class StaticConverter
{
    public static <Nsrc, Ntrg> Ntrg convert(Cursor<Nsrc> cursor, FragmentBuilder<Ntrg> builder)
        throws GxmlException
    {
        builder.reset();
        cursor.write(builder);
        return builder.getNode();
    }

}