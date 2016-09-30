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

package org.jdiameter.api.sy.events;

import org.jdiameter.api.app.AppAnswerEvent;

/**
 * The Spending-Limit-Answer (SLA) message, indicated by the Command-Code field set to 8388635 is sent
 * by the OCS to the PCRF as a response to SLR message.
 *
 * @author <a href="mailto:aferreiraguido@gmail.com"> Alejandro Ferreira Guido </a>
 */

public interface SpendingLimitAnswer extends AppAnswerEvent {

  String _SHORT_NAME = "SLA";
  String _LONG_NAME = "Spending-Limit-Answer";

  int code = 8388635;

}