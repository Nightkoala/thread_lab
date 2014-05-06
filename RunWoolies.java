/*
 * RunWoolies.java
 *
 * Version:
 * $Id: RunWoolies.java,v 1.2 2013/05/08 03:52:46 djb3718 Exp $
 * 
 * Revisions:
 * $Log: RunWoolies.java,v $
 * Revision 1.2  2013/05/08 03:52:46  djb3718
 * Final Revision
 *
 * Revision 1.1  2013/05/02 15:39:22  djb3718
 * Initial Revision
 *
 * Revision 1.1  2012/05/03 21:56:42  vcss243
 * Initial revision
 *
 */

/**
 * Test the TrollsBridge and Woolies simulation.
 * Test by creating a bunch of Woolies and let them cross the TrollsBridge.
 * <p>
 * Note: java -enableassertions should cause Woolies to validate their side.
 * </p>
 * @author     Ben Steele
 * @author Derek Brown (djb3718@rit.edu)
 */
public class RunWoolies {

	/** SIDE_ONE is Merctran.  */
	public final static String SIDE_ONE = "Merctran";

	/** SIDE_TWO is Sicstine.  */
	public final static String SIDE_TWO = "Sicstine";

	/** 
	 * Command interface for collecting all the functions in this test suite.
	 * Single method is Command.execute().
	 */
	private interface Command {
		public void execute();
	}

	/** 
	 * testSuite is the list of test cases.
	 */
	private static Command[] testSuite = {
		new Command() { public void execute() { RunWoolies.test0(); }},
		new Command() { public void execute() { RunWoolies.test1(); }},
		new Command() { public void execute() { RunWoolies.test2(); }},
		new Command() { public void execute() { RunWoolies.test3(); }},
	};

	/** TEST_COUNT is number of test cases.  */
	public final static int TEST_COUNT = testSuite.length;

	/**
	 * test0 is Test Scenario 0, an extremely simple, non-waiting test.
	 * test0 provides an example template/pattern for writing a test case.
	 */
	static void test0() {

		System.out.println( "Begin test0. ===============================\n" );

		Thread init = Thread.currentThread();      // init spawns the Woolies

		// Create a TrollsBridge of capacity 3.
		TrollsBridge trollBridge = new TrollsBridge( 3 );

		// Set an optional, test delay to stagger the start of each woolie.
		int delay = 4000;

		// Create the Woolies and store them in an array.
		Thread peds[] = {
				new Woolie( "Al",    3, SIDE_ONE, trollBridge ),
				new Woolie( "Bob",   4, SIDE_TWO, trollBridge ),
		};

		for ( int j = 0; j < peds.length; ++j ) {
			// Run them by calling their start() method.
			try {
				peds[j].start();
				init.sleep( delay );
			}
			catch ( InterruptedException e ) {
				System.err.println( "Abort. Unexpected thread interruption." );
				break;
			}
		}
		// Now, the test must give the woolies time to finish their crossings.
		for ( int j = 0; j < peds.length; ++j ) {
			try {
				peds[j].join();
			}
			catch ( InterruptedException e ) {
				System.err.println( "Abort. Unexpected thread interruption." );
				break;
			}
		}
		System.out.println( "\n=============================== End test0." );
		return;
	}

