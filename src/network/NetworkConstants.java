package network;

public class NetworkConstants {
	public static final int contentSize = 100;
	public static final int BUF_SIZE	= contentSize * 2 + 12; // 2 * contentSize * sizeof(char) + 3 * sizeof(int)
	public static final String IP		= "127.0.0.1";
	public static final int PORT		= 30000;
	
	public static final int LOGIN_MESSAGE = 1;
	public static final int LOGOUT_MESSAGE = 2;
	public static final int AUCTION_CHANGE_REQUEST = 3;
	public static final int OFFER_LAUNCHED = 4;
	public static final int OFFER_DROPPED = 5;
	public static final int START_TRANSFER = 6;
	public static final int FILE_CONTENT = 7;
}
