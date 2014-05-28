package network;

public class TcpWriteThread extends Thread {

	private TcpConnection tc;
	
	public TcpWriteThread(TcpConnection tc, String threadString){
		super(threadString);
		this.tc = tc;
	}
	
	public void run(){
		while(true){
			if(!TcpConnection.sendNetworkPacket(tc)){
				try {
					sleep(2L);
				} catch (InterruptedException e) {
					;
				}
			}
		}
	}
	
}
