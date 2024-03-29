/*
 * This file is part of the Flint library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.flint.api;

import java.io.Reader;

import org.weborganic.flint.IndexException;


/**
 * Translate a <code>Content</code> into an XML Stream, ready to be transformed to produce Index XML.
 *
 * @author Jean-Baptiste Reure
 *
 * @version 9 March 2010
 */
public interface ContentTranslator {

  /**
   * Translate the content provided into an XML Source ready to be transformed by Flint.
   *
   * @param content the content to translate
   *
   * @return the translation as a Reader
   *
   * @throws IndexException Should any error occur during the translation.
   */
  Reader translate(Content content) throws IndexException;

}
