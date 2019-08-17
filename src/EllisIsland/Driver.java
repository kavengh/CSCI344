package EllisIsland;

public class Driver extends Thread{

	public static Object canWatchLock = new Object();
	public static Object inTheaterLock = new Object();
	public static Object outsideTheater = new Object();
	public static Object speakerTheaterLock = new Object();
	public static Object speakerDoneTheaterLock = new Object();
	public static Object startTheMovie = new Object();
	public static Object leaveTheaterLock = new Object();
	//number of visitors
	public static  int numVisitor = 17;
	
	public static int waitingQ = 0;
	//total capacity of the theater
	public  int theaterCapacity = 8;
	public static int currentVisitors = 0;
	public static  Visitor [] hello = new Visitor[numVisitor];

	
	public static void main(String [] args) {
		Clock clock = new Clock();
		Speaker speaker = new Speaker();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i = 0; i < hello.length; i++) {
			hello[i] = new Visitor(i);
			System.out.println( hello[i].name + " was made");
		}
		
		speaker.start();
		clock.start();
		
		//starts the run method of all of the threads
		for(int i = 0; i< hello.length; i++) {
		
			hello[i].start();
			
		}
	}
	
	
}
