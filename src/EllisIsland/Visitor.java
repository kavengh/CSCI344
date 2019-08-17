package EllisIsland;

public class Visitor extends Thread{

	public String name;

	//public  Vector<Visitor> g = new Vector<Visitor>(Manager.numVisitor);
	
	public Visitor() {}
	public static int rude = 0;
	int currentVisitors = 0;
	
	//constructs a visitor
	public Visitor(int id) {
		this.name = "Thread_" + (id+1);
	}
	
	public static long time = System.currentTimeMillis(); 
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	
	
	public synchronized void startWatching(){
		//create an object lock
			//synchronized scope that checks if can watch
			//each object will acquire the lock and attempt to watch
		while(true) {
			if(Clock.moviesLeft >0) {
				try {
					Thread.sleep((long)(Math.random() * 1000));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				synchronized(Driver.canWatchLock) {
					//if the session is over
					if(canWatch()){
	
						//TODO: What happens when the theater is open or has seats
						// waiting for signal from the clock thread
						Driver.currentVisitors++;
						//Clock.movieScreening();
						System.out.println( name + " Im going into the theater my ticket is " + Driver.currentVisitors);
	
					}else { // we cannot watch yet 
						
						//System.out.println(name + ", Leaving the room and waiting in the lobby");
						Driver.waitingQ++;
						System.out.println("WAITING Q Before the can watch lock: "+ Driver.waitingQ);
						try {
							Driver.canWatchLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(name + ", Am off the waiting queue");
						//end of waiting 
					}//end of else
				}//end of the synchronized scope
				
				if(Driver.currentVisitors == 8 ) {
					System.out.println("IM GONNA TRY TO START THE MOVIE");
					synchronized(Driver.startTheMovie) {
						Driver.startTheMovie.notify();
					}
					
				}
				
				synchronized(Driver.inTheaterLock) {
					try {
						Driver.inTheaterLock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				synchronized(Driver.canWatchLock) {
					Driver.canWatchLock.notify();
				}
				
					
				
				
				Driver.currentVisitors = 0;
				Driver.waitingQ--;
			}
		}
			
	}
	
	public synchronized int getChance(){
		int randomNum = (int)(Math.random()*100);
		return randomNum;
	}
	
	//helper for the start watching function
	public synchronized  boolean canWatch() {
		//boolean to keep track of theater vacancy 
		boolean enterStatus;
		//when the movie is in session or the theater is full we can't enter
		if(Clock.isInSession ||Driver.currentVisitors == 8) { 
			enterStatus = false;
		}else {
			enterStatus = true;
		}
		return enterStatus;
	}
/*	
	public synchronized void leaveTheater() {

		synchronized(Driver.leaveTheaterLock){
			
			//75% will watch again 
			
			Visitor.waitingQ++;
			//startWatching();
			
			Clock.currentCap--;
		}
	}*/
	
	public void run() {
		//when the thread is running it first trys to watch 
		startWatching();
		//leaveTheater();
	}
	
	
}
