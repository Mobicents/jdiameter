/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jdiameter.api;

/**
 * This class implements counter of statistic
 * 
 * @author erick.svenson@yahoo.com
 * @version 1.5.1 Final
 */
public interface StatisticRecord {

  /**
   * Return name of counter
   * @return  name of counter
   */
  String getName();

  /**
   * Return description of counter
   * @return description of counter
   */
  String getDescription();

  /**
   * Return value of counter as integer
   * @return value of counter
   */
  int getValueAsInt();

  /**
   *  Return value of counter as double
   * @return value of counter
   */
  double getValueAsDouble();

  /**
   *  Return value of counter as long
   * @return value of counter
   */
  long getValueAsLong();

  /**
   * Return child counters
   * @return array of child counters
   */
  StatisticRecord[] getChilds();

  /**
   * Reset counter and all child counters
   */
  void reset();

  /**
   * Enable/Disable counter
   *
   * @param e on/off parameter
   */
  public void enable(boolean e);

  public boolean isEnabled();
}
