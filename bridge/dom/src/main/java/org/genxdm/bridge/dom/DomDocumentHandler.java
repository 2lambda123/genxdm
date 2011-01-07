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
package org.genxdm.bridge.dom;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.genxdm.exceptions.GxmlMarshalException;
import org.genxdm.exceptions.PreCondition;
import org.genxdm.processor.io.DefaultDocumentHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Overrides the DefaultDocumentHandler so that DOM clients can choose
 * to use the full support of schema validation built into existing DOM
 * builders.
 */
public class DomDocumentHandler extends DefaultDocumentHandler<Node> {

	/**
	 * Constructor takes a {@link DocumentBuilderFactory} so that clients
	 * can choose what sort of schema support they want to load into the
	 * document builder.
	 * 
	 * @param dbf	The document builder factory to use.
	 */
	public DomDocumentHandler(DocumentBuilderFactory dbf) {
		super( new DomFragmentBuilder(dbf), new DomModel());
		
		m_dbf = dbf;
	}

	
	@Override
	public Node parse(InputStream byteStream, URI systemId) throws IOException,
			GxmlMarshalException {
		return parse(new InputSource(byteStream), systemId);
	}


	@Override
	public Node parse(Reader characterStream, URI systemId) throws IOException,
			GxmlMarshalException {
		return parse(new InputSource(characterStream), systemId);
	}


	@Override
	public Node parse(InputSource source, URI systemId) throws IOException,
			GxmlMarshalException {
		
        PreCondition.assertArgumentNotNull(source, "source");
        Document result = null;
        
        if (null != systemId)
        {
            source.setSystemId(systemId.toString());
        }
        try
        {
        	PreCondition.assertTrue(m_dbf.isNamespaceAware(), "Document Builder factory must be namespace aware.");
            DocumentBuilder db = getDocumentBuilder();
            result = db.parse(source);
        }
        catch (ParserConfigurationException pce)
        {
            throw new GxmlMarshalException(pce);
        }
        catch (final SAXException e)
        {
            throw new GxmlMarshalException(e);
        }
        return result;
	}

	/**
	 * This method lets us reuse the document builder over and over again
	 * 
	 * @return A new or recycled DocumentBuilder.
	 * @throws ParserConfigurationException
	 */
	private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		if (m_db == null) {
			m_db = m_dbf.newDocumentBuilder();
		}
		else {
			m_db.reset();
		}
		
		// now, set the error handler.
        if (errors != null)
        {
        	m_db.setErrorHandler(errors);
        }
        
        return m_db;
	}
	
	private DocumentBuilder m_db;
	
	private DocumentBuilderFactory m_dbf;
}
