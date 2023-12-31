GenXDM (formerly gXML) provides an [XDM](http://www.w3.org/TR/xpath-datamodel/) XQuery/XPath Data
Model applications programming interface for Java. It makes extensive use of Java generics to enable the
API to run over any arbitrary tree model for which a "bridge" has been created—thus the name: GenXDM. Bridges
are provided for W3C DOM, for Apache Axiom, and for a simple "reference model" intended to aid others to develop
bridges as well.

The API divides naturally into "bridges" and "processors." A bridge exposes an instance of the Data Model (typed
or untyped, mutable or immutable). A processor operates over the data model—several examples are provided with
interesting functionality, core utility, or as samples for the inspiration of further development.

## What's it good for?

GenXDM is designed to allow the current (very broad and deep) infrastructure of XML tools to continue working,
using their preferred form of model for XML, while providing a path forward in which new tools can be
model-agnostic. That is, it's well-known by now that one cannot introduce a new tree model for XML and expect to
see it supplant the DOM. We don't try to do so. We provide an API against which tools can be built—and that API
is more pleasant and more predictable to work with than the DOM. We also provide a "bridge" to the DOM (which is
not a "wrapper," but uses the Handle/Body pattern to introduce absolute minimal overhead). That, in turn, means
that other XML tree model APIs can be used as well, so long as there's a bridge for them.

Projects often enough do this themselves—typically using a wrapper pattern rather than a bridge pattern, and
typically as a side effort, rather than as the primary goal. The result is extra overhead (wrapper, or Facade,
is not an effective pattern when dealing with very large XML documents) and ultimately a loss of momentum. In
most cases, different 'supported' tree models have different support profiles, different levels of completeness
(and eventually, the whole attempt to be more generic is abandoned: "Everybody uses DOM").

## Maven Repository

If you'd like to use it in a maven build environment, add this repository:
[http://www.genxdm.org/maven2/])http://www.genxdm.org/maven2/)

Note: that repository is no longer available.

