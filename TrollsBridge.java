/*
 * TrollsBridge.java
 * 
 * Version:
 * $Id: TrollsBridge.java,v 1.2 2013/05/08 03:52:46 djb3718 Exp $
 * 
 * Revision:
 * $Log: TrollsBridge.java,v $
 * Revision 1.2  2013/05/08 03:52:46  djb3718
 * Final Revision
 *
 * Revision 1.1  2013/05/02 15:39:22  djb3718
 * Initial Revision
 *
 */

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representation of the troll and bridge.
 * 
 * @author Derek Brown (djb3718@rit.edu)
 */
public class TrollsBridge {

	// Fields

	/**
	 * Stores the maximum number of woolies that can be on the bridge
	 * at any one time.
	 */
	private int max;

	/**
	 * Stores the current number of woolies on the bridge.
	 */
	private int curr;

	/**
	 * The line of woolies waiting to cross the bridge.
	 */
	private List<Woolie> waiting;

	// Constructor

	/**
	 * Constructs a TrollsBridge object necessary for Woolies to cross.
	 * 
	 * @param max - the max number of Woolies that can be on the bridge at any
	 * 				one time.
	 */
	public TrollsBridge(int max) {
		this.max = max;
		this.curr = 0;
		this.waiting = new LinkedList<Woolie>();
		this.waiting = Collections.synchronizedList(waiting);
	}//end constructor

	// Methods

	/**
	 * Request permission to go onto the troll's bridge.  Woolies call 
	 * this method to ask the troll to put them on the queue of woolies 
	 * trying to get on the bridge.  The Woolie (thread) waits until it 
	 * becomes the head of the queue and there is room on the troll's 
	 * bridge.
	 * 
	 * The troll of a TrollsBridge guards its bridge to make sure that 
	 * woolies get on the bridge in the order of their arrival.
	 * 
	 * The troll of a TrollsBridge prints the following message when the 
	 * Woolie shows up to get in line to cross the bridge:  
	 * The troll scowls "Get in line!" when <woolies_name> shows up at 
	 * the bridge.
	 * 
	 * Precondition:
	 * 		The calling thread is the Woolie instance itself.
	 * 
	 * Postcondition:
	 * 		The woolie got permission and has climbed onto the bridge.
	 * 		At some future time, the woolie must call leave() to get off.
	 * 
	 * @param thisWoolie - the Woolie trying to get on the bridge (the 
	 * 					   same object as Thread calling this method).
	 */
	public void enterBridgePlease(Woolie thisWoolie) {
		synchronized(thisWoolie) {
			System.out.println("The troll scowls 'Get in line!' when " +
					thisWoolie.getWName() + " shows up at the bridge.");

			waiting.add(thisWoolie);

			while ( curr == max ) {
				try {
					thisWoolie.wait();
				}//end try
				catch (InterruptedException e) {
					System.err.println("Unexpected thread interuption. Abort.");
					e.printStackTrace();
				}//end catch
			}//end while

			curr += 1;
			waiting.remove(0);
			
		}//end synchronized
	}//end enterBridgePlease

	/**
	 * Tell the troll of a TrollsBridge that a woolie has left the bridge 
	 * so that the troll can let other woolies get on if there is room.
	 * 
	 * A well-behaved Woolie always informs the troll of a TrollsBridge 
	 * that it (the caller) is getting off the bridge.
	 * 
	 * Preconditions:
	 * 		The calling thread is a Woolie instance that has already 
	 * 			called enterBridgePlease().  Because of this precondition, a 
	 *			Woolie argument is not needed.
	 * 
	 * Postconditions:
	 * 		There is one less Woolie on this TrollsBridge.
	 */
	public void leave() {
		curr -= 1;
		if ( !waiting.isEmpty() ) {
			synchronized(waiting.get(0)) {
				waiting.get(0).notifyAll();
			}//end synchronized
		}//end if
	}//end leave
}//end TrollsBridge
