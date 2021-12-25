import java.net.InetAddress;

public class User {
private String pseudo;
private InetAddress address;
private int port;
private String status;

public User() {
	super();
}
public User(String pseudo, InetAddress address, int port, String status) {
	super();
	this.pseudo = pseudo;
	this.address = address;
	this.port = port;
	this.status = status;
}
public String getPseudo() {
	return pseudo;
}
public void setPseudo(String pseudo) {
	this.pseudo = pseudo;
}
public InetAddress getAddress() {
	return address;
}
public void setAddress(InetAddress address) {
	this.address = address;
}
public int getPort() {
	return port;
}
public void setPort(int port) {
	this.port = port;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
