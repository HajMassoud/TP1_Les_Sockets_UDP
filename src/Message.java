
public class Message {
private String sender;
private String reciever;
private String msg;
public Message(String sender, String reciever, String msg) {
	super();
	this.sender = sender;
	this.reciever = reciever;
	this.msg = msg;
}
public Message() {
	super();
}
public String getSender() {
	return sender;
}
public void setSender(String sender) {
	this.sender = sender;
}
public String getReciever() {
	return reciever;
}
public void setReciever(String reciever) {
	this.reciever = reciever;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
}
