package org.bwizard.examples;

import org.bwizard.InvalidStateException;
import org.bwizard.PanelWizardStep;
import org.bwizard.WizardModel;

public class FirstStep extends PanelWizardStep {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private BStaticModel model;

	public FirstStep() {
		super("First Step", "This is the sumary for first step");
	}

	@Override
	public void init(WizardModel model2) {
		model = (BStaticModel) model2;
	}

	@Override
	public void prepare() {
		setComplete(true);
	}

	@Override
	public void applyState() throws InvalidStateException {
	}
}
