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

/**
 * Servlet implementation class ManageUsers
 */
@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {
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
    			"	<form action=\"ManageUsers\" method=\"post\">\n" + 
    	 
    	"			<table style=\"height: 51px; width: 100px; float: left;\" border=\"1\">\n" + 
    	"				<tbody>\n" + 
    	"					<tr style=\"height: 27px;\">\n" + 
    	"						<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"							name=\"click\" type=\"submit\" value=\"Manage users\" /></td>\n" + 
    	"					</tr>\n" + 
    	"					<tr style=\"height: 27px;\">\n" + 
    	"						<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"							name=\"click\" type=\"submit\" value=\"GetSales\" /></td>\n" + 
    	"					</tr>\n" + 
    	"					<tr style=\"height: 27px;\">\n" + 
    	"						<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"							name=\"click\" type=\"submit\" value=\"GetReservations\" /></td>\n" + 
    	"					</tr>\n" + 
    	"					<tr style=\"height: 27px;\">\n" + 
    	"						<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"							name=\"click\" type=\"submit\" value=\"GetRevenue\" /></td>\n" + 
    	"					</tr>\n" + 
    	"					<tr style=\"height: 27px;\">\n" + 
    	"						<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"							name=\"click\" type=\"submit\" value=\"GetFlightHistory\" /></td>\n" + 
    	"					</tr>\n" + 
    	"					<tr style=\"height: 27px;\">\n" + 
    	"						<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"							name=\"click\" type=\"submit\" value=\"GetallFlights\" /></td>\n" + 
    	"					</tr>\n" + 
    	"\n" + 
    	"				</tbody>\n" + 
    	"			</table></div>\n" ;
    	
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

    protected void finishHtml(java.io.PrintWriter out) {
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
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			
			PreparedStatement stmt;	
			
			stmt=con.prepareStatement("Select ID, FirstName, LastName, AccountType, EmailAddress from UserTable");  
			
			
			ResultSet rs = stmt.executeQuery();
			
			initialHtml(response.getWriter());
			makeTable(rs, response.getWriter());

			finishHtml(response.getWriter());
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
		
		//menu options
		
		
		
		if(request.getParameter("click").equals("Add User")) {
			
		}
		else if(request.getParameter("click") != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("EditUser");
			String str = request.getParameter("click");
			request.setAttribute("_ID", str);
			dispatcher.forward( request, response);
		}
		//person details
		
		
		doGet(request, response);
	}

}
