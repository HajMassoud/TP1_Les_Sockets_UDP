import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

public class Server {
		static Vector<User> onlinePeople=new Vector<User>();
		static Vector<Groupe> groupes=new Vector<Groupe>();
		static Vector<Message> messages=new Vector<Message>();
	    static String msgToSend="";
	    static String msgToRecieve="";
	    static DatagramSocket socket;
	    static DatagramPacket packet;
	    
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			 System.out.println("server started !");

     socket = new DatagramSocket(2500);

	 while(true){
		  packet = new DatagramPacket(new byte[1024], 1024);

			socket.receive(packet);
			 msgToRecieve = new String(packet.getData(),0,packet.getLength());
			 System.out.println(msgToRecieve);
            if(msgToRecieve.startsWith("##"))
            	connect();
            else if(msgToRecieve.equals("#LIST")) 
            	getOnlinePeople();
            else if(msgToRecieve.startsWith("#STATUS#")) 
            	updateStatus();
            else if(msgToRecieve.startsWith("@#")) 
            	sendMessage();
            else if(msgToRecieve.equals("#HISTO")) 
            	getHistory();
            else if(msgToRecieve.equals("#groupeS")) 
            	getgroupes();
            else if(msgToRecieve.startsWith("#groupe#")) 
            	creategroupe();
            else if(msgToRecieve.startsWith("#>")) 
            	joingroupe();
            else if(msgToRecieve.startsWith("#USERS#")) 
            	getMembers();
            else if(msgToRecieve.startsWith("@>")) 
            	sendgroupe();
	 }
    
      
}catch(Exception e){
	System.out.println(e.toString());
}
	}


	
	
	
	

	private static void sendgroupe() {
		// TODO Auto-generated method stub
		try {
			String[] ms=new String(packet.getData(),0,packet.getData().length).trim().split("@>");
			String groupe=ms[1];
			String message=ms[2];
			if(!isMember(groupe)) {
				msgToSend="You must join the club first";
				replay();
				return;
			}
			User user1=getUser();
				msgToSend="("+groupe+")"+user1.getPseudo()+": "+message;
				for (Groupe s : groupes) {
					if(s.getNom().equals(groupe)) {
						for (User user : s.getMembers()) {
							replay(user.getAddress(),user.getPort());
						}
						messages.add(new Message(user1.getPseudo(),"("+groupe+")",message));

					}
				}

			
			}
			catch (Exception e) {
				// TODO: handle exception
			}
	}



	private static void getMembers() {
		// TODO Auto-generated method stub
		String ms=new String(packet.getData(),0,packet.getData().length).trim();
		String groupe=ms.substring(7);
		msgToSend=" ";
		for (Groupe s : groupes) {
			if(s.getNom().equals(groupe)) {
				for (User user : s.getMembers()) {
					msgToSend+=user.getPseudo()+"@@@";
				}

			}
			break;
		}
		replay();
	}



	private static void joingroupe() {
		// TODO Auto-generated method stub
		String ms=new String(packet.getData(),0,packet.getData().length).trim();
		String groupe=ms.substring(2);
		for (Groupe s : groupes) {
			if(s.getNom().equals(groupe)) {
				s.getMembers().add(getUser());
				msgToSend="("+groupe+"): "+getUser().getPseudo()+" has joined us";
				for (User user : s.getMembers()) {
					replay(user.getAddress(),user.getPort());
				}

			}
		}
	}



	private static void creategroupe() {
		// TODO Auto-generated method stub
		String ms=new String(packet.getData(),0,packet.getData().length).trim();
		String groupe=ms.substring(7);
        groupes.add(new Groupe(groupe));
		msgToSend=groupe+" is created ";
		replay();
	}



	private static void getgroupes() {
		// TODO Auto-generated method stub
		msgToSend="";
			for (Groupe groupe : groupes) {
				msgToSend+=groupe.getNom()+"@@@";
			}
			replay();
	}



	private static void getHistory() {
		// TODO Auto-generated method stub
		msgToSend="";
		for (Message message : messages) {
			if((message.getReciever().equals(getUser().getPseudo()))||(message.getSender().equals(getUser().getPseudo()))   ) {
				msgToSend+=message.getSender()+" -> "+message.getReciever()+": "+message.getMsg()+"@@@";
			}
		}
		replay();
	}



	private static void sendMessage() {
		// TODO Auto-generated method stub
		try {
		String[] ms=new String(packet.getData(),0,packet.getData().length).trim().split("@#");
		String reciever=ms[1];
		String message=ms[2];
		if ((existPseudo(reciever)!=null)&&(!getUser().getPseudo().equals(reciever))) {
			msgToSend=getUser().getPseudo()+": "+message;
			System.out.println(msgToSend);
			User rec=existPseudo(reciever);
			replay(rec.getAddress(),rec.getPort());
			messages.add(new Message(getUser().getPseudo(),reciever,message ));
			msgToSend="Message sent";
			replay();

		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}



	private static User existPseudo(String string) {
		// TODO Auto-generated method stub
		for (User user : onlinePeople) {
			if(user.getPseudo().equals(string))
				return user;
			
		}
		return null;
	}



	private static void updateStatus() {
		// TODO Auto-generated method stub
		String ms=new String(packet.getData(),0,packet.getData().length).trim();
		String status=ms.substring(8);
	//	check();
		if (getUser()!=null) {
          getUser().setStatus(status);
		}
		msgToSend="Status updated to "+status;
		replay();
	}



	public static void connect() {
		// TODO Auto-generated method stub
		
		String ms=new String(packet.getData(),0,packet.getData().length);
		String pseudo=ms.substring(2,ms.length());
		User user=new User(pseudo.trim(),packet.getAddress(),packet.getPort(),"Online");
		if (getUser()==null) {
			if(existPseudo(pseudo.trim())==null) {
				onlinePeople.add(user);
		    	msgToSend=pseudo.trim()+" added with success !";
		}
	        else
    	       msgToSend="This pseudo already exists !";


		}
		else
	    	msgToSend="You are already connected !";

		replay();
		
	}

	public static void replay() {
		try {
			byte[] bt= msgToSend.getBytes();
			DatagramPacket packet2 = new DatagramPacket(bt, bt.length,packet.getAddress(),packet.getPort());
			socket.send(packet2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void replay(InetAddress address,int port) {
		// TODO Auto-generated method stub
		try {
			byte[] bt= msgToSend.getBytes();
			DatagramPacket packet2 = new DatagramPacket(bt, bt.length,address,port);
			socket.send(packet2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void getOnlinePeople() {
		// TODO Auto-generated method stub
		msgToSend="";
	//	check();
		for (User user : onlinePeople) {
			msgToSend+=user.getPseudo()+"("+user.getStatus()+")"+"@@@";
		}
		replay();
	}
	public static User getUser() {
		// TODO Auto-generated method stub
         for (User user : onlinePeople) {
			if((user.getAddress().equals(packet.getAddress()))&&(user.getPort()==packet.getPort()))	
               return user;			    
         }
		return null;
		}

	public static boolean isMember(String S) {
		boolean verif=false;
		for(Groupe groupe:groupes) {
			if(groupe.getNom().equals(S)){
				for(User user:groupe.getMembers()) {
					if(user.getPseudo().equals(getUser().getPseudo())) {
						verif=true;
						break;
					}
				}
				break;
			}
		}
		return verif;
	}
}