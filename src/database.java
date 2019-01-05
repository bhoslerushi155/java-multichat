import java.util.*;
import java.sql.*;

public class database{



    public void writeDb(ArrayList<ArrayList<String>> messages,int sessionId){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/multichat","root","root");

            String query="INSERT INTO messages values(?,?,?);";
            for(ArrayList<String> msg:messages){
                PreparedStatement ps=con.prepareStatement(query);
                ps.setInt(1,sessionId);
                ps.setString(2,msg.get(0));
                ps.setString(3,msg.get(1));
                ps.execute();
            }
            messages.clear();
            con.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


}