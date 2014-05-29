package network;

public class NetworkPlayer {
	
	private TcpConnection tc;
	private NetServerHandler nsh;
	
	public NetworkPlayer(TcpConnection tc){
		this.tc = tc;
		nsh = new NetServerHandler(tc);
	}
	
	public TcpConnection getConnection(){
		return tc;
	}
	
	public NetServerHandler getNetServerHandler(){
		return nsh;
	}
	
}
