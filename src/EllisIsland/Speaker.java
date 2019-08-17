package EllisIsland;
public class Speaker extends Thread {
	
	public Speaker() {
		
	}
	
	public static long time = System.currentTimeMillis(); 
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
	public synchronized static void aTalk() {
		System.out.println("This is my random speech");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized(Driver.speakerDoneTheaterLock) {
			Driver.speakerDoneTheaterLock.notify();
		}
		//System.out.println("The Speech is over and he gave up the lock");
	}
	

	public static synchronized void yallFreeToGo () {
		
		
		synchronized(Driver.inTheaterLock) {
			Driver.inTheaterLock.notifyAll();
		}
		
	}
	
	public void run(){
		while(Clock.moviesLeft > 0 ) {
			synchronized(Driver.speakerTheaterLock){
				try {
				Driver.speakerTheaterLock.wait();
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				aTalk();
				
				yallFreeToGo();
				
				System.out.println("Everyone in the theater was released");
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
