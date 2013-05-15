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

package org.bcommon.resources;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * @author Carlos Alberto Gomez Ormachea
 * @version 2.0, 03/07/2009
 */
public class Resources {

	private ResourceBundle bundle = null;

	public Resources(String baseName) {
		try {
			bundle = ResourceBundle.getBundle(baseName);
		} catch (MissingResourceException e) {
			bundle = null;
		}
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public boolean existKey(String key) {
		return bundle.containsKey(key);
	}

	public String getString(String key) throws ResourceNotFoundException {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			throw new ResourceNotFoundException("The resource " + key
					+ " does not exist");
		}
	}

	public String getStringSafe(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "";
		}
	}

	public String[] getStringArray(String key) throws ResourceNotFoundException {
		String string = getString(key);
		if (string.trim().equals(""))
			return new String[0];
		return string.split(" ");
	}

	public URL getURL(String prefix, String key)
			throws ResourceNotFoundException {

		String value = getString(key);
		URL url = getClass().getClassLoader().getResource(prefix + value);

		if (url == null)
			throw new ResourceNotFoundException("The resource " + key
					+ " does not exist");
		return url;
	}

	public URL getURL(String key) throws ResourceNotFoundException {
		return getURL("", key);
	}

	public File getFile(String key) throws ResourceNotFoundException {
		return new File(getURL(key).getFile());
	}

	public ImageIcon getImage(String key) throws ResourceNotFoundException {
		String prefix = getString("prefix.images");
		return new ImageIcon(getURL(prefix, key));
	}

	public ImageIcon getImage(String key, int w, int h)
			throws ResourceNotFoundException {
		return scale(getImage(key), w, h);
	}

	public int getMnemonic(String key) throws ResourceNotFoundException {
		String mnemonicString = getString(key);

		if (mnemonicString == null)
			throw new ResourceNotFoundException("Missing resource: " + key);

		if (mnemonicString.length() != 1)
			throw new ResourceNotFoundException("Mnemonic string invalid: "
					+ mnemonicString);

		KeyStroke ks = KeyStroke.getKeyStroke(mnemonicString.toUpperCase());

		if (ks == null)
			throw new ResourceNotFoundException("Mnemonic string invalid: "
					+ mnemonicString);

		return ks.getKeyCode();
	}

	private static ImageIcon scale(ImageIcon icon, int w, int h) {
		icon.setImage(icon.getImage().getScaledInstance(w, h,
				Image.SCALE_SMOOTH));
		return icon;
	}
}
