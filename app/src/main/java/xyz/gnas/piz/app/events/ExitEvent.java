package xyz.gnas.piz.app.events;

import javafx.stage.WindowEvent;

public class ExitEvent {
	private WindowEvent windowEvent;

	public WindowEvent getWindowEvent() {
		return windowEvent;
	}

	public ExitEvent(WindowEvent windowEvent) {
		this.windowEvent = windowEvent;
	}
}