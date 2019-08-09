package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mysql.jdbc.ResultSetMetaData;

import spack.AdminPages.NavigationBar;

/**
 * Servlet implementation class ManageUsers
 */
@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet implements NavigationBar{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void initialHtml(java.io.PrintWriter out) {
    	String str = "\n" + 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>Manage Users</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			navbarhtml + 
    			"<form action=\"ManageUsers\" method=\"post\">";
    	
    	out.print(str);
    }

    private int makeTable(java.sql.ResultSet rs, java.io.PrintWriter out)
    	    throws Exception {
		 int rowCount = 0;
		
		 out.println("<P ALIGN='center'><TABLE BORDER=1>");
		 ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		 int columnCount = rsmd.getColumnCount();
		 // table header
		 out.println("<TR>");
    	 for (int i = 0; i < columnCount; i++) {
    		 out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
		 }
    	 out.println("</TR>");
		 // the data
		while (rs.next()) {
			rowCount++;
			out.println("<TR>");
			for (int i = 0; i < columnCount; i++) {
				if(rsmd.getColumnLabel(i+1).equals("ID")) {
					out.println("<TD>");
					out.println("<input name=\"click\" type=\"submit\" value=\""+ rs.getString(i+1)  +"\" /> ");
					
				}else {
					out.println("<TD>" + rs.getString(i + 1) + "</TD>");
				}
			}
//			out.println("<input name=\"click\" type=\"submit\" value=\"Book for User\" /> ");
			out.println("</TR>");
		}
		out.println("</TABLE></P>");
		return rowCount;
	}

    protected void finishHtml(java.io.PrintWriter out, String error) {
    	String str = "<div>\n" + 
    			"		<br> <br> <br> <br>\n" + 
    			"		<p>Add User</p>\n" + 
    			"		<table border=\"2\">\n" + 
    			"			<tbody>\n" + 
    			"				<tr>\n" + 
    			"					<td>First Name</td>\n" + 
    			"					<td>Last Name</td>\n" + 
    			"					<td>Account Type</td>\n" + 
    			"					<td>Password</td>\n" + 
    			"					<td>EmailAddress</td>\n" + 
    			"				</tr>\n" + 
    			"				<tr>\n" + 
    			"					<td><input type=\"text\" name=\"first_name\" /></td>\n" + 
    			"					<td><input type=\"text\" name=\"last_name\" /></td></td>\n" + 
    			"					<td><input name=\"accountType\" type = \"number\" min=\"1\" max = \"3\" value = \"2\" /></td>\n" +
    			"					<td><input type=\"password\" name=\"password\" /></td>\n" + 
    			"					<td><input type=\"text\" name=\"email\" /></td></td>\n" +
    			"				</tr>\n" + 
    			"			</tbody>\n" + 
    			"		</table>\n" + 
    			"		\n" + 
    			"		<input type=\"submit\" name = \"click\" value=\"Add User\" />\n" + 
    			"	</div>\n" + 
    			"	</form>\n" + 
    			"<p>" + error+ "</p>"+
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			String error = request.getParameter("error");
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			
			PreparedStatement stmt;	
			
			stmt=con.prepareStatement("Select ID, FirstName, LastName, AccountType, EmailAddress from UserTable");  
			
			
			ResultSet rs = stmt.executeQuery();
			
			initialHtml(response.getWriter());
			makeTable(rs, response.getWriter());
			
			if(error == null) {
				error = "";
			}
			finishHtml(response.getWriter(), error);
			con.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//navigation
		if(request.getParameter("click").equals("Manage users")) {
			
		}
		if(request.getParameter("click").equals("GetSales")) {
			response.sendRedirect("GetSales");
			return;
		}
		if(request.getParameter("click").equals("GetReservations")) {
			response.sendRedirect("GetReservations");
			return;
		}
		if(request.getParameter("click").equals("GetRevenue")) {
			response.sendRedirect("GetRevenue");
			return;
		}
		if(request.getParameter("click").equals("GetFlightHistory")) {
			response.sendRedirect("GetFlightHistory");
			return;
		}
		if(request.getParameter("click").equals("GetallFlights")) {
			response.sendRedirect("GetAllFlights");
			return;
		}
		
		
		
		if(request.getParameter("click").equals("Add User")) {
			String firstName = "";
			String lastName = "";

			String password = "";
			String emailAddress = "";

			String temptype = request.getParameter("accountType");
			
			int account = 0;
			String error = "";
			
			firstName = request.getParameter("first_name");
			lastName = request.getParameter("last_name");
			
			password = request.getParameter("password");
			emailAddress = request.getParameter("email");
			
			String accountNumber = request.getParameter("accountType");
			
			boolean isCorrect = true;
			
			if(firstName.length() == 0) {
				isCorrect = false;
				error = "Please enter a first name. ";
			}
			if(lastName.length() == 0) {
				error += "Please enter a last name. ";
				isCorrect = false;
			}
			if(temptype.length() == 0) {
				error += "Please enter an account type. ";
				isCorrect = false;
			}
			else {
				try {
					account = Integer.parseInt(temptype);
					if(account > 3 || account <1) {
						error += "Please enter account type in between 1 and 3. ";
					}
				}catch(Exception e) {
					error += e.getMessage();
				}
			}
			if(password.length() == 0) {
				isCorrect = false;
				request.setAttribute("error3", "Please enter password");
			}
			if(emailAddress.length() == 0) {
				request.setAttribute("error4", "Please enter an email");
				isCorrect = false;
			}
			
			if(isCorrect == true) {
				 
			
				
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Insert into UserTable (FirstName, LastName, Password, AccountType, EmailAddress) Values (?, ?, ?, ?, ?)");  
	
					int ac = Integer.parseInt(accountNumber);
					stmt.setString(1, firstName);
					stmt.setString(2, lastName);
					stmt.setString(3, password);
					stmt.setInt(4, ac);
					stmt.setString(5, emailAddress);
					
					stmt.executeUpdate();
					con.close();
					
				}catch(Exception e) {
					error += e.getMessage();
					
				}
			}
			if(error.length()> 0) {
				request.setAttribute("error", error);
			}
			
			
		}
		else if(request.getParameter("click") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("EditUser");
			String str = request.getParameter("click");
			request.setAttribute("userID", str);
			dispatcher.forward( request, response);
		}
		//person details
		
		
		doGet(request, response);
	}

}
