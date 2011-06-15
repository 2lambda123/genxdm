/*
 * Copyright (c) 2010-2011 TIBCO Software Inc.
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
package org.genxdm.processor.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.genxdm.Model;
import org.genxdm.ProcessingContext;
import org.genxdm.exceptions.XdmMarshalException;
import org.genxdm.exceptions.PreCondition;
import org.genxdm.io.DocumentHandler;
import org.genxdm.io.FragmentBuilder;
import org.genxdm.io.Resolved;
import org.genxdm.io.Resolver;
import org.genxdm.processor.input.XmlEventVisitor;
import org.genxdm.processor.output.ContentHandlerOnXmlStreamWriter;
import org.xml.sax.InputSource;

/** A generic DocumentHandler suitable for specializing for return by any
 * ProcessingContext.
 * 
 * This implementation requires that either a ProcessingContext be supplied
 * (the implementation will retrieve a fragment builder for input and a model
 * for output), or the pair FragmentBuilder, Model, in the constructor.
 * 
 * This implementation makes use of the generic adapters found in org.genxdm.processor.input
 * and org.genxdm.processor.output.
 * 
 * @param <N> The node handle.
 */
public class DefaultDocumentHandler<N>
    implements DocumentHandler<N>
{
    
    public DefaultDocumentHandler(ProcessingContext<N> context)
    {
        this(PreCondition.assertNotNull(context, "context").newFragmentBuilder(), context.getModel());
    }
    
    public DefaultDocumentHandler(final FragmentBuilder<N> builder, final Model<N> model)
    {
        this.builder = PreCondition.assertNotNull(builder, "builder");
        this.model = PreCondition.assertNotNull(model, "model");
        ipf.setProperty("javax.xml.stream.isCoalescing", true);
        ipf.setProperty("javax.xml.stream.isReplacingEntityReferences", true);
    }
    
    public boolean isValidating()
    {
        // TODO: if validation is enabled, return indicator.
        return false;
    }
    
    public void setResolver(Resolver resolver)
    {
        this.resolver = resolver;
    }
    
    public void setReporter(XMLReporter reporter)
    {
        ipf.setProperty("javax.xml.stream.reporter", reporter);
    }
    
    public void setValidating(boolean flag)
    {
        // TODO: implement?
        throw new UnsupportedOperationException();
    }

    public N parse(InputStream byteStream, URI systemId)
        throws IOException, XdmMarshalException
    {
        PreCondition.assertNotNull(byteStream, "byteStream");
        try
        {
            XMLEventReader eventReader;
            if (systemId == null)
            {
                eventReader = ipf.createXMLEventReader(byteStream);
            }
            else
            {
                eventReader = ipf.createXMLEventReader(systemId.toString(), byteStream);
            }
            return parseEventReader(eventReader, systemId);
        }
        catch (XMLStreamException xse)
        {
            throw new XdmMarshalException(xse);
        }
    }

    public N parse(Reader characterStream, URI systemId)
        throws IOException, XdmMarshalException
    {
        PreCondition.assertNotNull(characterStream, "characterStream");
        try
        {
            XMLEventReader eventReader;
            if (systemId == null)
            {
                eventReader = ipf.createXMLEventReader(characterStream);
            }
            else
            {
                eventReader = ipf.createXMLEventReader(systemId.toString(), characterStream);
            }
            return parseEventReader(eventReader, systemId);
        }
        catch (XMLStreamException xse)
        {
            throw new XdmMarshalException(xse);
        }
    }
    
    public N parse(InputSource source, URI systemId)
        throws IOException, XdmMarshalException
    {
        if (source.getCharacterStream() != null)
            return parse(source.getCharacterStream(), systemId);
        if (source.getByteStream() != null)
            return parse(source.getByteStream(), systemId);
        if (resolver != null)
        {
            // TODO: this might break, actually.
            // also, this indicates that we're being lame with the resolver.
            Resolved<Reader> rdr = resolver.resolveReader(URI.create(source.getSystemId())); 
            return parse(rdr.getResource(), systemId);
        }
        return null;
    }

    public void write(final OutputStream byteStream, final N source, String encoding)
        throws IOException, XdmMarshalException
    {
        PreCondition.assertNotNull(byteStream, "byteStream");
        PreCondition.assertNotNull(source, "source");
        Charset cs = null;
        if (encoding != null)
        {
            if (Charset.isSupported(encoding))
            {
                cs = Charset.forName(encoding);
            }
        }
        if (cs == null)
        {
            cs = Charset.forName("UTF-8");
        }
        OutputStreamWriter writer = new OutputStreamWriter(byteStream, cs);
        write(writer, source);
        writer.flush();
    }

    public void write(Writer characterStream, N source)
        throws IOException, XdmMarshalException
    {
        PreCondition.assertNotNull(characterStream, "characterStream");
        PreCondition.assertNotNull(source, "source");
        try
        {
            XMLStreamWriter stream = opf.createXMLStreamWriter(characterStream);
            ContentHandlerOnXmlStreamWriter adapter = new ContentHandlerOnXmlStreamWriter(stream);
            //ContentWriter adapter = new ContentWriter(characterStream);
            model.stream(source, true, adapter);
            stream.flush();
        }
        catch (XMLStreamException xse)
        {
            throw new XdmMarshalException(xse);
        }
    }
    
    protected N parseEventReader(XMLEventReader reader, URI systemId)
        throws IOException, XdmMarshalException
    {
        // this is probably working now.
        PreCondition.assertNotNull(reader, "reader");
        builder.reset();
        XmlEventVisitor visitor = new XmlEventVisitor(reader, builder);
        if (systemId != null)
            visitor.setSystemId(systemId);
        visitor.parse();
        return builder.getNode();
    }

    protected final XMLOutputFactory opf = XMLOutputFactory.newInstance();
    protected final XMLInputFactory ipf = XMLInputFactory.newInstance();
    private final FragmentBuilder<N> builder;
    private final Model<N> model;
    private Resolver resolver;

}
