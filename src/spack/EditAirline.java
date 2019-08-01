package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditAirline
 */
@WebServlet("/EditAirline")
public class EditAirline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private int id = 0;
	
	private void setID(int value) {
		id = value;
	}
	
	private int getID() {
		return id;
	}
	
	 protected void initializeHtml(java.io.PrintWriter out , ResultSet rs) throws Exception {
	    	if(rs.next()) {
	    	String str = 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
		    			"<head>"+
		    			"<meta charset=\"UTF-8\">\n" + 
		    			"<title>Edit Delete Airline</title>\n" +
		    			"</head><body>" + 
			    			"<div>\n<form action=\"EditAirline\" method=\"post\">" + 
			    			"		<br> <br> <br> <br>\n" + 
			    			"		<p>Add airplane</p>\n" + 
			    			"		<table border=\"2\">\n" + 
			    			"			<tbody>\n" + 
			    			"				<tr>\n" + 
			    			"					<td>Airline Name</td>\n" +
			    			"				</tr>\n" + 
			    			"				<tr>\n" + 
			    			"					<td><input name=\"Name\" value = \""+rs.getString("Name")+"\"    /></td>\n" + 
			    			"				</tr>\n" + 
			    			"			</tbody>\n" + 
			    			"		</table>\n" + 
			    			"		\n" + 
			    			"		<input type=\"submit\" name = \"click\" value=\"Update\" />\n" +
			    			"<input type=\"submit\" name = \"click\" value=\"Delete\" />\n" +
			    			"	</form></div>\n" + 
			    			"<p>${error}</p>"+
		    			"</body>\n" + 
	    			"</html>";
	    	out.write(str);
	    	}
	    }
	    
	
    public EditAirline() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		int id = 0;
		String strid =   (String)request.getAttribute("_id");
		if(strid != null) {
			id = Integer.parseInt(strid);
		}
		if(id == 0) {
			
		}
		else {
			this.setID(id);
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");
					
				PreparedStatement stmt;		
				stmt=con.prepareStatement("Select Name from AirlineTable where AirlineID = ?");
				stmt.setInt(1, id);
				
				ResultSet rs = stmt.executeQuery();
				initializeHtml(response.getWriter(), rs);
				con.close();
			}
			catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
			}   
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("click").equals("Update")) {
			String name = "";
			
			String error = "";
			
			name = request.getParameter("Name");
			
			boolean isCorrect = true;
			
			if(name.length() == 0) {
				isCorrect = false;
				error = "Airline name cannot be empty. ";
			}
			
			if(isCorrect == false) {
				 request.setAttribute("error", error);
			}
			else {
				try {
					Connection con = DriverManager.getConnection(  
							"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");
						
					PreparedStatement stmt;		
					stmt= con.prepareStatement("Update AirlineTable set Name = ? where AirlineID = ?");  
					
					stmt.setString(1, name);
					
					stmt.setInt(2, this.getID());
					int count = stmt.executeUpdate();
					if(count>0) {
						System.out.println("Updated");
					}

					
					con.close();
					response.sendRedirect("ManageAirlines");
					return;
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
			}
		}
		else if(request.getParameter("click").equals("Delete")) {
			try {
				Connection con = DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");
			
				PreparedStatement stmt=con.prepareStatement("Delete from AirlineTable where AirlineID = ?");
				stmt.setInt(1,this.getID());
				
				int count = stmt.executeUpdate();
				if(count > 0) {
					System.out.println("Deleted");
				}
				con.close();
				
				
				response.sendRedirect("ManageAirlines");
				return;
				
			}catch(Exception e) {
				
			}
		}
		request.setAttribute("_ID", this.getID());
		doGet(request, response);
	}

}
