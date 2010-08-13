package org.weborganic.flint.search;

import java.io.IOException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.weborganic.flint.util.Beta;

import com.topologi.diffx.xml.XMLWritable;

/**
 * Defines a facet for a search.
 * 
 * @author Christophe Lauret
 * @version 2 August 2010
 */
@Beta
public interface Facet extends XMLWritable {

  /**
   * Returns the name of this facet.
   * 
   * @return the name of this facet.
   */
  String name();

  /**
   * Returns the query that would correspond to this facet for the specified value.
   * 
   * @param value the text of the term to match.
   * @return the requested query if it exists or <code>null</code>.
   */
  Query forValue(String value);

  /**
   * Compute the values for this facet.
   * 
   * @param searcher The searcher to use to compute the facet values.
   * @param base     The base query to build the facet from.
   * 
   * @throws IOException should it be reported by the searcher.
   */
  void compute(IndexSearcher searcher, Query base) throws IOException;

}