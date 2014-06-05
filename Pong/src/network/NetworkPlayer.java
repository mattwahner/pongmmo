package network;

public class NetworkPlayer {
	
	private TcpConnection tc;
	private NetServerHandler nsh;
	private NetworkPlayerThread npt;
	
	private static int playerNum = 0;
	
	private String username = "Anonymous";
	private String team = "None";
	private boolean isConnected;
	private long connectionTime;
	
	public NetworkPlayer(TcpConnection tc){
		this.tc = tc;
		nsh = new NetServerHandler(tc);
		isConnected = true;
		connectionTime = System.currentTimeMillis();
		npt = new NetworkPlayerThread(this, playerNum + " player thread");
		playerNum++;
		npt.start();
	}
	
	public void shutdown(){
		tc.shutdownConnection();
		npt.terminate();
	}
	
	public boolean checkConnection(){
		tc.addToSendQue(new Packet02TestConnection());
		if(tc.getOutstandingPackets(2).size() > 0) connectionTime = System.currentTimeMillis();
		if(connectionTime + 1000 < System.currentTimeMillis()) isConnected = false;
		return isConnected;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setTeam(String team){
		this.team = team;
	}
	
	public String getTeam(){
		return team;
	}
	
	public TcpConnection getConnection(){
		return tc;
	}
	
	public NetServerHandler getNetServerHandler(){
		return nsh;
	}
	
	public boolean getIsConnected(){
		return isConnected;
	}
	
	static boolean checkNetworkConnection(NetworkPlayer networkPlayer){
		return networkPlayer.checkConnection();
	}
	
}
