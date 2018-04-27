package network;

public class ServerListenThread extends Thread {

	private PongServer pongServer;
	
	private volatile boolean running = true;
	
	public ServerListenThread(PongServer pongServer, String threadString){
		super(threadString);
		this.pongServer = pongServer;
	}
	
	public void run(){
		while(running){
			try {
				sleep(1000L);
				PongServer.acceptNetworkConnection(pongServer);
				PongServer.checkNetworkConnections(pongServer);
			} catch (InterruptedException e) {
				;
			}
		}
	}
	
	public void terminate(){
		running = false;
	}
	
}
