package network;

public class ServerPacketThread extends Thread {

	private volatile boolean running = true;
	
	private PongServer pongServer;
	
	public ServerPacketThread(PongServer pongServer, String threadString){
		super(threadString);
		this.pongServer = pongServer;
	}
	
	public void run(){
		while(running){
			PongServer.redirectNetworkPackets(pongServer);
			try {
				sleep(2L);
			} catch (InterruptedException e) {
				;
			}
		}
	}
	
	public void terminate(){
		running = false;
	}
	
}
