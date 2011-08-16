/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.services.bench;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.services.log.Log;

/**
 * Created by The eXo Platform SAS
 * 
 * @Author : <a href="mailto:quanglt@exoplatform.com">Le Thanh Quang</a> Jul 20,
 *         2011
 */
public abstract class DataInjector extends BaseComponentPlugin {

  private List<String> users   = Arrays.asList(new String[] { "root", "demo", "mary", "john" });

  private Random       rand    = new Random();

  private LoremIpsum4J textGen = new LoremIpsum4J();
  
  private String restId;
  
  public String getRestId() {
    return restId;
  }

  public void setRestId(String restId) {
    this.restId = restId;
  }
  
  /**
   * get log object.
   * @return
   */
  public abstract Log getLog();

  /**
   * check whether data is initialized or not.
   * 
   * @return true if data is initialized.
   */
  public abstract boolean isInitialized();
  
  /**
   * Transform parameters set by user to variables. 
   * <br>
   * The parameters can be value-params declared in plugin declaration part of portal configuration file or query parameters of a RESTful request.
   * <br>
   * Using {@link InitParams#getValueParam(String)} to get value of parameters.
   * @param initParams - includes value-params only.
   */
  public abstract void initParams(InitParams initParams);
  
  /**
   * This function should be implemented to inject data into the product.
   * @throws Exception
   */
  public abstract void inject() throws Exception;
  
  /**
   * This function should be implemented to clear data that is injected before by {@link #inject()}.
   * @throws Exception
   */
  public abstract void reject() throws Exception;

  /**
   * get random user id.
   */
  public final String randomUser() {
    return users.get(rand.nextInt(4));
  }

  /**
   * get random words.
   * 
   * @param i maximum number of words. the number of words is between 0 and i.
   * @return
   */
  public final String randomWords(int i) {
    int wordCount = rand.nextInt(i + 1) + 1;
    String words = textGen.getWords(wordCount);
    return words;
  }

  /**
   * get random paragraphs
   * 
   * @param i maximum number of paragraphs.
   * @return
   */
  public final String randomParagraphs(int i) {
    int paragraphCount = rand.nextInt(i + 1) + 1;
    String paragraphs = textGen.getParagraphs(paragraphCount);
    return paragraphs.replaceAll("\\n\\n", "<br/><br/>");
  }
  
  /**
   * create text/plain resource by size.
   * @param size in kilobyte
   * @return
   */
  public String createTextResource(int size) {
    int sizeInByte = size * 1024; // byte
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < sizeInByte; i++) {
      sb.append("A"); // each A character spends one byte in UTF-8.
    }
    return sb.toString();
  }
  
}