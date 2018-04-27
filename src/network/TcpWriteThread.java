package network;

public class TcpWriteThread extends Thread {

	private TcpConnection tc;
	private volatile boolean running = true;
	
	public TcpWriteThread(TcpConnection tc, String threadString){
		super(threadString);
		this.tc = tc;
	}
	
	public void run(){
		while(running){
			if(!TcpConnection.sendNetworkPacket(tc)){
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
