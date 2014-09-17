package agent;

import java.text.SimpleDateFormat;
import server.GUI;
import server.TAC_Ontology;
import server.Util;
import yinyang.Message;
import TACSCMApp.SCM;

public class SCM_Agent extends Thread {
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

	protected void registering(String agentName, int id) {
		Message kqml = Util.buildKQML(TAC_Ontology.agent_registering,
				agentName, " " + id);
		String str = server.send(kqml.toString());
		Message response = Message.buildMessage(str);
		gui.append("In " + response.getContent());
	}
} // end of SCM_Agent