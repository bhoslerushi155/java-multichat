<%-- 
    Document   : display
    Created on : 18 Mar, 2019, 12:08:34 AM
    Author     : virus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<a href="index.html">go to home</a><br>

<%
        String name = String.valueOf(request.getParameter("usr_name"));
        String pss = String.valueOf(request.getParameter("pass"));
         Connection con=null;
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/multichat" , "root" , "root");
            
        PreparedStatement ps =con.prepareStatement("SELECT * FROM messages WHERE sender=? AND EXISTS(SELECT * FROM credentials WHERE user=? AND pass=?);");
        ps.setString(1, name);
        ps.setString(2,name);
        ps.setString(3,pss);
        ResultSet rs =ps.executeQuery();
        %>
        <table border="1">
            <tr>
                <th>Session ID</th>
                <th>Name</th>
                <th>Message</th>
            </tr>
         <%
        while(rs.next()){
            int sId=rs.getInt(1);
            String nme=rs.getString(2);
            String msg=rs.getString(3);

           %>
             <tr>
               <td><%=sId%></td>
               <td><%=nme %></td>
               <td><%=msg %></td>  
           </tr> 
           <%
            
            
        }
         %>
         </table>
         <%
    }catch(Exception e){
        
    }finally{
        con.close();
    }

    %>
   
    
