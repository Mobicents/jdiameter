/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
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

package org.jdiameter.client.api;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.SessionFactory;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * This interface describe extends methods of base class
 * 
 * @version 1.5.0.1
 * 
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public interface ISessionFactory extends SessionFactory {

  /**
   * Method used for creating a new App Session using the specified class with the 
   * desired Application Id and Session Id.
   * 
   * @param sessionId the session-id for this App Session, if desired
   * @param applicationId the application id for this session
   * @param aClass the class of the app session object
   * @param args
   * @return an AppSession instance 
   * @throws InternalException
   */
  <T extends AppSession> T getNewAppSession(String sessionId, ApplicationId applicationId, Class<? extends AppSession> aClass,
      Object... args) throws InternalException;

  /**
   * Registers a new App Session factory.
   * 
   * @param sessionClass the class of the objects being generated by the factory
   * @param factory the factory to generate app sessions
   */
  void registerAppFacory(Class<? extends AppSession> sessionClass, IAppSessionFactory factory);

  /**
   * Unregisters an existing App Session factory.
   * 
   * @param sessionClass the class identifier for this factory
   */
  void unRegisterAppFacory(Class<? extends AppSession> sessionClass);

  /**
   * Retrieves the app session factory associated with an app session class
   * 
   * @param sessionClass the class identifier for the desired factory
   * @return the App Session Factory instance if registered, 
   *         null if no factory is registered for such class
   */
  IAppSessionFactory getAppSessionFactory(Class<? extends AppSession> sessionClass);

  /**
   * 
   * @return
   */
  IContainer getContainer();

}
