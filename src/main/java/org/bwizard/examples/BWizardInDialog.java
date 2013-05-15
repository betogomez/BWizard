package org.bwizard.examples;

import java.awt.Component;

import org.bwizard.WizardModel;

public class BWizardInDialog extends BAbstractWizard {

	private static final long serialVersionUID = 1L;

	public BWizardInDialog(WizardModel wizardModel, String name,
			Component parent) {
		super(wizardModel);
		showInDialog(name, parent, true);
	}
}
