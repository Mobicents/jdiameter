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

package org.jdiameter.client.impl.app.rx;

import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.app.AppAnswerEvent;
import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.AppRequestEvent;
import org.jdiameter.api.app.StateEvent;

import org.jdiameter.api.rx.events.RxAARequest;
import org.jdiameter.api.auth.events.ReAuthRequest;
import org.jdiameter.api.auth.events.AbortSessionRequest;
import org.jdiameter.api.auth.events.SessionTermRequest;

import org.jdiameter.api.rx.events.RxAAAnswer;
import org.jdiameter.api.auth.events.ReAuthAnswer;
import org.jdiameter.api.auth.events.AbortSessionAnswer;
import org.jdiameter.api.auth.events.SessionTermAnswer;

/**
 * 
 * @author <a href="mailto:richard.good@smilecoms.com"> Richard Good </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class Event implements StateEvent {

  public enum Type {
    SEND_AAR,           RECEIVE_AAA,
    SEND_STR,           RECEIVE_STA,
    SEND_RAA,           RECEIVE_RAR,
    SEND_ASA,           RECEIVE_ASR,
    SEND_EVENT_REQUEST, RECEIVE_EVENT_ANSWER;
  }

  Type type;
  AppRequestEvent request;
  AppAnswerEvent answer;

  Event(Type type) {
    this.type = type;
  }

  Event(Type type, AppRequestEvent request, AppAnswerEvent answer) {
    this.type = type;
    this.answer = answer;
    this.request = request;
  }

  Event(boolean isRequest, AppRequestEvent request, AppAnswerEvent answer) throws AvpDataException {

    this.answer = answer;
    this.request = request;

    if (isRequest) {
      switch (request.getCommandCode()) {
        case RxAARequest.code:
          type = Type.SEND_AAR;
          break;
        case SessionTermRequest.code:
          type = Type.SEND_STR;
          break;
        case ReAuthRequest.code:
          type = Type.RECEIVE_RAR;
          break;
        case AbortSessionRequest.code:
          type = Type.RECEIVE_ASR;
          break;
        case 5:  //BUG FIX How do we know this is an event and not a session? Do we need to fix this? Does Rx do event?
          type = Type.SEND_EVENT_REQUEST;
          break;
        default:
          throw new RuntimeException("Wrong command code value: " + request.getCommandCode());
      }
    }
    else {
      switch (answer.getCommandCode()) {
        case RxAAAnswer.code:
          type = Type.RECEIVE_AAA;
          break;
        case SessionTermAnswer.code:
          type = Type.RECEIVE_STA;
          break;
        case ReAuthAnswer.code:
          type = Type.SEND_RAA;
          break;
        case AbortSessionAnswer.code:
          type = Type.SEND_ASA;
        case 6: //BUG FIX How do we know this is an event and not a session? Do we need to fix this? Does Rx do event?
          type = Type.RECEIVE_EVENT_ANSWER;
          break;  
        default:
          throw new RuntimeException("Wrong CC-Request-Type value: " + answer.getCommandCode());
      }
    }
  }

  public Enum getType() {
    return type;
  }

  public int compareTo(Object o) {
    return 0;
  }

  public Object getData() {
    return request != null ? request : answer;
  }

  public void setData(Object data) {
    // FIXME: What should we do here?! Is it request or answer?
  }

  public AppEvent getRequest() {
    return request;
  }

  public AppEvent getAnswer() {
    return answer;
  }

  public <E> E encodeType(Class<E> eClass) {
    return eClass == Event.Type.class ? (E) type : null;
  }
}