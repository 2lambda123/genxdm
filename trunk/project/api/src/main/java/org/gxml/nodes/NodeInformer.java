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
package org.gxml.nodes;

import java.net.URI;

import javax.xml.namespace.QName;

import org.gxml.NodeKind;
import org.gxml.names.NamespaceBinding;

public interface NodeInformer<N>
{
    /**
     * Returns the set of attribute names for the node.
     * 
     * <p>
     * This method does not inherit attribute names in the reserved XML namespace.
     * </p>
     * 
     * @param node
     *            The node for which the attribute names are required.
     * @param orderCanonical
     *            Determines whether the names will be returned in canonical order (lexicographically by namespace
     *            URI,local name).
     */
    Iterable<QName> getAttributeNames(N node, boolean orderCanonical);
    
    /**
     * Returns the dm:string-value of the attribute node with the specified expanded-QName.
     * <p>
     * This is equivalent to retrieving the attribute node and then its string value.
     * </p>
     * 
     * @param parent
     *            The node that is the parent of the attribute node.
     * @param namespaceURI
     *            The namespace-uri part of the attribute name.
     * @param localName
     *            The local-name part of the attribute name.
     */
    String getAttributeStringValue(N parent, String namespaceURI, String localName);

    /**
     * Returns the base URI of the supplied context node, per the XML:Base
     * specification.
     * <br />Corresponds to the dm:base-uri accessor in the XDM.  Defined
     * for all node types except namespace.
     * 
     * @return the absolute value of the base-uri property, if it is available,
     * or null if it is not.
     */
    URI getBaseURI(N node);

    /**
     * Returns the absolute URI of the resource from which the Document Node was
     * constructed. <br/>
     * Corresponds to the dm:document-uri accessor in the XDM.
     * 
     * @return the absolute URI of the resource from which the Document Node was
     *         constructed, if the absolute URI is available; f there is no URI
     *         available, or if it cannot be made absolute when the Document
     *         Node is constructed, or if it is used on a node other than a
     *         Document Node, returns null
     */
    URI getDocumentURI(N node);

	/**
	 * Returns the local-name property of the dm:node-name.
	 * 
	 * <br/>
	 * 
	 * TEXT, COMMENT, and DOCUMENT nodes return <code>null</code>; they have no name.
	 * 
	 * Other node types should never return <code>null</code>.
     * @param node
     *            The node for which the node local-name is required.
     */
    String getLocalName(N node);

    /**
     * Returns the namespace bindings associated with the node as a set or prefix/URI pairs.
     * 
     * Only includes prefix mappings which are explicit and local to the node.
     * 
     * @param node
     *            The node under consideration.
     */
    Iterable<NamespaceBinding> getNamespaceBindings(N node);

    /** Only reports on namespace declarations for the target node,
     * not namespaces in scope for that node.
     * 
     * @param node the target node on which the namespace is declared.
     * @param prefix the prefix (namespace name) for which the URI is desired.
     * 
     * @return the namespace URI declared for this prefix, or null if no such prefix
     * mapping is declared on this node.
     */
    String getNamespaceForPrefix(N node, String prefix);

    /**
     * Returns the set of namespace names (prefixes) for a given node.
     * 
     * <br/>
     * 
     * This refers to the prefix mappings which are explicit and local to the node.
     * 
     * @param orderCanonical
     *            Determines whether the names will be returned in canonical order (lexicographically by local name).
     */
    Iterable<String> getNamespaceNames(N node, boolean orderCanonical);

	/**
	 * Returns the namespace-uri part of the dm:node-name.
	 * 
	 * <br/>
	 * 
	 * DOCUMENT, COMMENT, and TEXT nodes return <code>null</code>; they have no name.
	 * 
	 * Other node types should never return <code>null</code>.
     * @param node
     *            The node for which the node namespace-uri is required.
     */
    String getNamespaceURI(N node);

