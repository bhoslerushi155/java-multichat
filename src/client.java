import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class client implements Runnable{
	static Socket cs=null;
	static DataInputStream din=null;
	static DataOutputStream dout=null;
	static BufferedReader br=null;
	
	
	public static void main(String[] args) {
		
		try {
			String input="";
			cs=new Socket("Localhost" , 7000);
			din=new DataInputStream(cs.getInputStream());
			dout=new DataOutputStream(cs.getOutputStream());
			br=new BufferedReader(new InputStreamReader(System.in));
			
			(new Thread(new client())).start();
			
			while(!input.equals("quit")) {
				input=br.readLine();
				dout.writeUTF(input);
				dout.flush();
			}
			
			dout.close();
			din.close();
			br.close();
			cs.close();
			return;
		} catch (IOException e) {	
			
		}		
	}
	
	public void run() {
		String reply="";
		try {
			while((reply=din.readUTF()) !=null) {
				System.out.println(reply);
			}
		} catch (IOException e) {
			
		}
	}
}
