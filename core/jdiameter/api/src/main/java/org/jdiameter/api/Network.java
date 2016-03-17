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
 * This interface introduces a capability to work with a network.
 * You can get instance of this interface over stack instance:
 * <code>
 * if (stack.isWrapperFor(Network.class)) {
 *       Network netWork = stack.unwrap(Network.class);
 * .....
 * }
 * </code>
 * 
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @version 1.5.1 Final
 */
public interface Network extends Wrapper {

  /**
   * Return local peer network statistics
   * @return network statistics
   */
  Statistic getStatistic();

  /**
   * Register listener for processing network requests
   * @param applicationId application Id
   * @param listener request listener
   * @throws ApplicationAlreadyUseException  if listener with predefined appId already append to network
   */
  void addNetworkReqListener(NetworkReqListener listener, ApplicationId... applicationId) throws ApplicationAlreadyUseException;

  /**
   * Register listener for processing network requests
   * @param selector application selector
   * @param listener request listener
   */
  void addNetworkReqListener(NetworkReqListener listener, Selector<Message, ApplicationId>... selector);

  /**
   * Remove request listener
   * @param applicationId application id of listener
   */
  void removeNetworkReqListener(ApplicationId... applicationId);

  /**
   * Remove request listener
   * @param selector selector of application
   */
  void removeNetworkReqListener(Selector<Message, ApplicationId>... selector);

}