    /**
     * Return an object which obeys the contract for equals() and hashCode();
     * it may also identify the node via object identity (==), but this is not
     * guaranteed.  Implementations often return the node itself, but some bridges
     * are not able to do so.  The object returned from this method is guaranteed
     * to obey the equals()/hashCode() contract even when the node the object identifies
     * does not.
     * 
     * @param node the node for which an ID object is required.
     */
    Object getNodeId(N node);

    /**
     * Returns the node-kind of the node as an enumeration in {@link NodeKind}.
     * 
     * Applies to all node kinds and never returns <code>null</code>. <br/>
     * Corresponds to the dm:node-kind accessor in the XDM.
     * 
     * @param node
     *            The node for which the node-kind is required.
     */
    NodeKind getNodeKind(N node);

	/**
	 * Returns the prefix part of the dm:node-name.
	 * 
	 * <br/>
	 * 
	 * DOCUMENT, COMMENT, and TEXT nodes return <code>null</code>; they have no name.
	 * 
	 * Other node types should never return <code>null</code>.
	 * This is just a hint because it usually contains the prefix of the original document. The prefix will not be
	 * updated to reflect in scope namespaces.
     * @param node
     *            The node for which the node prefix hint is required.
	 */
    String getPrefix(N node);

    /**
     * Returns the dm:string-value property of the node. Applies to all node kinds.
     * 
     * @param node
     *            The node for which the dm:string-value is required.
     */
    String getStringValue(N node);

    /**
     * Determines whether there are nodes on the attribute axis for this node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean hasAttributes(N node);

    /**
     * Determines whether there are nodes on the child axis for this node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean hasChildren(N node);

    /**
     * Determines whether there are prefix-to-namespace mappings for this node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean hasNamespaces(N node);

    /**
     * Determines whether the node has a following sibling.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean hasNextSibling(N node);

    /**
     * Determines whether there are nodes on the parent axis for this node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean hasParent(N node);

    /**
     * Determines whether the node has a preceding sibling.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean hasPreviousSibling(N node);
    
    /**
     * Determines whether the specified node is an attribute node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean isAttribute(N node);

    /**
     * Determines whether the specified node is an element node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean isElement(N node);

    /**
     * Determine whether the node is an ID node.
     * Corresponds to the dm:is-id accessor.  Valid for element and attribute nodes.
     * 
     * @param node the node under consideration
     * @return true if the node is an attribute named xml:id, if it has a PSVI
     * type derived from xs:ID, or if it is an attribute with a DTD-defined type of ID,
     * otherwise false.
     */
    boolean isId(N node);
    
    /**
     * Determine whether the node contains one or more IDREFs.
     * Corresponds to the dm:is-idrefs accessor.  Valid for element and attribute nodes.
     * 
     * @param node the node under consideration
     * @return true if the node is an element or attribute with at least one atomic value
     * derived from xs:IDREF or xs:IDREFS, or if it is an attribute with a DTD-defined
     * type of IDREF or IDREFS.
     */
    boolean isIdRefs(N node);

    /**
     * Determines whether the specified node is a namespace node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean isNamespace(N node);

    /**
     * Determines whether the specified node is a text node.
     * 
     * @param node
     *            The node under consideration.
     */
    boolean isText(N node);

    /**
     * Deterimines whether the specified node matches the arguments.
     * 
     * @param node
     *            The XML node.
     * @param nodeKind
     *            The node kind to match.
     * @param namespaceURI
     *            The namespace-uri to match.
     * @param localName
     *            The local-name to match.
     */
    boolean matches(N node, NodeKind nodeKind, String namespaceURI, String localName);

    /**
     * Determines whether the specified node matches in name.
     * 
     * @param node
     *            The node being tested.
     * @param namespaceURI
     *            The namespace-uri part of the name.
     * @param localName
     *            The local-name part of the name.
     * @return <code>true</code> if the node matches the arguments specified, otherwise <code>false</code>.
     */
    boolean matches(N node, String namespaceURI, String localName);
}