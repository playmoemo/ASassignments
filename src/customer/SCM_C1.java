package customer;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import server.GUI;
import server.TAC_Ontology;
import TACSCMApp.SCM;
import TACSCMApp.SCMHelper;

public class SCM_C1 extends SCM_Customer {
	public SCM_C1(SCM ser) {
		server = ser;
		gui = new GUI(getClassName());
	}

	public String getClassName() {
		return TAC_Ontology.C1;
	}

	public void run() {
		boolean isOn = getStatus();
		try {
			while (true) {
				isOn = getStatus();
				if (isOn && has_started == false) {
					startTheGame(getClassName());
					has_started = true;
				} else if (!isOn || interval >= TAC_Ontology.gameLength) {
					if (has_started) {
						close(isOn, getClassName());
						has_started = false;
					}
				} else if (isOn) {
					// compute day and time
					day = interval / TAC_Ontology.lengthOfADay;
					int time = interval % TAC_Ontology.lengthOfADay;
					// write the day number in the beginning of the day
					if (time == 0) {
						gui.append("\nday: " + day);
					}
				}
				interval++;
				sleep(TAC_Ontology.sec);
			}
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

	public static void main(String args[]) {
		try {
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);
			// get the root naming context
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is
			// part of the naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			// resolve the Object Reference in Naming
			String name = "TACSCM";
			SCM server = SCMHelper.narrow(ncRef.resolve_str(name));
			//
			SCM_C1 customer = new SCM_C1(server);
			customer.run();
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}