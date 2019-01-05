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
	ArrayList<ArrayList<String>> messages=new ArrayList<ArrayList<String>>();
	int sessionId;
	int max=10;
	public handleCommunication(Socket cs , handleCommunication[] threads,ArrayList<ArrayList<String>> messages,int sessionId) throws IOException {
		din=new DataInputStream(cs.getInputStream());
		dout=new DataOutputStream(cs.getOutputStream());
		this.threads=threads;
		this.messages=messages;
		this.sessionId=sessionId;
	}
	public void run() {
		int i;
		try {
			String input="";
			dout.writeUTF("enter name");
			dout.flush();
			name=din.readUTF();
			
			for(i=0;i<max;i++) {
				if(threads[i]!=this && threads[i]!=null) {
					threads[i].dout.writeUTF("new user : "+name +" connected");
					threads[i].dout.flush();
				}
			}
			
			while(!input.equals("quit")) {
				input=din.readUTF();

				ArrayList<String>temp=new ArrayList<String>();
				temp.add(name);
				temp.add(input);
				messages.add(temp);
				for( i=0;i<max;i++) {
					if(threads[i]!=this  && threads[i]!=null) {
						threads[i].dout.writeUTF(name + " : " + input);
						threads[i].dout.flush();
					}
				}
			}
			int count=0;
			for(i=0;i<max;i++){
				if(threads[i]!=null){
					count++;
				}
			}
			System.out.println(count);
			if(count==1){
				database db=new database();
				db.writeDb(messages,sessionId);
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
