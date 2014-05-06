/*
 * Woolie.java
 * 
 * Version:
 * $Id: Woolie.java,v 1.2 2013/05/08 03:52:46 djb3718 Exp $
 * 
 * Revision:
 * $Log: Woolie.java,v $
 * Revision 1.2  2013/05/08 03:52:46  djb3718
 * Final Revision
 *
 * Revision 1.1  2013/05/02 15:39:22  djb3718
 * Initial Revision
 *
 */

/**
 * The Woolie simulates a Woolie crossing a TRollsBridge.
 * 
 * Each woolie object is constructed with a name, length of time it takes 
 * the woolie to cross a bridge, a destination city, and a reference to a 
 * TrollsBridge whose troll coordinates how woolies get on and off their 
 * bridge.
 * 
 * A Woolie extends the Thread class and executes as an active object.
 * 
 * Before crossing, a woolie must ask the troll guarding the bridge for 
 * permission to cross.  After the troll grants permission, the woolie 
 * begins crossing the bridge.  After reaching the other side, the 
 * woolie must leave and notify the troll and everyone that it is no 
 * longer on the bridge.
 * 
 * @author Derek Brown (djb3718@rit.edu)
 */
public class Woolie extends Thread {

	// Fields
	
	/**
	 * The name of the Woolie
	 */
	private String name;
	
	/**
	 * The amount of time it takes the Woolie to cross the bridge.
	 */
	private int crossTime;
	
	/**
	 * The city the Woolie desires to go
	 */
	private String destination;
	
	/**
	 * The TrollsBridge object that the Woolie is crossing
	 */
	private TrollsBridge bridgeGuard;
	
	// Getters/Setters
	
	/**
	 * Returns the name of the Woolie.
	 * 
	 * @return - String representing the name of the Woolie
	 */
	public String getWName() { return name; }//end getWName
	
	// Constructor

	/**
	 * Cosntruct a new Woolie object that can run as a thread.  The 
	 * constructor simply initializes all of the instance's fields.
	 * 
	 * Preconditions:
	 * 		destination = "Sicstine" or "Merctran"
	 * 		crossTime >= 0
	 * 		name != null
	 * 		bridgeGuard != null
	 * 
	 * @param name - the name of this Woolie.
	 * @param crossTime - the number of seonds it takes the Woolie to 
	 * 					  cross after it has climbed onto the bridge.
	 * @param destination - the Woolie's destination city.
	 * @param bridgeGuard - the TrollsBridge that the Woolie is crossing.
	 */
	public Woolie(String name, int crossTime,
			String destination, TrollsBridge bridgeGuard) {
		this.name = name;
		this.crossTime = crossTime;
		this.destination = destination;
		this.bridgeGuard = bridgeGuard;
	}//end constructor
	
	// Methods
	
	/**
	 * The run method handles a Woolie's behavior as it crosses the 
	 * bridge.  The well-behaved Woolie asks the troll at the bridge to 
	 * cross.  While it is crossing the bridge, it reports its progress 
	 * each second as it works its way across, and the woolie lastly 
	 * tells the troll that it has gotten off the bridge.
	 * 
	 * There are several messages that a Woolie thread must display to 
	 * describe their progress crossing the bridge.
	 * 
	 * When the Woolie starts crossing the bridge, at time 0, display the 
	 * message:
	 * 		<name> is starting to cross.
	 * 
	 * For every one second interval, beyond time 0, that the Woolie is 
	 * on the bridge, display the message:
	 * 		<name> <time> seconds.
	 * 
	 * When the Woolie reaches it's destination, display the message:
	 * 		<name> leaves at <city>.
	 */
	public void run() {
		
		bridgeGuard.enterBridgePlease(this);
		
		// start crossing bridge
		System.out.println(name + " is starting to cross.");
		
		try {
			this.sleep(1000);
		}//end try
		catch (InterruptedException e) {
			System.err.println("Unexpected thread interuption. Abort.");
			e.printStackTrace();
		}//end catch
		
		// actual bridge crossing
		int i = 1;
		while ( i < crossTime ) {
			System.out.println(name + " " + i + " seconds");
			i++;
			try {
				this.sleep(1000);
			}//end try
			catch (InterruptedException e) {
				System.err.println("Unexpected thread interuption. Abort.");
				e.printStackTrace();
			}//end catch
		}//end while
		
		//leaving bridge
		System.out.println(name + " leaves at " + destination);

		bridgeGuard.leave();
		
	}//end run
}//end Woolie
