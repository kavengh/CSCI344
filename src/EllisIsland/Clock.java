package EllisIsland;

public class Clock extends Thread {
	
	//current capacity of the theater
	public static  int currentCap = 0;
	
	public static int moviesLeft = 4;
	
	//status of the movie session
	public static boolean isInSession = false;
	
	public static long time = System.currentTimeMillis(); 
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	public synchronized static void movieScreening() {

		while(moviesLeft > 0) {
			synchronized(Driver.startTheMovie) {
				try {
					Driver.startTheMovie.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//System.out.println("PASSED THE LOCK FOR THE MOIVE");
			
			System.out.println("Movie " + moviesLeft + " Began");
			
			isInSession = true;
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//session is over
			isInSession = false;
			
			System.out.println("Movie " + moviesLeft + " Ended");			
			moviesLeft--;
			
			synchronized(Driver.speakerTheaterLock){
				Driver.speakerTheaterLock.notify();
			}
			
			synchronized(Driver.speakerDoneTheaterLock) {
				try {
					Driver.speakerDoneTheaterLock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.print("NO MORE MOVIES FOR THE DAY");
		
	}
	
	public synchronized static void nextShowing() {
		//TODO: this method will be called for the next showing
		
		//what happens when the movie ends?
		
	}
	
	public void run() {
		
		movieScreening();
		
		
	}

}
