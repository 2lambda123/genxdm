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
package org.gxml.processor.xpath.v10.functions;

import org.genxdm.base.Model;
import org.gxml.xpath.v10.expressions.ConvertibleExpr;
import org.gxml.xpath.v10.expressions.ConvertibleNodeSetExpr;
import org.gxml.xpath.v10.expressions.ExprContextDynamic;
import org.gxml.xpath.v10.expressions.ExprContextStatic;
import org.gxml.xpath.v10.expressions.ExprException;
import org.gxml.xpath.v10.expressions.ExprParseException;
import org.gxml.xpath.v10.expressions.NodeSetExpr;
import org.gxml.xpath.v10.expressions.StringExpr;
import org.gxml.xpath.v10.expressions.VariantExpr;
import org.gxml.xpath.v10.iterators.NodeIterator;
import org.gxml.xpath.v10.variants.Variant;
import org.gxml.processor.xpath.v10.iterators.SingleNodeIterator;

/**
 * the XPath function id(x)
 */
public final class IdFunction
    extends Function1
{

	private final <N> NodeIterator<N> id(final N node, final NodeIterator<N> iter) throws ExprException
	{
	    // TODO:
	    // for each node in the set, call: id(node, StringFunction(iter.next()).stringFunction())
	    // (or something like that: turn the node into a string representing an id)
	    // collect the results of each call, return the iterator over the entire collection.
		throw new UnsupportedOperationException("TODO: id()");
	}

	private final <N> NodeIterator<N> id(Model<N> model, final N node, final String str) throws ExprException
	{
		// TODO - review:
		// The following seems a little to simplistic - it just always returns the node with the given ID? 
		N result = model.getElementById(node, str);
		return new SingleNodeIterator<N>(result);
	}

	ConvertibleExpr makeCallExpr(final ConvertibleExpr e, final ExprContextStatic statEnv) throws ExprParseException
	{
		if (e instanceof NodeSetExpr)
		{
			final NodeSetExpr nse = (NodeSetExpr)e;
			return new ConvertibleNodeSetExpr()
			{
				public <N> NodeIterator<N> nodeIterator(Model<N> model, final N node, final ExprContextDynamic<N> dynEnv) throws ExprException
				{
					return id(node, nse.nodeIterator(model, node, dynEnv));
				}
			};
		}
		else if (e instanceof VariantExpr)
		{
			final VariantExpr ve = (VariantExpr)e;
			return new ConvertibleNodeSetExpr()
			{
				public <N> NodeIterator<N> nodeIterator(Model<N> model, final N node, final ExprContextDynamic<N> dynEnv) throws ExprException
				{
					Variant<N> v = ve.evaluateAsVariant(model, node, dynEnv);
					if (v.isNodeSet())
					{
						return id(node, v.convertToNodeSet());
					}
					else
					{
						return id(model, node, v.convertToString());
					}
				}
			};
		}
		else
		{
			final StringExpr se = e.makeStringExpr(statEnv);
			return new ConvertibleNodeSetExpr()
			{
				public <N> NodeIterator<N> nodeIterator(Model<N> model, final N contextNode, final ExprContextDynamic<N> dynEnv) throws ExprException
				{
					return id(model, contextNode, se.stringFunction(model, contextNode, dynEnv));
				}
			};
		}
	}

}
