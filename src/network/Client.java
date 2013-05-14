package network;

import mediator.MediatorNetwork;
import models.Auction;
import models.StatusTypes;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Client implements Runnable {
	
	private static final int BUF_SIZE = NetworkConstants.BUF_SIZE;
	private boolean running = true;
	private boolean initialWrite = false;
	private SelectionKey serverKey = null;
	private Selector selector = null;
	private Packet dataToSend = null;
	private Packet dataReceived = null;
	private String username;
	private int isBuyer;
	private int sendProgress;
	private int recvProgress;
	private List<String> services;
	private MediatorNetwork mediator;
	
	public Client(String username, String type, List<String> services, MediatorNetwork mediator) {
		this.username = username;
		this.mediator = mediator;
		if (type.equals("buyer"))
			isBuyer = 1;
		else
			isBuyer = 0;
		this.services = services;
		String content = username + " " + type;
		for (String service : services)
			content = content + " " + service;
		this.dataToSend = new Packet(content, NetworkConstants.LOGIN_MESSAGE, isBuyer);
	}
	
	public void processMessage(Packet p, SelectionKey key) {
		if (p.getType() == NetworkConstants.LOGIN_MESSAGE) {
			String[] content = p.getContent().split(" ");
			String otherUserName = content[0];
			String otherUserType = content[1];
			ArrayList<String> otherUserServices = new ArrayList<String>();
			for (int i = 2; i < content.length; i++)
				otherUserServices.add(content[i]);
			mediator.userLoggedIn(otherUserName, otherUserType, otherUserServices);
		}
		
		if (p.getType() == NetworkConstants.LOGOUT_MESSAGE) {
			String[] content = p.getContent().split(" ");
			String otherUserName = content[0];
			String otherUserType = content[1];
			mediator.userLoggedOut(otherUserName, otherUserType);
		}
		
		if (p.getType() == NetworkConstants.AUCTION_CHANGE_REQUEST) {
			String[] content = p.getContent().split(" ");
			String sourceUser = content[1];
			String service = content[2];
			StatusTypes status = StatusTypes.valueOf(content[3]);
			float price = Float.parseFloat(content[4]);
			mediator.auctionStatusChangeInform(service, new Auction(sourceUser, status, price));
		}
		
		if (p.getType() == NetworkConstants.OFFER_LAUNCHED) {
			String[] content = p.getContent().split(" ");
			String otherUserName = content[0];
			String otherUserService = content[1];
			
			if (services.contains(otherUserService)) {
				Auction a = new Auction(otherUserName);
				a.setStatus(StatusTypes.No_Offer);
				mediator.auctionStatusChangeInform(otherUserService, a);
			}
		}
		
		if (p.getType() == NetworkConstants.OFFER_DROPPED) {
			String[] content = p.getContent().split(" ");
			String otherUserName = content[0];
			String otherUserService = content[1];
			
			if (services.contains(otherUserService)) {
				Auction a = new Auction(otherUserName);
				a.setStatus(StatusTypes.Inactive);
				mediator.auctionStatusChangeInform(otherUserService, a);
			}
		}
		
		if (p.getType() == NetworkConstants.START_TRANSFER) {
			String[] content = p.getContent().split(" ");
			String sourceUser = content[1];
			String service = content[2];
			float price = Float.parseFloat(content[4]);
			mediator.auctionStatusChangeInform(service, new Auction(sourceUser, StatusTypes.Transfer_In_Progress, price));
		}
		
		if (p.getType() == NetworkConstants.FILE_CONTENT) {
			String[] content = p.getContent().split(" ");
			String sourceUser = content[1];
			String service = content[2];
			float price = Float.parseFloat(content[4]);
			p.writeContentToFile(service);
			mediator.auctionStatusChangeInform(service, new Auction(sourceUser, StatusTypes.Transfer_Completed, price));
		}

		
	}
	
	public synchronized void connect(SelectionKey key) throws IOException {
		System.out.print("CONNECT: ");
		serverKey = key;
		SocketChannel socketChannel = (SocketChannel)key.channel();
		if (! socketChannel.finishConnect()) {
			System.err.println("Eroare finishConnect");
			running = false;
		}
		key.interestOps(SelectionKey.OP_WRITE);
		notifyAll();
	}
	
	public synchronized void send(String content, String fileName, int type) throws InterruptedException {
		if (serverKey == null)
			wait();
		if (!initialWrite)
			wait();
		Packet p;
		if (fileName != null)
			p = new Packet(content, fileName, type, isBuyer);
		else
			p = new Packet(content, type, isBuyer);
		dataToSend = p;	
		serverKey.interestOps(SelectionKey.OP_WRITE);
		selector.wakeup();
	}	
	
	public synchronized void write(SelectionKey key) throws IOException {
		
		System.out.println("WRITE: ");
		
		ByteBuffer buf = (ByteBuffer)key.attachment();		
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ArrayList<Message> messages = dataToSend.getMessages();
		int numMessages = messages.size();
		int sent = 0;
		try {
			for (Message m : messages) {
				sent++;
				sendProgress = 100 * sent / numMessages;
				m.copyIntoBuffer(buf);
				while (socketChannel.write(buf) > 0);
				if (!buf.hasRemaining()) {
					buf.clear();
				}
			}
			sendProgress = 0;
			key.interestOps(SelectionKey.OP_READ);
			dataToSend = null;
			if (!initialWrite) {
				initialWrite = true;
				notifyAll();
			}
		} catch (IOException e) {
			System.out.println("Connection closed: " + e.getMessage());
			socketChannel.close();
		}
	}

	public void read(SelectionKey key) throws IOException {	
		
		System.out.println("READ: ");
		
		int bytes = 0;
		boolean finalMessage = false;
		ByteBuffer buf = (ByteBuffer)key.attachment();		
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ArrayList<Message> messages = new ArrayList<Message>();
		int received = 0;
		
		try {
			while (!finalMessage) {
				while ((bytes = socketChannel.read(buf)) > 0);
				if (bytes == -1)
					throw new IOException("EOF");
				if (!buf.hasRemaining()) {
					ByteBuffer recvBuf = buf.duplicate();
					recvBuf.rewind();
					Message m = new Message(recvBuf);
					messages.add(m);
					received++;
					recvProgress = 100 * received / m.getCount();
					finalMessage = m.isFinal();
					buf.clear();
				}
			}
			recvProgress = 0;
			dataReceived = new Packet(messages);
			processMessage(dataReceived, key);
		} catch (IOException e) {
			System.out.println("Connection closed: " + e.getMessage());
			socketChannel.close();
			
		}
		
	}
	
	public void terminate() {
		this.running = false;
	}
	
	public int getSendProgress() {
		return sendProgress;
	}
	
	public int getRecvProgress() {
		return recvProgress;
	}
	

	@Override
	public void run() {
		SocketChannel socketChannel	= null;
		
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress(NetworkConstants.IP, NetworkConstants.PORT));
			
			ByteBuffer buf = ByteBuffer.allocateDirect(BUF_SIZE);
			socketChannel.register(selector, SelectionKey.OP_CONNECT, buf);
			
			while (running) {
				selector.select();
				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
					SelectionKey key = it.next();
					it.remove();
					
					if (key.isConnectable())
						connect(key);
					else if (key.isWritable())
						write(key);
					else if (key.isReadable())
						read(key);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if (selector != null)
				try {
					selector.close();
				} catch (IOException e) {}
			
			if (socketChannel != null)
				try {
					socketChannel.close();
				} catch (IOException e) {}
		}		
	}

}
