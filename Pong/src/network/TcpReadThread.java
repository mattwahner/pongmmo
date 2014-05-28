package network;

public class TcpReadThread extends Thread {

	private TcpConnection tc;
	
	public TcpReadThread(TcpConnection tc, String threadString){
		super(threadString);
		this.tc = tc;
	}
	
	public void run(){
		if(!TcpConnection.readNetworkPacket(tc)){
			try {
				sleep(2L);
			} catch (InterruptedException e) {
				;
			}
		}
	}
	
}
