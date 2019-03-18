import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class handleCommunication extends Thread{
	DataInputStream din=null;
	DataOutputStream dout=null;
	handleCommunication[] threads=null;
	String name=null;
	int sessionId;
	int max=10;
	public handleCommunication(Socket cs , handleCommunication[] threads,int sessionId) throws IOException {
		din=new DataInputStream(cs.getInputStream());
		dout=new DataOutputStream(cs.getOutputStream());
		this.threads=threads;
		this.sessionId=sessionId;
	}
	public void run() {
		int i;
		try {
			database db=new database();
			String in;
			String input="";
			dout.writeUTF("New user (y/n)?");
			in=din.readUTF();
			String password;
			if(in.equals("y") || in.equals("Y") || in.equals("yes")){
				dout.writeUTF("Enter name");
				dout.flush();
				name=din.readUTF();
				dout.writeUTF("Enter password");
				dout.flush();
				password=din.readUTF();
				db.addUser(name, password);
			}
			else{
				dout.writeUTF("enter name");
				dout.flush();
				name=din.readUTF();
				dout.writeUTF("Enter password");
				dout.flush();
				password=din.readUTF();
				while(!db.authUser(name,password)){
					dout.writeUTF("wrong credentials");
					dout.writeUTF("enter name");
					dout.flush();
					name=din.readUTF();
					dout.writeUTF("Enter password");
					dout.flush();
					password=din.readUTF();
				}
			}
			
			for(i=0;i<max;i++) {
				if(threads[i]!=this && threads[i]!=null) {
					threads[i].dout.writeUTF("new user : "+name +" connected");
					threads[i].dout.flush();
				}
			}
			
			while(!input.equals("quit")) {
				input=din.readUTF();

				ArrayList<String>message=new ArrayList<String>();
				message.add(name);
				message.add(input);
				db.writeDb(message,sessionId);
				for( i=0;i<max;i++) {
					if(threads[i]!=this  && threads[i]!=null) {
						threads[i].dout.writeUTF(name + " : " + input);
						threads[i].dout.flush();
					}
				}
			}
			
			for( i=0;i<max;i++) {
				if(threads[i]!=this && threads[i]!=null) {
					threads[i].dout.writeUTF("user : "+name+" left");
					threads[i].dout.flush();
				}
				if(threads[i]==this) {
					threads[i].dout.writeUTF("bye.....");
					threads[i].dout.flush();
					threads[i]=null;
				}
			}

			dout.close();
			din.close();
			
		}catch(Exception e) {
			
		}	
	}
}
