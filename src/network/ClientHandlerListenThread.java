package network;

public class ClientHandlerListenThread extends Thread {

	private NetClientHandler nch;
	
	private volatile boolean running = true;
	
	public ClientHandlerListenThread(NetClientHandler nch, String threadString){
		super(threadString);
		this.nch = nch;
	}
	
	public void run(){
		while(running){
			NetClientHandler.checkNetworkConnection(nch);
			try {
				sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void terminate(){
		running = false;
	}
	
}