	/**
	 * test1 is Test Scenario 1, another fairly simple simulation run.
	 * test1 provides another example for writing a test case.
	 */
	static void test1() {

		System.out.println( "Begin test1. ===============================\n" );

		Thread init = Thread.currentThread();      // init spawns the Woolies

		// Create a TrollsBridge of capacity 3.
		TrollsBridge trollBridge = new TrollsBridge( 3 );

		int delay = 1000;

		// Create the Woolies and store them in an array.
		Thread peds[] = {
				new Woolie( "Al",    3, SIDE_ONE, trollBridge ),
				new Woolie( "Bob",   2, SIDE_ONE, trollBridge ),
				new Woolie( "Cathy", 2, SIDE_TWO, trollBridge ),
				new Woolie( "Doris", 3, SIDE_TWO, trollBridge ),
				new Woolie( "Edith", 3, SIDE_ONE, trollBridge ),
				new Woolie( "Fred",  2, SIDE_TWO, trollBridge ),
		};

		for ( int j = 0; j < peds.length; ++j ) {
			// Run them by calling their start() method.
			try {
				peds[j].start();
				init.sleep( delay );         // delay start of next woolie
			}
			catch ( InterruptedException e ) {
				System.err.println( "Abort. Unexpected thread interruption." );
			}
		}
		// Now, the test must give the woolies time to finish their crossings.
		for ( int j = 0; j < peds.length; ++j ) {
			try {
				peds[j].join();              // wait for next woolie to finish
			}
			catch ( InterruptedException e ) {
				System.err.println( "Abort. Unexpected thread interruption." );
			}
		}

		System.out.println( "\n=============================== End test1." );
	}

	/**
	 * Test all the Woolies arriving at the same time.
	 */
	static void test2() {

		System.out.println( "Begin test2. ===============================\n" );

		Thread init = Thread.currentThread();      // init spawns the Woolies
		
		TrollsBridge trollBridge = new TrollsBridge(3);
		
		Thread peds[] = {
			new Woolie( "Pikachu",   4, SIDE_ONE, trollBridge),
			new Woolie( "Squirtle",  5, SIDE_ONE, trollBridge),
			new Woolie( "Gyarados",  6, SIDE_TWO, trollBridge),
			new Woolie( "Psyduck",   3, SIDE_TWO, trollBridge),
			new Woolie( "Pidgey",    3, SIDE_TWO, trollBridge),
			new Woolie( "Sandslash", 3, SIDE_ONE, trollBridge),
		};
		
		for ( int j = 0; j < peds.length; ++j ) {
			peds[j].start();
		}
		
		for ( int j = 0; j < peds.length; ++j ) {
			try {
				peds[j].join();              // wait for next woolie to finish
			}
			catch ( InterruptedException e ) {
				System.err.println( "Abort. Unexpected thread interruption." );
			}
		}

		System.out.println( "\n=============================== End test2." );
	}

	/**
	 * Testing a slightly more complex configuration of Woolies
	 */
	static void test3() {

		System.out.println( "Begin test3. ===============================\n" );

		Thread init = Thread.currentThread();      // init spawns the Woolies

		TrollsBridge trollBridge = new TrollsBridge(3);
		
		int delay = 1000;
		
		Thread peds[] = {
			new Woolie( "Pikachu",   8, SIDE_ONE, trollBridge),
			new Woolie( "Squirtle",  5, SIDE_ONE, trollBridge),
			new Woolie( "Gyarados",  7, SIDE_TWO, trollBridge),
			new Woolie( "Psyduck",   4, SIDE_TWO, trollBridge),
			new Woolie( "Pidgey",    4, SIDE_TWO, trollBridge),
			new Woolie( "Sandslash", 3, SIDE_ONE, trollBridge),
		};
		
		for ( int j = 0; j < peds.length; ++j ) {
			// Run them by calling their start() method.
			try {
				peds[j].start();
				init.sleep( delay );         // delay start of next woolie
			}
			catch ( InterruptedException e ) {
				System.err.println( "Abort. Unexpected thread interruption." );
			}
		}
		
		for ( int j = 0; j < peds.length; ++j ) {
			try {
				peds[j].join();              // wait for next woolie to finish
			}
			catch ( InterruptedException e ) {
				System.err.println( "Abort. Unexpected thread interruption." );
			}
		}
		
		System.out.println( "\n=============================== End test3." );
	}

	/**
	 * Run all the tests in this test suite.
	 *
	 * @param args not used
	 */
	public static void main( String args[] ) {

		for ( int j = 0 ; j < TEST_COUNT; ++j ) {
			testSuite[j].execute();
		}
	}
}