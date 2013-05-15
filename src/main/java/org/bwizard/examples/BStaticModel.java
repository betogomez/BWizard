package org.bwizard.examples;

import javax.swing.JComponent;

import org.bwizard.HelpBroker;
import org.bwizard.OverviewProvider;
import org.bwizard.WizardModel;
import org.bwizard.models.StaticModel;

public class BStaticModel extends StaticModel implements OverviewProvider,
		HelpBroker {

	public BStaticModel() {
		super();
		setLastVisible(false);

		add(new FirstStep());
		add(new SecondStep());
	}
	
	@Override
	public void activateHelp(JComponent parent, WizardModel model) {
		System.out.println("help");
	}
	
	public static void main(String[] args) {
		new BWizardInFrame(new BStaticModel(), "Static Wizard Example");
	}
}
