package network;

public class TcpReadThread extends Thread {

	private TcpConnection tc;
	private volatile boolean running = true;
	
	public TcpReadThread(TcpConnection tc, String threadString){
		super(threadString);
		this.tc = tc;
	}
	
	public void run(){
		while(running){
			if(!TcpConnection.readNetworkPacket(tc)){
				try {
					sleep(2L);
				} catch (InterruptedException e) {
					;
				}
			}
		}
	}
	
	public void terminate(){
		running = false;
	}
	
}
