/**
 * Portions copyright (c) 1998-1999, James Clark : see copyingjc.txt for
 * license details
 * Portions copyright (c) 2002, Bill Lindsey : see copying.txt for license
 * details
 * 
 * Portions copyright (c) 2009-2010 TIBCO Software Inc.
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
package org.gxml.processor.xpath.v10.iterators;

import org.genxdm.base.Model;
import org.gxml.xpath.v10.expressions.ExprException;
import org.gxml.xpath.v10.iterators.NodeIterator;

public final class DifferenceNodeIterator<N> implements NodeIterator<N>
{
	private final NodeIterator<N> iter1;
	private final NodeIterator<N> iter2;
	private final Model<N> model;
	private N node1;
	private N node2;

	public DifferenceNodeIterator(final NodeIterator<N> iter1, final NodeIterator<N> iter2, final Model<N> model) throws ExprException
	{
		this.iter1 = iter1;
		this.iter2 = iter2;
		this.model = model;
		this.node1 = iter1.next();
		this.node2 = iter2.next();
	}

	public N next() throws ExprException
	{
		while (node1 != null)
		{
			if (node2 == null)
			{
				N tem = node1;
				node1 = iter1.next();
				return tem;
			}
			final int cmp = model.compare(node1, node2);
			if (cmp < 0)
			{
				N tem = node1;
				node1 = iter1.next();
				return tem;
			}
			if (cmp == 0)
			{
				node1 = iter1.next();
				node2 = iter2.next();
			}
			else
			{
				node2 = iter2.next();
			}
		}
		return null;
	}
}
