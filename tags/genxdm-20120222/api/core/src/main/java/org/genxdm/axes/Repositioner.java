/*
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
package org.genxdm.axes;

/** An interface that deliberately isolates the only &lt;N>-specific method
 * in the cursor interface.  Cursor extends this interface, rather than
 * {@link Navigator}.
 */
public interface Repositioner<N>
    extends Navigator
{
    /**
     * Positions the cursor at the specified bookmark.
     * 
     * <p>Note that behavior is <em>undefined</em> if the target node is not in
     * the same tree that the stateful-abstraction-extending-repositioner is
     * currently positioned in.  It may work.  It may not.  It may throw
     * an exception.  Don't do that.</p>
     * 
     * @param bookmark
     *            The node to which the cursor should be positioned.
     */
    void moveTo(N bookmark);

}
