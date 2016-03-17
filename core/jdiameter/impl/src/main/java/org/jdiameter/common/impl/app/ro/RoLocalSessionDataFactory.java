/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat, Inc. and individual contributors
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

package org.jdiameter.common.impl.app.ro;

import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.ro.ClientRoSession;
import org.jdiameter.api.ro.ServerRoSession;
import org.jdiameter.client.impl.app.ro.ClientRoSessionDataLocalImpl;
import org.jdiameter.common.api.app.IAppSessionDataFactory;
import org.jdiameter.common.api.app.ro.IRoSessionData;
import org.jdiameter.server.impl.app.ro.ServerRoSessionDataLocalImpl;

/**
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class RoLocalSessionDataFactory implements IAppSessionDataFactory<IRoSessionData>{

  /* (non-Javadoc)
   * @see org.jdiameter.common.api.app.IAppSessionDataFactory#getAppSessionData(java.lang.Class, java.lang.String)
   */
  @Override
  public IRoSessionData getAppSessionData(Class<? extends AppSession> clazz, String sessionId) {
    if(clazz.equals(ClientRoSession.class)) {
      ClientRoSessionDataLocalImpl data = new ClientRoSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    else if(clazz.equals(ServerRoSession.class)) {
      ServerRoSessionDataLocalImpl data = new ServerRoSessionDataLocalImpl();
      data.setSessionId(sessionId);
      return data;
    }
    throw new IllegalArgumentException(clazz.toString());
  }

}
