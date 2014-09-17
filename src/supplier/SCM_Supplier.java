package supplier;

import java.text.SimpleDateFormat;
import server.GUI;
import TACSCMApp.SCM;

public class SCM_Supplier extends Thread {
	// pointer of the server
	SCM server;
	// count of time in seconds
	protected int interval = 0;
	protected int day = 0;
	protected boolean has_started = false;
	protected SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
	protected GUI gui;

	// ask for status, on or off
	protected boolean getStatus() {
		return server.status();
	}

	protected void startTheGame(String agent) {
		boolean isOn = getStatus();
		if (isOn) {
			interval = (int) server.getTime();
			gui.setText("-----> Time : " + interval + " seconds");
		}
	}

	// close the game round
	protected void close(boolean isOn, String agent) {
		if (isOn)
			return;
	}
} // end of SCM_Supplier