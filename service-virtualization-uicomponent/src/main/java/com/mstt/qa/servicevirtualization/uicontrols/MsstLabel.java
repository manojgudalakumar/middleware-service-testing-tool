package com.mstt.qa.servicevirtualization.uicontrols;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class MsstLabel extends Label {

	public MsstLabel(String lableName) {
		super(lableName);
		initialize();
	}

	DropShadow shadow = new DropShadow();

	private void initialize() {
		this.addEventHandler(MouseEvent.MOUSE_ENTERED, (
				MouseEvent mouseEnteredEvent) -> {
			this.setEffect(shadow);
		});

		// Removing the shadow when the mouse cursor is off
		this.addEventHandler(MouseEvent.MOUSE_EXITED, (
				MouseEvent mouseExitedEvent) -> {
			this.setEffect(null);
		});
	}
}