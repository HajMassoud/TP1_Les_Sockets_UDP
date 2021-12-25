import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UserRecieve extends Thread{
DatagramSocket socket;
public UserRecieve(DatagramSocket sc) {
	super();
	this.socket = sc;
}
public void run(){
	try{
		while(true){
		DatagramPacket packet=new DatagramPacket(new byte[1024], 1024);
		socket.receive(packet);
		String msg=new String (packet.getData(),0,packet.getData().length);
		String[] str=msg.trim().split("@@@");
		for (String string : str) {
		System.out.println(string );
		}
		}
	}catch(Exception e){
		System.out.println(e.toString());
	}
}

}
