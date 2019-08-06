package spack.AdminPages;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditCustomerRep
 */
@WebServlet("/EditCustomerRep")
public class EditCustomerRep extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCustomerRep() {
        super();
        // TODO Auto-generated constructor stub
        private int ID;
	
	private void setID(int value) {
		ID = value;
	}
	
	private int getID(){
		return ID;
	}
	
	
	 protected void initializeHtml(java.io.PrintWriter out , ResultSet rs) throws Exception {
	    	if(rs.next()) {
	    	String str = 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
		    			"<head>"+
		    			"<meta charset=\"UTF-8\">\n" + 
		    			"<title>Edit Delete Users</title>\n" +
		    			"</head><body>" + 
			    			"<div>\n<form action=\"EditUser\" method=\"post\">" + 
			    			"		<br> <br> <br> <br>\n" + 
			    			"		<p>Add airplane</p>\n" + 
			    			"		<table border=\"2\">\n" + 
			    			"			<tbody>\n" + 
			    			"				<tr>\n" + 
			    			"					<td>First Name</td>\n" + 
			    			"					<td>LastName</td>\n" + 
			    			"					<td>AccountType</td>\n" + 
			    			"					<td>Email</td>\n" + 
			    			"					<td>Password</td>\n" + 
			    			"				</tr>\n" + 
			    			"				<tr>\n" + 
			    			"					<td><input name=\"FirstName\" value = \""+rs.getString("FirstName")+"\"    /></td>\n" + 
			    			"					<td><input name=\"LastName\" value = \""+ rs.getString("LastName")+"\" /></td>\n" + 
			    			"					<td><input name=\"AccountType\" type = \"number\" min=\"0\" value = \""+ rs.getInt("AccountType") + "\"/></td>\n" + 
			    			"					<td><input name=\"Email\"   value = \""+ rs.getString("EmailAddress") +"\" /></td>\n" + 
			    			"					<td><input name=\"Password\"   value = \""+ rs.getString("Password") +"\"/></td>\n" + 
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
    int id = 0;
		String strid = (String) request.getAttribute("repID");
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
				stmt=con.prepareStatement("Select FirstName, LastName, AccountType, EmailAddress, Password from RepsTable where ID = ?");
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
			String firstName = "";
			String lastName = "";

			String password = "";
			String emailAddress = "";
			int accountType = 0;

			String error = "";
			
			firstName = request.getParameter("FirstName");
			lastName = request.getParameter("LastName");
			
			password = request.getParameter("Password");
			emailAddress = request.getParameter("Email");
			accountType = Integer.parseInt(request.getParameter("AccountType"));
			
			boolean isCorrect = true;
			
			if(firstName.length() == 0) {
				isCorrect = false;
				error = "First name cannot be empty. ";
			}
			if(lastName.length() == 0) {
				error += "Last name cannot be empty. ";
				isCorrect = false;
			}
			if(password.length() == 0) {
				isCorrect = false;
				error += "Password cannot be empty. ";
			}
			if(emailAddress.length() == 0) {
				error += "Email cannot be empty. ";
				isCorrect = false;
			}
			if(accountType != 0) {
				if(accountType > 3 || accountType<1) {
					error += "Account type must be between 1 and 3.";
					isCorrect = false;
				}
			}
			else {
				isCorrect = false;
				error += "Account type must be between 1 and 3.";
			}
			if(isCorrect == false) {
				 request.setAttribute("error", error);
			}
			else {
				try {
					Connection con = DriverManager.getConnection(  
							"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");
						
					PreparedStatement stmt;		
					stmt= con.prepareStatement("Update UserTable set FirstName = ?,"
							+ " LastName = ?, AccountType = ?, Password = ?, EmailAddress = ? where ID = ?");  
					
					stmt.setString(1, firstName);
					stmt.setString(2, lastName);
					stmt.setInt(3, accountType);
					stmt.setString(4, password);
					stmt.setString(5, emailAddress);
					stmt.setInt(6, this.getID());
					int count = stmt.executeUpdate();
					if(count>0) {
						System.out.println("Updated");
					}

					
					con.close();
					response.sendRedirect("ManageUsers");
					return;
					}catch(Exception e) {
						
					}
			}
		}
		else if(request.getParameter("click").equals("Delete")) {
			try {
				Connection con = DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");
			
				PreparedStatement stmt=con.prepareStatement("Delete from UserTable where ID = ?");
				stmt.setInt(1,this.getID());
				
				int count = stmt.executeUpdate();
				if(count > 0) {
					System.out.println("Deleted");
				}
				con.close();
				
				
				response.sendRedirect("ManageUsers");
				return;
				
			}catch(Exception e) {
				
			}
		}
		request.setAttribute("_ID", this.getID());
		doGet(request, response);
	}
