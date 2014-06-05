package network;

public class ClientHandlerPacketThread extends Thread {

	private volatile boolean running = true;
	
	private NetClientHandler nch;
	
	public ClientHandlerPacketThread(NetClientHandler nch, String threadString){
		super(threadString);
		this.nch = nch;
	}
	
	public void run(){
		while(running){
			NetClientHandler.processNetPackets(nch);
			try {
				sleep(2L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void terminate(){
		running = false;
	}
	
}
