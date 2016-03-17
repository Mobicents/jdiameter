/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
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

package org.jdiameter.client.impl.app.rf;

import java.io.Serializable;

import org.jdiameter.api.Request;
import org.jdiameter.common.api.app.AppSessionDataLocalImpl;
import org.jdiameter.common.api.app.rf.ClientRfSessionState;

/**
 * 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ClientRfSessionDataLocalImpl extends AppSessionDataLocalImpl implements IClientRfSessionData {

  protected boolean isEventBased = true;
  protected boolean requestTypeSet = false;
  protected ClientRfSessionState state = ClientRfSessionState.IDLE;
  protected Serializable tsTimerId;

  protected Request buffer;
  protected String destinationHost;
  protected String destinationRealm;

  /**
   * 
   */
  public ClientRfSessionDataLocalImpl() {
  }

  public ClientRfSessionState getClientRfSessionState() {
    return state;
  }

  public void setClientRfSessionState(ClientRfSessionState state) {
    this.state = state;
  }

  public Serializable getTsTimerId() {
    return tsTimerId;
  }

  public void setTsTimerId(Serializable txTimerId) {
    this.tsTimerId = txTimerId;
  }

  public Request getBuffer() {
    return buffer;
  }

  public void setBuffer(Request buffer) {
    this.buffer = buffer;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#getDestinationHost()
   */
  @Override
  public String getDestinationHost() {
    return this.destinationHost;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#setDestinationHost(java.lang.String)
   */
  @Override
  public void setDestinationHost(String destinationHost) {
    this.destinationHost = destinationHost;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#getDestinationRealm()
   */
  @Override
  public String getDestinationRealm() {
    return this.destinationRealm;
  }

  /*
   * (non-Javadoc)
   * @see org.jdiameter.client.impl.app.rf.IClientRfSessionData#setDestinationRealm(java.lang.String)
   */
  @Override
  public void setDestinationRealm(String destinationRealm) {
    this.destinationRealm = destinationRealm;
  }

}
