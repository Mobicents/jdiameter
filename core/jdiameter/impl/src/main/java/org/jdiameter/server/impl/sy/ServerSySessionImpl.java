/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2016, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.jdiameter.server.impl.sy;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Session;
import org.jdiameter.api.Request;
import org.jdiameter.api.Answer;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.api.app.StateEvent;
import org.jdiameter.api.auth.events.SessionTermAnswer;
import org.jdiameter.api.auth.events.SessionTermRequest;
import org.jdiameter.api.sy.ServerSySession;
import org.jdiameter.api.sy.ServerSySessionListener;
import org.jdiameter.api.sy.events.SpendingLimitAnswer;
import org.jdiameter.api.sy.events.SpendingLimitRequest;
import org.jdiameter.api.sy.events.SpendingStatusNotificationRequest;
import org.jdiameter.client.api.ISessionFactory;
import org.jdiameter.common.api.app.IAppSessionData;
import org.jdiameter.common.api.app.IAppSessionState;
import org.jdiameter.common.api.app.sy.ISyMessageFactory;
import org.jdiameter.common.api.app.sy.ServerSySessionState;
import org.jdiameter.common.impl.app.sy.AppSySessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Policy and charging control, Spending Limit Report - Sy session implementation
 *
 * @author <a href="mailto:aferreiraguido@gmail.com"> Alejandro Ferreira Guido </a>
 */

public class ServerSySessionImpl extends AppSySessionImpl implements ServerSySession, NetworkReqListener, EventListener<Request, Answer> {

  private static final Logger logger = LoggerFactory.getLogger(ServerSySessionImpl.class);

  protected IServerSySessionData sessionData;
  // Session State Handling ---------------------------------------------------
  protected Lock sendAndStateLock = new ReentrantLock();

  protected transient ISyMessageFactory factory = null;
  protected transient ServerSySessionListener listener = null;

  public ServerSySessionImpl(IServerSySessionData data, ISessionFactory sf, ServerSySessionListener lst, IAppSessionData appSessionData) {
    super(sf, appSessionData);

    this.sessionData = data;
    this.listener = lst;
  }

  @Override
  public Answer processRequest(Request request) {
    RequestDelivery rd = new RequestDelivery();
    rd.session = this;
    rd.request = request;
    super.scheduler.execute(rd);
    return null;
  }

  @Override
  public void sendSpendingLimitAnswer(SpendingLimitAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    handleEvent(new Event(false, null, answer));
  }

  @Override
  public void sendFinalSpendingLimitAnswer(SessionTermAnswer answer) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    handleEvent(new Event(false, null, answer));
  }

  @Override
  public void sendSpendingStatusNotificationRequest(SpendingStatusNotificationRequest request) throws InternalException, IllegalDiameterStateException, RouteException, OverloadException {
    handleEvent(new Event(true, request, null));
  }

  @Override
  public void receivedSuccessMessage(Request request, Answer answer) {
    AnswerDelivery ad = new AnswerDelivery();
    ad.session = this;
    ad.request = request;
    ad.answer = answer;
    super.scheduler.execute(ad);
  }

  @Override
  public void timeoutExpired(Request request) {

  }

  @Override
  public boolean isStateless() {
    return false;
  }

  @Override
  public ApplicationId getSessionAppId() {
    return null;
  }

  @Override
  public List<Session> getSessions() {
    return null;
  }

  @Override
  public void addStateChangeNotification(StateChangeListener listener) {

  }

  @Override
  public void removeStateChangeNotification(StateChangeListener listener) {

  }

  @Override
  public boolean handleEvent(StateEvent event) throws InternalException, OverloadException {
    ServerSySessionState newState = null;

    try {
      ServerSySessionState state = this.sessionData.getServerSySessionState();

      Event localEvent = (Event) event;

      Event.Type eventType = (Event.Type) event.getType();

      switch(state) {
        case IDLE:
          switch(eventType) {
            case RECEIVED_INITIAL:
              listener.doSpendingLimitRequest(this, (SpendingLimitRequest) localEvent.getRequest());
              break;
            case SENT_INITIAL_RESPONSE:
              newState = ServerSySessionState.OPEN;
              // dispatchEvent()
              session.send(((AppEvent)localEvent).getMessage());
              setState(newState);
              break;
          }
          break;
        case OPEN:
          switch(eventType) {
            case RECEIVED_INTERMEDIATE:
              listener.doSpendingLimitRequest(this, (SpendingLimitRequest) localEvent.getRequest());
              break;
            case RECEIVED_TERMINATION:
              listener.doFinalSpendingLimitRequest(this, (SessionTermRequest) localEvent.getRequest());
              break;
          }
          break;
      }
      return false;
    }
    catch (Exception e) {
      throw new InternalException(e);
    }
    finally {
      sendAndStateLock.unlock();
    }
  }

  @Override
  public <E> E getState(Class<E> stateType) {
    return null;
  }

  @Override
  public long getCreationTime() {
    return 0;
  }

  @Override
  public long getLastAccessedTime() {
    return 0;
  }

  @Override
  public boolean isValid() {
    return false;
  }

  protected void setState(ServerSySessionState newState) {
    setState(newState, true);
  }

  protected void setState(ServerSySessionState newState, boolean release) {
    IAppSessionState oldState = this.sessionData.getServerSySessionState();
    this.sessionData.setServerSySessionState(newState);

    for (StateChangeListener i : stateListeners) {
      i.stateChanged(this, (Enum) oldState, (Enum) newState);
    }
    if (newState == ServerSySessionState.IDLE) {
      // do EVERYTHING before this!
      //stopTcc(false);
      if (release) {
        this.release();
      }
    }
  }

  @Override
  public void release() {

  }

  @Override
  public boolean isAppSession() {
    return false;
  }

  @Override
  public boolean isReplicable() {
    return false;
  }

  @Override
  public void onTimer(String timerName) {

  }

  @Override
  public String getSessionId() {
    return null;
  }

  private class RequestDelivery implements Runnable {
    ServerSySession session;
    Request request;

    @Override
    public void run() {
      try {
        switch (request.getCommandCode()) {
          case SpendingLimitRequest.code:
            handleEvent(new Event(true, factory.createSpendingLimitRequest(request), null));
            break;
          case SessionTermRequest.code:
            handleEvent(new Event(true, factory.createSessionTerminationRequest(request), null));
            break;
          default:
            logger.debug("Unexpected request received on Sy channel", request);
            break;
        }
      }
      catch (Exception e) {
        logger.debug("Failed to process request message", e);
      }
    }
  }

  private class AnswerDelivery implements Runnable {
    ServerSySession session;
    Answer answer;
    Request request;

    @Override
    public void run() {
      try {
        // FIXME: baranowb: add message validation here!!!
        // We handle CCR, STR, ACR, ASR other go into extension
        switch (request.getCommandCode()) {
          /*case ReAuthRequest.code:
            handleEvent(new Event(Event.Type.RECEIVED_RAA, factory.createReAuthRequest(request), factory.createReAuthAnswer(answer)));
            break;*/
          default:
            //listener.doOtherEvent(session, new AppRequestEventImpl(request), new AppAnswerEventImpl(answer));
            break;
        }

      }
      catch (Exception e) {
        logger.debug("Failed to process success message", e);
      }
    }
  }

}