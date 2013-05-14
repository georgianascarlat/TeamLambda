package network;
import java.nio.ByteBuffer;


public class Message {
	private String content;
	private int contentLen;
	private int messageType;
	private int isBuyer;
	private ByteBuffer buffer;
	private int count;
	
	public Message(int messageType, int isBuyer, int bufferSize, String content) {
		this.messageType = messageType;
		this.content = content;
		this.contentLen = content.length();
		this.isBuyer = isBuyer;
		this.buffer = ByteBuffer.allocateDirect(bufferSize);
		fillBuffer(bufferSize);
	}
	
	public Message(ByteBuffer buffer) {
		this.buffer = buffer;
		this.messageType = buffer.getInt();
		this.isBuyer = buffer.getInt();
		this.contentLen = buffer.getInt();
		this.content = "";
		for (int i = 0; i < contentLen; i++) {
			this.content += buffer.getChar();
		}
		this.buffer.rewind();
	}
	
	private void fillBuffer(int bufferSize) {
		this.buffer = ByteBuffer.allocateDirect(bufferSize);
		this.buffer.putInt(messageType);
		this.buffer.putInt(isBuyer);		
		this.buffer.putInt(contentLen);
		for (int index = 0; index < contentLen; index++) {
			this.buffer.putChar(content.charAt(index));
		}
		this.buffer.rewind();
	}

	public void copyIntoBuffer(ByteBuffer destination) {
		destination.clear();
		for (int i = 0; i < destination.capacity(); i++) {
			destination.put(this.buffer.get());
		}
		destination.rewind();
		this.buffer.rewind();
	}
	
	public void setFinal() {
		messageType = -1;
	}
	
	public void setCount(int val) {
		this.count = val;
	}
	
	public void setType(int val) {
		this.messageType = val;
	}
	
	public boolean isFinal() {
		return (messageType < 0);
	}
	
	public boolean isBuyer() {
		return (isBuyer == 1);
	}
	
	public String getContent() {
		return content;
	}
	
	public int getType() {
		return messageType;
	}
	
	public int getCount() {
		return count;
	}

	public String toString() {
		return "" + messageType + isBuyer + content; 
	}
	
}
