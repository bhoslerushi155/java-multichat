import java.util.*;
import java.sql.*;

public class database{



    public void writeDb(ArrayList<String> message,int sessionId){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/multichat","root","root");

            String query="INSERT INTO messages values(?,?,?);";
            
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,sessionId);
            ps.setString(2,message.get(0));
            ps.setString(3,message.get(1));
            ps.execute();
         
            con.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean authUser(String name ,String pass){
    	try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/multichat","root","root");

            String query="SELECT * FROM credentials WHERE user=?;";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
            	String pss=rs.getString("pass");
            	if(pass.equals(pss)){
            		return Boolean.TRUE;
            	}
            }
            
            con.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    	return Boolean.FALSE;
    }

    public void addUser(String name,String pass){
    	try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/multichat","root","root");

            String query="INSERT INTO credentials values(?,?);";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, pass);
            ps.execute();
            
            con.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}