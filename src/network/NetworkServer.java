package network;

public class NetworkServer {

	public static void main(String[] args) {
		Server server = new Server();
		new Thread(server).run();
	}

}
