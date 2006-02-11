/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.internal.databinding.nonapi.swt;

import org.eclipse.jface.internal.databinding.api.observable.value.AbstractObservableValue;
import org.eclipse.jface.internal.databinding.api.observable.value.ValueDiff;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @since 3.2
 * 
 */
public class ButtonObservableValue extends AbstractObservableValue {

	private final Button button;

	private boolean selectionValue;

	private Listener updateListener = new Listener() {
		public void handleEvent(Event event) {
			boolean oldSelectionValue = selectionValue;
			selectionValue = button.getSelection();
			fireValueChange(new ValueDiff(new Boolean(oldSelectionValue),
					new Boolean(selectionValue)));
		}
	};

	/**
	 * @param button
	 * @param updatePolicy
	 */
	public ButtonObservableValue(Button button) {
		this.button = button;
		button.addListener(SWT.Selection, updateListener);
		button.addListener(SWT.DefaultSelection, updateListener);
	}

	public void setValue(final Object value) {
		boolean oldSelectionValue = selectionValue;
		selectionValue = value == null ? false : ((Boolean) value)
				.booleanValue();
		button.setSelection(selectionValue);
		fireValueChange(new ValueDiff(new Boolean(oldSelectionValue),
				new Boolean(selectionValue)));
	}

	public Object doGetValue() {
		return new Boolean(button.getSelection());
	}

	public Object getValueType() {
		return boolean.class;
	}

}
