/*******************************************************************************
 * OpenEMS - Open Source Energy Management System
 * Copyright (c) 2016 FENECON GmbH and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *   FENECON GmbH - initial API and implementation and initial documentation
 *******************************************************************************/
package io.openems.impl.controller.asymmetric.avoidtotaldischarge;

import io.openems.api.channel.DebugChannel;
import io.openems.api.channel.ReadChannel;
import io.openems.api.channel.WriteChannel;
import io.openems.api.controller.IsThingMap;
import io.openems.api.controller.ThingMap;
import io.openems.api.device.nature.ess.AsymmetricEssNature;
import io.openems.api.exception.InvalidValueException;

@IsThingMap(type = AsymmetricEssNature.class)
public class Ess extends ThingMap {
	public ReadChannel<Long> soc;
	public ReadChannel<Integer> minSoc;
	public ReadChannel<Integer> chargeSoc;
	public WriteChannel<Long> setActivePowerL1;
	public WriteChannel<Long> setActivePowerL2;
	public WriteChannel<Long> setActivePowerL3;
	public WriteChannel<Long> setReactivePowerL1;
	public WriteChannel<Long> setReactivePowerL2;
	public WriteChannel<Long> setReactivePowerL3;
	public ReadChannel<Long> systemState;
	public ReadChannel<Long> allowedCharge;
	public ReadChannel<Long> allowedDischarge;
	public State currentState = State.NORMAL;
	public DebugChannel<Integer> stateMachineState;

	public enum State {
		NORMAL(0), MINSOC(1), CHARGESOC(2), FULL(3),EMPTY(4);

		private final int value;

		State(int value){
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	public Ess(AsymmetricEssNature ess) {
		super(ess);
		minSoc = ess.minSoc().required();
		chargeSoc = ess.chargeSoc().required();
		setActivePowerL1 = ess.setActivePowerL1().required();
		setActivePowerL2 = ess.setActivePowerL2().required();
		setActivePowerL3 = ess.setActivePowerL3().required();
		setReactivePowerL1 = ess.setReactivePowerL1().required();
		setReactivePowerL2 = ess.setReactivePowerL2().required();
		setReactivePowerL3 = ess.setReactivePowerL3().required();
		soc = ess.soc().required();
		systemState = ess.systemState().required();
		allowedCharge = ess.allowedCharge().required();
		allowedDischarge = ess.allowedDischarge().required();
		stateMachineState  = new DebugChannel<>("AvoidTotalDischargeState", ess);
	}

	public long useableSoc() throws InvalidValueException {
		return soc.value() - minSoc.value();
	}
}
