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

package org.jdiameter.common.api.app.auth;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.auth.ClientAuthSessionListener;
import org.jdiameter.api.auth.ServerAuthSessionListener;
import org.jdiameter.common.api.app.IAppSessionFactory;

/**
 * Interface for Diameter Authentication Session Factories
 * 
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public interface IAuthSessionFactory extends IAppSessionFactory {

  public IAuthMessageFactory getMessageFactory();

  public void setMessageFactory(IAuthMessageFactory v);

  public ServerAuthSessionListener getServerSessionListener();

  public void setServerSessionListener(ServerAuthSessionListener v);

  public StateChangeListener<AppSession> getStateListener();

  public void setStateListener(StateChangeListener<AppSession> v);

  public ClientAuthSessionListener getClientSessionListener();

  public void setClientSessionListener(ClientAuthSessionListener v);

  public IServerAuthActionContext getServerSessionContext();

  public void setServerSessionContext(IServerAuthActionContext v);

  public IClientAuthActionContext getClientSessionContext();

  public void setClientSessionContext(IClientAuthActionContext v);

  public boolean isStateles();

  public void setStateles(boolean stateless);

  /**
   * @return the messageTimeout
   */
  public long getMessageTimeout();

  /**
   * @param messageTimeout
   *            the messageTimeout to set
   */
  public void setMessageTimeout(long messageTimeout);

}
