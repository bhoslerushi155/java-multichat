import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class server {
	
	public static void main(String[] args) throws IOException {
		ServerSocket ss=new ServerSocket(7000);
		System.out.println("server started ...");
		System.out.println("Enter session ID:");
		Scanner sc=new Scanner(System.in);
		int sessionId=sc.nextInt();
		Socket cs=null;
		int max=10;
		handleCommunication threads[]=new handleCommunication[10];
		int i;
        ArrayList<ArrayList<String>> messages=new ArrayList<ArrayList<String>>();
        ArrayList<String> temp=new ArrayList<String>();
		while(true) {
			cs=ss.accept();
			for( i=0;i<max;i++) {
				if(threads[i]==null) {
					threads[i]=new handleCommunication(cs , threads,messages,sessionId);
					threads[i].start();
					break;
				}
			}
			if(i==max) {
				DataOutputStream dout=new DataOutputStream(cs.getOutputStream());
				dout.writeUTF("server too busy ....");
				cs.close();
			}
		}
	}

}
