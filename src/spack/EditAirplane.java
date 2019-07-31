package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditAirplane
 */
@WebServlet("/EditAirplane")
public class EditAirplane extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAirplane() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String tailNumber;
    
    private String getTail() {
    	return tailNumber;
    }
    
    private void setTail(String _val) {
    	tailNumber = _val;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
    
    
    
    protected void initializeHtml(java.io.PrintWriter out , ResultSet rs) throws Exception {
    	if(rs.next()) {
    	String str = 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
	    			"<head>"+
	    			"<meta charset=\"UTF-8\">\n" + 
	    			"<title>Edit Delete Aircraft</title>\n" +
	    			"</head><body>" + 
		    			"<div>\n<form action=\"EditAirplane\" method=\"post\">" + 
		    			"		<br> <br> <br> <br>\n" + 
		    			"		<p>Add airplane</p>\n" + 
		    			"		<table border=\"2\">\n" + 
		    			"			<tbody>\n" + 
		    			"				<tr>\n" + 
		    			"					<td>Tail Number</td>\n" + 
		    			"					<td>Manufacturer</td>\n" + 
		    			"					<td>Model</td>\n" + 
		    			"					<td>Economy Count</td>\n" + 
		    			"					<td>Business Count</td>\n" + 
		    			"					<td>First Count</td>\n" + 
		    			"				</tr>\n" + 
		    			"				<tr>\n" + 
		    			"					<td><input name=\"tailNumber\" value = \""+ rs.getString("TailNumber") +"\"/></td>\n" + 
		    			"					<td><input name=\"manufacturer\" value = \""+rs.getString("Manufacturer")+"\"    /></td>\n" + 
		    			"					<td><input name=\"model\" value = \""+ rs.getString("Model")+"\" /></td>\n" + 
		    			"					<td><input name=\"economyCount\" type = \"number\" min=\"0\" value = \""+ rs.getInt("EconomyCount") + "\"/></td>\n" + 
		    			"					<td><input name=\"businessCount\" type = \"number\" min=\"0\"  value = \""+ rs.getInt("BusinessCount") +"\" /></td>\n" + 
		    			"					<td><input name=\"firstCount\" type = \"number\" min=\"0\"  value = \""+ rs.getInt("FirstCount") +"\"/></td>\n" + 
		    			"				</tr>\n" + 
		    			"			</tbody>\n" + 
		    			"		</table>\n" + 
		    			"		\n" + 
		    			"		<input type=\"submit\" name = \"click\" value=\"Update\" />\n" +
		    			"<input type=\"submit\" name = \"click\" value=\"Delete\" />\n" +
		    			"	</form></div>\n" + 
	    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    	}
    }
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String tailNumber =(String) request.getAttribute("TailNumber");
		if(tailNumber == null) {
			
		}
		else {
			this.setTail(tailNumber);
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");
					
				PreparedStatement stmt;		
				stmt=con.prepareStatement("Select TailNumber, Manufacturer, Model, EconomyCount, BusinessCount, FirstCount from AircraftTable where TailNumber = ?");
				stmt.setString(1, tailNumber);
				
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
		String forwardAddress = "";
		if(request.getParameter("click").equals("Update")) {
			String _tailNumber = "";
			String manufacturer = "";
			String model = "";
			String economy = "";
			String business = "";
			String first = "";
			
			String error = "";
			
			int x = 0;
			int x2 = 0;
			int x3 = 0;
			
			
			if(request.getParameter("tailNumber") != null) {
				_tailNumber = request.getParameter("tailNumber");
			}else {
				error = "Tail number cannot be empty. ";
			}
			
			if(request.getParameter("manufacturer") != null) {
				manufacturer = request.getParameter("manufacturer");
			}else {
				error += "Manufacturer cannot be empty. ";
			}
			
			if(request.getParameter("model") != null) {
				model = request.getParameter("model");
			}else {
				error += "Model cannot be empty. ";
			}
			
			if(request.getParameter("economyCount") != null) {
				economy = request.getParameter("economyCount");
				x = 0;
				try {
					x = Integer.parseInt(economy);
				}catch(Exception e) {
					error += "Please enter a numerical value for economy count. ";
				}
			}
			
			if(request.getParameter("businessCount") != null) {
				business = request.getParameter("businessCount");
				x2 = 0;
				try {
					x2 = Integer.parseInt(business);
				}catch(Exception e) {
					error += "Please enter a numerical value for business count. ";
				}
			}
			
			if(request.getParameter("firstCount") != null) {
				x3 = 0;
				first = request.getParameter("firstCount");
				try {
					x3 = Integer.parseInt(first);
				}catch(Exception e){
					error += "Please enter a numerical value for first count. ";
				}
			}
			if(error.length() == 0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Update AircraftTable set Manufacturer = ?,"
							+ " Model = ?, EconomyCount = ?, BusinessCount = ?, FirstCount = ?, TailNumber = ? where TailNumber = ? ");  
				
	
					stmt.setString(1, manufacturer);
					stmt.setString(2, model);
	
					stmt.setInt(3, x);

					stmt.setInt(4, x2);
	
					stmt.setInt(5, x3);
					stmt.setString(6, _tailNumber);
					
					stmt.setString(7, this.getTail());
					
					int count = stmt.executeUpdate();
					if(count > 0) {
						System.out.println("Updated");
					}
					con.close();
//					RequestDispatcher dispatcher = request.getRequestDispatcher("ManageAirplanes");
//					dispatcher.forward( request, response);
					response.sendRedirect("ManageAirplanes");
					
				}catch(Exception e) {
					System.out.println(e.getMessage());
					error += e.getMessage();
				}
			}
			
			if(error.length()>0) {
				request.setAttribute("error", error);
				doGet(request, response);
				return;
			}
					
		}
		else if (request.getParameter("click").equals("Delete")) {
			String error = "";
			String _tail = "";
			if(request.getParameter("tailNumber") != null) {
				_tail = request.getParameter("tailNumber");
			}else {
				error = "Tail number cannot be empty. ";
			}
			
			if(error.length() == 0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Delete from AircraftTable where TailNumber = ?");
					stmt.setString(1, _tail);
					
					
					int count = stmt.executeUpdate();
					if(count > 0) {
						System.out.println("Deleted");
					}
					con.close();
					
					
//					RequestDispatcher dispatcher = request.getRequestDispatcher("ManageAirplanes");
//					dispatcher.forward( request, response);
					response.sendRedirect("ManageAirplanes");
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
