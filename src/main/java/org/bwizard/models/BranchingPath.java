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

import java.util.ArrayList;

import org.bwizard.WizardStep;

/**
 * BranchingPaths represent a sequence of {@link WizardStep}s that has multiple
 * choices for the next path to traverse.
 * 
 * @author Carlos Alberto Gomez Ormachea
 * @version 2.0, 03/07/2009
 * @see #addBranch
 * @see #addStep
 */
public class BranchingPath extends Path {

	private ArrayList<BPath> paths = new ArrayList<BPath>();

	/**
	 * Creates a new empty BranchingPath.
	 */
	public BranchingPath() {
	}

	/**
	 * Creates a new BranchingPath that is initialized with the specified step.
	 * 
	 * @param step
	 *            the first step of the path.
	 */
	public BranchingPath(WizardStep step) {
		addStep(step);
	}

	/**
	 * Gets the path to traverse after this path has exhausted all its steps.
	 * This method will call iterate over each path selector to determine the
	 * path to return.
	 * 
	 * @return the next path in the sequence.
	 * @throws IllegalStateException
	 *             if no matching path is found.
	 */
	protected Path getNextPath(MultiPathModel model) {
		for (BPath entry : paths) {
			Condition condition = entry.getCondition();
			if (condition.evaluate(model))
				return (Path) entry.getPath();
		}
		throw new IllegalStateException("No next path selected");
	}

	/**
	 * Adds a possible branch from this path.
	 * 
	 * @param path
	 *            the {@link Path} to traverse based when the condition returns
	 *            <tt>true</tt>.
	 * @param condition
	 *            a {@link Condition} that activates this path.
	 */
	public void addBranch(Path path, Condition condition) {
		paths.add(new BPath(path, condition));
	}

	public void acceptVisitor(PathVisitor visitor) {
		visitor.visitPath(this);
	}

	public void visitBranches(PathVisitor visitor) {
		for (BPath path : paths) {
			path.getPath().acceptVisitor(visitor);
		}
	}

	private class BPath {

		private Path path;

		private Condition condition;

		public BPath(Path path, Condition condition) {
			this.path = path;
			this.condition = condition;
		}

		public Condition getCondition() {
			return condition;
		}

		public Path getPath() {
			return path;
		}
	}
}
