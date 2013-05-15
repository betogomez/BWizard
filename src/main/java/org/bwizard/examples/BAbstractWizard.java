package org.bwizard.examples;

import javax.swing.JComponent;

import org.bwizard.DefaultTitleComponent;
import org.bwizard.Wizard;
import org.bwizard.WizardEvent;
import org.bwizard.WizardListener;
import org.bwizard.WizardModel;

public class BAbstractWizard extends Wizard implements WizardListener {

	private static final long serialVersionUID = 1L;

	public BAbstractWizard(WizardModel wizardModel) {
		super(wizardModel);
		addWizardListener(this);
		setDefaultExitMode(EXIT_ON_CLOSE);
	}

	protected JComponent createTitleComponent() {
		DefaultTitleComponent titleComponent = (DefaultTitleComponent) super
				.createTitleComponent();

		titleComponent.setGradientBackground(true);
		return titleComponent;
	}

	public void wizardClosed(WizardEvent e) {
		setVisible(false);
		try {
			finalize();
			System.gc();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}

	public void wizardCancelled(WizardEvent e) {
		setVisible(false);
		try {
			finalize();
			System.gc();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}
}
