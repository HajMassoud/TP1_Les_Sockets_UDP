import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UserSend extends Thread {
	private DatagramSocket sc ;
	private String pseudo;
	public UserSend(DatagramSocket sc, String pseudo) {
		super();
		this.sc = sc;
		this.setPseudo(pseudo);
	}
	
	public void run(){
		try {
		InetAddress adr=InetAddress.getLocalHost();
		int port=2500;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
			while(true) {
                String msg = scanner.nextLine();
				DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(),adr,port);
				sc.send(packet);  
				
			}
			
	} catch (Exception e) {
			// TODO Auto-generated catch block
System.out.println(e.toString());		}
		
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}
