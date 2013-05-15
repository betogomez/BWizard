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

package org.bwizard.models;

import static org.bwizard.Wizard.wresources;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bcommon.resources.ResourceNotFoundException;
import org.bwizard.WizardStep;

/**
 * This class provides an overview panel for instances of {@link StaticModel}.
 * 
 * @author Carlos Alberto Gomez Ormachea
 * @version 2.0, 03/07/2009
 */
public class MultiPathModelOverview extends JPanel implements
		PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private MultiPathModel model;

	private HashMap<WizardStep, JLabel> labels = new HashMap<WizardStep, JLabel>();

	public MultiPathModelOverview(MultiPathModel model) {
		this.model = model;
		this.model.addPropertyChangeListener(this);
		setBackground(Color.WHITE);
		// we do this to ensure laf's like quaqua still render as opaque.
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		String stitle = "";
		try {
			stitle = wresources.getString("StaticModelOverview.title");
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}

		JLabel title = new JLabel(stitle);
		title.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));

		title.setAlignmentX(0);
		title.setMaximumSize(new Dimension(Integer.MAX_VALUE, title
				.getMaximumSize().height));
		add(title);
		int i = 1;

		for (Iterator<WizardStep> iter = model.stepIterator(); iter.hasNext();) {

			WizardStep step = iter.next();
			JLabel label = new JLabel("" + (i++) + ". " + step.getName());

			label.setBackground(new Color(240, 240, 240));
			label.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
			label.setAlignmentX(0);
			label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label
					.getMaximumSize().height));
			add(label);
			labels.put(step, label);
		}

		add(Box.createGlue());
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("activeStep")) {
			JLabel old = labels.get(evt.getOldValue());
			if (old != null)
				formatInactive(old);

			JLabel label = labels.get(evt.getNewValue());
			formatActive(label);
			repaint();
		}
	}

	protected void formatActive(JLabel label) {
		label.setOpaque(true);
	}

	protected void formatInactive(JLabel label) {
		label.setOpaque(false);
	}
}
