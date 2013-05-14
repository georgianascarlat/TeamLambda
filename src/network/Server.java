package network;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
	
	private final int BUF_SIZE	= NetworkConstants.BUF_SIZE;
	private final String IP		= NetworkConstants.IP;
	private final int PORT		= NetworkConstants.PORT;
	private boolean running = true;
	private Selector selector = null;
	private Packet data = null;
	private Packet fileData = null;
	private HashMap<String, SelectionKey> users = new HashMap<String, SelectionKey>();
	
	public void processMessage(Packet p, SelectionKey key) {
		if (p.getType() == NetworkConstants.LOGIN_MESSAGE) {
			for (String user : users.keySet()) {
				SelectionKey currentUserKey = users.get(user);
				currentUserKey.interestOps(SelectionKey.OP_WRITE);
			}
			String[] content = p.getContent().split(" ");
			users.put(content[0], key);
			System.out.println("User " + content[0] + " added");
			selector.wakeup();
			return;
		}
		
		if (p.getType() == NetworkConstants.LOGOUT_MESSAGE) {
			String[] content = p.getContent().split(" ");
			users.remove(content[0]);
			for (String user : users.keySet()) {
				SelectionKey currentUserKey = users.get(user);
				currentUserKey.interestOps(SelectionKey.OP_WRITE);
			}
			System.out.println("User " + content[0] + " removed");
			selector.wakeup();
			return;
		}
		
		if (p.getType() == NetworkConstants.AUCTION_CHANGE_REQUEST) {
			String[] content = p.getContent().split(" ");
			SelectionKey currentUserKey = users.get(content[0]);
			currentUserKey.interestOps(SelectionKey.OP_WRITE);
			System.out.println("User " + content[0] + " informed of change request");
			selector.wakeup();
			return;
		}
		
		if (p.getType() == NetworkConstants.OFFER_LAUNCHED) {
			String[] content = p.getContent().split(" ");
			for (String user : users.keySet())
				if (!user.equals(content[0])) {
					SelectionKey currentUserKey = users.get(user);
					currentUserKey.interestOps(SelectionKey.OP_WRITE);
				}
			System.out.println("User " + content[0] + " launched offer");
			selector.wakeup();
			return;
		}
		
		if (p.getType() == NetworkConstants.OFFER_DROPPED) {
			String[] content = p.getContent().split(" ");
			for (String user : users.keySet())
				if (!user.equals(content[0])) {
					SelectionKey currentUserKey = users.get(user);
					currentUserKey.interestOps(SelectionKey.OP_WRITE);
				}
			System.out.println("User " + content[0] + " dropped offer");
			selector.wakeup();
			return;
		}
		
		if (p.getType() == NetworkConstants.START_TRANSFER) {
			String[] content = p.getContent().split(" ");
			fileData = new Packet(p.getContent(), p.getType(), p.getIsBuyer());
			Packet aux = data;
			data = fileData;
			fileData = aux;
			fileData.setType(NetworkConstants.FILE_CONTENT);
			for (String user : users.keySet())
				if (!user.equals(content[0])) {
					SelectionKey currentUserKey = users.get(user);
					currentUserKey.interestOps(SelectionKey.OP_WRITE);
				}
			System.out.println("User " + content[0] + " notified of file");
			selector.wakeup();
			return;
		}		

	}
	
	public void accept(SelectionKey key) throws IOException {
		
		System.out.print("ACCEPT: ");
		
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		ByteBuffer buf = ByteBuffer.allocateDirect(BUF_SIZE);
		socketChannel.register(key.selector(), SelectionKey.OP_READ, buf);
		
		System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
	}
	
	public void read(SelectionKey original, SelectionKey key) throws IOException {
		
		System.out.println("READ: ");
		
		int bytes = 0;
		boolean finalMessage = false;
		ByteBuffer buf = (ByteBuffer)key.attachment();		
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ArrayList<Message> messages = new ArrayList<Message>();
		
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
					finalMessage = m.isFinal();
					buf.clear();
				}
			}
			data = new Packet(messages);
			processMessage(data, key);
		} catch (IOException e) {
			System.out.println("Connection closed: " + e.getMessage());
			for (String username : users.keySet()) {
				if (users.get(username).equals(key)) {
					users.remove(username);
					break;
				}					
			}
			socketChannel.close();
			
		}
	}
	
	public void write(SelectionKey key) throws IOException {
		System.out.println("WRITE: ");
		
		if (data == null) {
			key.interestOps(SelectionKey.OP_READ);
			return;
		}	
		
		ByteBuffer buf = (ByteBuffer)key.attachment();		
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ArrayList<Message> messages = data.getMessages();
		
		try {
			for (Message m : messages) {
				m.copyIntoBuffer(buf);
				while (socketChannel.write(buf) > 0);
				if (!buf.hasRemaining()) {
					buf.clear();
				}
			}
			
			if (fileData != null) {
				data = fileData;
				fileData = null;
				selector.wakeup();
				return;
			}
			key.interestOps(SelectionKey.OP_READ);
		} catch (IOException e) {
			System.out.println("Connection closed: " + e.getMessage());
			for (String username : users.keySet()) {
				if (users.get(username).equals(key)) {
					users.remove(username);
					break;
				}					
			}
			socketChannel.close();
		}
		
	}
	
	public void terminate() {
		this.running = false;
	}
	

	@Override
	public void run() {
		ServerSocketChannel serverSocketChannel	= null;
		SelectionKey original = null;
		
		try {
			selector = Selector.open();
			
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(IP, PORT));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			original = selector.keys().iterator().next();
			while (running) {
				selector.select();
				
				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
					SelectionKey key = it.next();
					it.remove();
					
					if (key.isAcceptable())
						accept(key);
					else if (key.isReadable())
						read(original, key);
					else if (key.isWritable())
						write(key);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			if (selector != null)
				try {
					selector.close();
				} catch (IOException e) {}
			
			if (serverSocketChannel != null)
				try {
					serverSocketChannel.close();
				} catch (IOException e) {}
		}

	}

}
