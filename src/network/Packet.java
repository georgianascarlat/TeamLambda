package network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Packet {
	
	private ArrayList<Message> messages;
	private static final int contentSize = NetworkConstants.contentSize;
	private static final int bufferSize = NetworkConstants.BUF_SIZE;
	private int type;
	private int isBuyer;
	
	public Packet(String input, int type, int isBuyer) {
		messages = new ArrayList<Message>();
		this.type = type;
		this.isBuyer = isBuyer;
		int inputLen = input.length();
		int numMessages = inputLen / contentSize;
		
		for (int i = 0; i < numMessages; i++) {
			String currentChunk = input.substring(i * contentSize, i * contentSize + contentSize);
			Message currentMessage = new Message(type, isBuyer, bufferSize, currentChunk);
			currentMessage.setCount(numMessages+1);
			messages.add(currentMessage);
		}
		
		if (inputLen % contentSize > 0) {
			String lastChunk = input.substring(numMessages * contentSize);
			Message lastMessage = new Message(-type, isBuyer, bufferSize, lastChunk);
			messages.add(lastMessage);			
		}	
	}	
	
	public Packet(String input, String filename, int type, int isBuyer) {
		messages = new ArrayList<Message>();
		this.type = type;
		this.isBuyer = isBuyer;
		int inputLen = input.length();
		int numMessages = inputLen / contentSize;
		File file = new File(filename);
		
		for (int i = 0; i < numMessages; i++) {
			String currentChunk = input.substring(i * contentSize, i * contentSize + contentSize);
			Message currentMessage = new Message(type, isBuyer, bufferSize, currentChunk);
			currentMessage.setCount(numMessages+1);
			messages.add(currentMessage);
		}
		
		if (inputLen % contentSize > 0) {
			String lastChunk = input.substring(numMessages * contentSize);
			Message lastMessage = new Message(type, isBuyer, bufferSize, lastChunk);
			messages.add(lastMessage);			
		}	
		
		try {
			Scanner reader = new Scanner(file);
			String line;
			ArrayList<Message> localMessages;
			while (reader.hasNextLine()) {
				line = reader.nextLine();
				line += "\n";
				localMessages = convertToMessages(line, NetworkConstants.FILE_CONTENT, isBuyer);
				messages.addAll(localMessages);
			}
			
			messages.get(messages.size() - 1).setType(-type);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<Message> convertToMessages(String input, int type, int isBuyer) {
		ArrayList<Message> messages = new ArrayList<Message>();
		int inputLen = input.length();
		int numMessages = inputLen / contentSize;
		
		for (int i = 0; i < numMessages; i++) {
			String currentChunk = input.substring(i * contentSize, i * contentSize + contentSize);
			Message currentMessage = new Message(type, isBuyer, bufferSize, currentChunk);
			currentMessage.setCount(numMessages+1);
			messages.add(currentMessage);
		}
		
		if (inputLen % contentSize > 0) {
			String lastChunk = input.substring(numMessages * contentSize);
			Message lastMessage = new Message(type, isBuyer, bufferSize, lastChunk);
			messages.add(lastMessage);			
		}	
		
		return messages;
	}
	
	public Packet(ArrayList<Message> messages) {
		this.messages = messages;
		this.type = Math.abs(messages.get(0).getType());
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public String getContent() {
		String content = "";
		
		for (Message m : messages) {
			if (m.getType() != NetworkConstants.FILE_CONTENT)
				content += m.getContent();
		}
		
		return content;
	}
	
	public void writeContentToFile(String fileName) {
		try {
			File file = new File(fileName);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Message m : messages) {
				if (m.getType() == NetworkConstants.FILE_CONTENT)
					bw.write(m.getContent());
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public int getIsBuyer() {
		return isBuyer;
	}
}
