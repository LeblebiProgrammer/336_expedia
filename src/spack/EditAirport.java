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
 * Servlet implementation class EditAirports
 */
@WebServlet("/EditAirport")
public class EditAirport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private String airportCode;
	
	private void setAirportCode(String value) {
		airportCode = value;
	}
	
	private String getAirportCode() {
		return airportCode;
	}
	
	 protected void initializeHtml(java.io.PrintWriter out , ResultSet rs) throws Exception {
	    	if(rs.next()) {
	    	String str = 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
		    			"<head>"+
		    			"<meta charset=\"UTF-8\">\n" + 
		    			"<title>Edit Delete Airport</title>\n" +
		    			"</head><body>" + 
			    			"<div>\n<form action=\"EditAirport\" method=\"post\">" + 
			    			"		<br> <br> <br> <br>\n" + 
			    			"		<p>Add airplane</p>\n" + 
			    			"		<table border=\"2\">\n" + 
			    			"			<tbody>\n" + 
			    			"				<tr>\n" + 
			    			"					<td>Airport Code</td>\n" + 
			    			"					<td>Airport Name</td>\n" + 
			    			"					<td>Airport City</td>\n" + 
			    			"					<td>Airport Address</td>\n" + 
			    			"				</tr>\n" + 
			    			"				<tr>\n" + 
			    			"					<td><input name=\"Code\" value = \""+rs.getString("AirportCode")+"\"    /></td>\n" + 
			    			"					<td><input name=\"Name\" value = \""+ rs.getString("AirportName")+"\" /></td>\n" + 
			    			"					<td><input name=\"City\"   value = \""+ rs.getString("AirportCity") +"\" /></td>\n" + 
			    			"					<td><input name=\"Address\"   value = \""+ rs.getString("AirportAddress") +"\"/></td>\n" + 
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
	    
	
	
    public EditAirport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String code =(String) request.getAttribute("Code");
		if(code == null) {
			
		}
		else {
			this.setAirportCode(code);
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");
					
				PreparedStatement stmt;		
				stmt=con.prepareStatement("Select * from AirportTable where AirportCode = ?");
				stmt.setString(1, code);
				
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
			String code = "";
			String name = "";
			String city = "";
			String address = "";
			
			
			String error = "";
			

			
			
			if(request.getParameter("Code") != null) {
				code = request.getParameter("Code");
				if(code.length() == 0) {
					error = "Airport code cannot be empty. ";
				}
			}else {
				error = "Airport code cannot be empty. ";
			}
			
			if(request.getParameter("Name") != null) {
				name = request.getParameter("Name");
				if(name.length() == 0) {
					error += "Airport name cannot be empty. ";
				}
			}else {
				error += "Airport name cannot be empty. ";
			}
			
			if(request.getParameter("City") != null) {
				city = request.getParameter("City");
				if(city.length() == 0) {
					error += "Airport city cannot be empty. ";
				}
			}else {
				error += "Airport city cannot be empty. ";
			}
			
			if(request.getParameter("Address") != null) {
				address = request.getParameter("Address");
				if(address.length() == 0) {
					error += "Airport address cannot be empty.";
				}
			}else {
				error += "Airport address cannot be empty.";
			}
			
			
			if(error.length() == 0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Update AirportTable set AirportCode = ?,"
							+ " AirportName = ?, AirportCity = ?, AirportAddress = ? where AirportCode = ? ");  
				
	
					stmt.setString(1, code);
					stmt.setString(2, name);
	
					
					stmt.setString(3, city);
					
					stmt.setString(4, address);
					stmt.setString(5, this.getAirportCode());
					
					int count = stmt.executeUpdate();
					if(count > 0) {
						System.out.println("Updated");
					}
					con.close();

					response.sendRedirect("ManageAirports");
					
				}catch(Exception e) {
					System.out.println(e.getMessage());
					error += e.getMessage();
				}
			}
			
			if(error.length()>0) {
				request.setAttribute("Code", this.getAirportCode());
				request.setAttribute("error", error);
				doGet(request, response);
				return;
			}
					
		}
		else if (request.getParameter("click").equals("Delete")) {
			String error = "";
			String code = "";
			if(request.getParameter("Code") != null) {
				code = request.getParameter("Code");
			}else {
				error = "Airport code cannot be empty. ";
			}
			
			if(error.length() == 0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Delete from AirportTable where AirportCode = ?");
					stmt.setString(1, code);
					
					
					int count = stmt.executeUpdate();
					if(count > 0) {
						System.out.println("Deleted");
					}
					con.close();
					

					response.sendRedirect("ManageAirports");
					return;
					
				}catch(Exception e) {
					error += e.getMessage();
				}
			}
			if(error.length()>0) {
				request.setAttribute("error", error);
				doGet(request, response);
				return;
			}
		}
		else {
			doGet(request, response);
		}
	}

}
