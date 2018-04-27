package network;

public class NetworkPlayerThread extends Thread {

	private NetworkPlayer networkPlayer;
	
	private volatile boolean running = true;
	
	public NetworkPlayerThread(NetworkPlayer networkPlayer, String threadString){
		super(threadString);
		this.networkPlayer = networkPlayer;
	}
	
	public void run(){
		while(running){
			if(!NetworkPlayer.checkNetworkConnection(networkPlayer)) running = false;
			try {
				sleep(100L);
			} catch (InterruptedException e) {
				;
			}
		}
	}
	
	public void terminate(){
		running = false;
	}
	
}
