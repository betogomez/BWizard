package org.bwizard.examples;

import org.bwizard.WizardModel;

public class BWizardInFrame extends BAbstractWizard {

	private static final long serialVersionUID = 1L;

	public BWizardInFrame(WizardModel wizardModel, String name) {
		super(wizardModel);
		showInFrame(name, null, null);
	}
}
