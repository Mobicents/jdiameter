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

package org.jdiameter.server.impl.app.acc;

import org.jdiameter.api.acc.events.AccountRequest;
import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.StateEvent;

/**
 * 
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
class Event implements StateEvent {

  enum Type{
    RECEIVED_EVENT_RECORD,
    RECEIVED_START_RECORD,
    RECEIVED_INTERIM_RECORD,
    RECEIVED_STOP_RECORD
  }

  Type type;
  AppEvent data;

  Event(Type type) {
    this.type = type;
  }

  Event(AccountRequest accountRequest) throws Exception {
    data = accountRequest;
    int type = accountRequest.getAccountingRecordType();
    switch (type) {
    case 1:
      this.type = Type.RECEIVED_EVENT_RECORD;
      break;
    case 2:
      this.type = Type.RECEIVED_START_RECORD;
      break;
    case 3:
      this.type = Type.RECEIVED_INTERIM_RECORD;
      break;
    case 4:
      this.type = Type.RECEIVED_STOP_RECORD;
      break;
    default:
      throw new Exception("Unknown type " + type);
    }
  }

  public <E> E encodeType(Class<E> eClass) {
    return eClass == Type.class ? (E) type : null;
  }

  public Enum getType() {
    return type;
  }

  public void setData(Object o) {
    data = (AppEvent) o;
  }

  public Object getData() {
    return data;
  }

  public int compareTo(Object other) {
    return equals(other) ? 0 : -1;
  }

  public boolean equals(Object other) {
    return this == other;
  }
}
