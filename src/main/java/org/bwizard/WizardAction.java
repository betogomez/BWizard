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

import static org.bwizard.Wizard.wresources;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.bcommon.resources.ResourceNotFoundException;

/**
 * Base class for all Wizard actions.
 * 
 * @author Carlos Alberto Gomez Ormachea
 * @version 2.0, 03/07/2009
 */
public abstract class WizardAction extends AbstractAction implements
		PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	protected Wizard wizard;

	private WizardStep activeStep;

	protected WizardAction(String key, Wizard wizard, Icon icon) {
		this(key, wizard);
		if (icon != null)
			putValue(SMALL_ICON, icon);
	}

	protected WizardAction(String key, Wizard wizard) {
		super();
		try {
			putValue(NAME, wresources.getString(key + ".text"));
			putValue(MNEMONIC_KEY, new Integer(wresources.getMnemonic(key
					+ ".mnemonic")));
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		this.wizard = wizard;
		getModel().addPropertyChangeListener(this);
		activeStep = getModel().getActiveStep();
		if (activeStep != null)
			activeStep.addPropertyChangeListener(this);

		updateState();
	}

	protected Wizard getWizard() {
		return wizard;
	}

	public WizardModel getModel() {
		return getWizard().getModel();
	}

	public WizardStep getActiveStep() {
		return activeStep;
	}

	public final void actionPerformed(ActionEvent e) {
		try {
			doAction(e);
		} catch (InvalidStateException ise) {
			handleInvalideStateException(ise);
		}
	}

	protected void handleInvalideStateException(InvalidStateException ise) {
		if (ise.isShowUser()) {
			JOptionPane.showMessageDialog(getWizard(), ise.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public abstract void doAction(ActionEvent e) throws InvalidStateException;

	protected abstract void updateState();

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("activeStep")) {
			if (activeStep != null)
				activeStep.removePropertyChangeListener(this);
			activeStep = (WizardStep) evt.getNewValue();
			activeStep.addPropertyChangeListener(this);
		}

		updateState();
	}

}
