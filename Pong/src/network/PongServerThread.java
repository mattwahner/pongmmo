package network;

public class PongServerThread extends Thread {

	private PongServer pongServer;
	
	public PongServerThread(PongServer pongServer, String threadString){
		super(threadString);
		this.pongServer = pongServer;
	}
	
	public void run(){
		try {
			sleep(5000L);
			PongServer.acceptNetworkConnection(pongServer);
			PongServer.checkNetworkConnections(pongServer);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
