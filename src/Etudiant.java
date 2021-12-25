import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Etudiant {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Creation d'une socket
			DatagramSocket socket = new DatagramSocket();
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String msg = "";
			do {
				msg = scanner.nextLine();
			} while (!msg.startsWith("##"));
			InetAddress address = InetAddress.getLocalHost();
			int port = 2500;
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), address, port);
			socket.send(packet);
			String pseudo = msg.substring(2);
			UserSend send = new UserSend(socket, pseudo);
			UserRecieve recieve = new UserRecieve(socket);

			send.start();
			recieve.start();
		} catch (Exception e) {

		}

	}
}
