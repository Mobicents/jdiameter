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
* The Spending-Notification-Request (SNR) message, indicated by the Command-Code field set to 8388636 is sent
* by the OCS to the PCRF when the OCS detects that status of a policy counter identifier(s) had changed.
*
* @author <a href="mailto:aferreiraguido@gmail.com"> Alejandro Ferreira Guido </a>
* @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
* @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
*/
public interface SpendingStatusNotificationRequest extends AppAnswerEvent {

  String _SHORT_NAME = "SNR";
  String _LONG_NAME = "Spending-Status-Notification-Request";

  int code = 8388636;

}
