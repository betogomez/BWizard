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

import org.bcommon.resources.ResourceNotFoundException;

/**
 * @author Carlos Alberto Gomez Ormachea
 * @version 2.0, 03/07/2009
 */
class HelpAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private Wizard wizard;

	protected HelpAction(Wizard wizard) {
		super();
		this.wizard = wizard;
		try {
			putValue(NAME, wresources.getString("help.text"));
			putValue(MNEMONIC_KEY, new Integer(wresources
					.getMnemonic("help.mnemonic")));
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

		wizard.addPropertyChangeListener("helpBroker",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						configureState();
					}
				});

		configureState();
	}

	public void actionPerformed(ActionEvent e) {
		HelpBroker helpBroker = wizard.getHelpBroker();

		if (helpBroker != null)
			helpBroker.activateHelp(wizard, wizard.getModel());
	}

	private void configureState() {
		setEnabled(wizard.getHelpBroker() != null);
	}
}
