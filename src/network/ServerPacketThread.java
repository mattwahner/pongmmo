package network;

public class ServerPacketThread extends Thread {

	private volatile boolean running = true;
	
	private PongServer server;
	
	public ServerPacketThread(PongServer server, String threadString){
		super(threadString);
		this.server = server;
	}
	
	public void run(){
		while(running){
			PongServer.processNetworkPackets(server);
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
