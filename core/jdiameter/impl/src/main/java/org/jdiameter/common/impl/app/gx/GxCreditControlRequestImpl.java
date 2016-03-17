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

package org.jdiameter.common.impl.app.gx;

import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Request;
import org.jdiameter.api.app.AppSession;
import org.jdiameter.api.gx.events.GxCreditControlRequest;
import org.jdiameter.common.impl.app.AppRequestEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public class GxCreditControlRequestImpl extends AppRequestEventImpl implements GxCreditControlRequest {

    private static final long serialVersionUID = 1L;
    protected final static Logger logger = LoggerFactory.getLogger(GxCreditControlRequestImpl.class);
    private static final int REQUESTED_ACTION_AVP_CODE = 436;
    private static final int CC_REQUEST_TYPE_AVP_CODE = 416;

    public GxCreditControlRequestImpl(AppSession session, String destRealm, String destHost) {
        super(session.getSessions().get(0).createRequest(code, session.getSessionAppId(), destRealm, destHost));
    }

    public GxCreditControlRequestImpl(Request request) {
        super(request);
    }

    public boolean isRequestedActionAVPPresent() {
        return super.message.getAvps().getAvp(REQUESTED_ACTION_AVP_CODE) != null;
    }

    public int getRequestedActionAVPValue() {
        Avp requestedActionAvp = super.message.getAvps().getAvp(REQUESTED_ACTION_AVP_CODE);
        if (requestedActionAvp != null) {
            try {
                return requestedActionAvp.getInteger32();
            } catch (AvpDataException e) {
                logger.debug("Failure trying to obtain Requested-Action AVP value", e);
            }
        }

        return -1;
    }

    public boolean isRequestTypeAVPPresent() {
        return super.message.getAvps().getAvp(CC_REQUEST_TYPE_AVP_CODE) != null;
    }

    public int getRequestTypeAVPValue() {
        Avp requestTypeAvp = super.message.getAvps().getAvp(CC_REQUEST_TYPE_AVP_CODE);
        if (requestTypeAvp != null) {
            try {
                return requestTypeAvp.getInteger32();
            } catch (AvpDataException e) {
                logger.debug("Failure trying to obtain CC-Request-Type AVP value", e);
            }
        }

        return -1;
    }
}
