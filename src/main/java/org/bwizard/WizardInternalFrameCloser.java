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

import javax.swing.JInternalFrame;

/**
 * This class implements {@link WizardListener} and is used to hide and dispose
 * JInternalFrames when a wizard has been completed or canceled.
 * 
 * @author Carlos Alberto Gomez Ormachea
 * @version 2.0, 03/07/2009
 */
public class WizardInternalFrameCloser implements WizardListener {
	private JInternalFrame frame;

	private Wizard wizard;

	/**
	 * Creates a new closer that monitors the specified wizard and closes the
	 * parent frame.
	 * 
	 * @param wizard
	 * @param frame
	 */
	public static void bind(Wizard wizard, JInternalFrame frame) {
		new WizardInternalFrameCloser(wizard, frame);
	}

	/**
	 * Constructs a new closer for the specified wizard in the specified window.
	 */
	private WizardInternalFrameCloser(Wizard wizard, JInternalFrame frame) {
		this.frame = frame;
		this.wizard = wizard;
		this.wizard.addWizardListener(this);
	}

	public void wizardClosed(WizardEvent e) {
		closeFrame();
	}

	public void wizardCancelled(WizardEvent e) {
		closeFrame();
	}

	public void closeFrame() {
		frame.setVisible(false);
		frame.dispose();
		wizard.removeWizardListener(WizardInternalFrameCloser.this);
	}
}
