/**
 * BWizard Framework
 * Copyright 2008 - 2009 Carlos Alberto Gomez Ormachea
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.bwizard;

import java.awt.event.ActionEvent;

/**
 * @author Carlos Alberto Gomez Ormachea
 * @version 2.0, 03/07/2009
 */
class FinishAction extends WizardAction {

	private static final long serialVersionUID = 1L;

	protected FinishAction(Wizard model) {
		super("finish", model);
	}

	public void doAction(ActionEvent e) throws InvalidStateException {
		WizardStep finishStep = getModel().getActiveStep();
		finishStep.applyState();

		int defaultCloseOperation = getWizard().getDefaultExitMode();
		if (defaultCloseOperation == Wizard.EXIT_ON_FINISH)
			getWizard().getCloseAction().actionPerformed(e);
		else if (defaultCloseOperation == Wizard.EXIT_ON_CLOSE)
			getWizard().showCloseButton();
		else
			throw new InvalidStateException("Invalid finish operation: "
					+ defaultCloseOperation);
	}

	protected void updateState() {
		WizardStep activeStep = getActiveStep();
		setEnabled(activeStep != null && getModel().isLastStep(activeStep)
				&& activeStep.isComplete() && !activeStep.isBusy());
	}
}
