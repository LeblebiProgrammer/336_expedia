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
 * Servlet implementation class ManageAirports
 */
@WebServlet("/ManageAirports")
public class ManageAirports extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageAirports() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void initialHtml(java.io.PrintWriter out) {
    	String str = "\n" + 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>Manage Airports</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"ManageAirports\" method=\"post\">\n" + 
    	 
    	"			<table style=\"height: 51px; width: 100px; float: left;\" border=\"1\">\n" + 
    	"				<tbody>\n" + 
    	"<tr style=\"height: 27px;\">\n" + 
    	"					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"						name=\"click\" type=\"submit\" value=\"BookUser\" /></td>\n" + 
    	"				</tr>\n" + 
    	"				<tr style=\"height: 27px;\">\n" + 
    	"					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"						name=\"click\" type=\"submit\" value=\"ChangeFlight\" /></td>\n" + 
    	"				</tr>\n" + 
    	"				<tr style=\"height: 27px;\">\n" + 
    	"					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"						name=\"click\" type=\"submit\" value=\"ManageFlights\" /></td>\n" + 
    	"				</tr>\n" + 
    	"				<tr style=\"height: 27px;\">\n" + 
    	"					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"						name=\"click\" type=\"submit\" value=\"ManageAirports\" /></td>\n" + 
    	"				</tr>\n" + 
    	"				<tr style=\"height: 27px;\">\n" + 
    	"					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"						name=\"click\" type=\"submit\" value=\"ManageAirlines\" /></td>\n" + 
    	"				</tr>\n" + 
    	"				<tr style=\"height: 27px;\">\n" + 
    	"					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"						name=\"click\" type=\"submit\" value=\"ManageAirplanes\" /></td>\n" + 
    	"				</tr>\n" + 
    	"				<tr style=\"height: 27px;\">\n" + 
    	"					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n" + 
    	"						name=\"click\" type=\"submit\" value=\"GetWaitlist\" /></td>\n" + 
    	"				</tr>"+
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
				if(rsmd.getColumnLabel(i+1).equals("AirportCode")) {
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
    			"		<p>Add Airport</p>\n" + 
    			"		<table border=\"2\">\n" + 
    			"			<tbody>\n" + 
    			"				<tr>\n" + 
    			"					<td>Airport Code</td>\n" + 
    			"					<td>Airport City</td>\n" +
    			"					<td>Airport Name</td>\n" + 
    			"					<td>Airport Address</td>\n" + 
    			"				</tr>\n" + 
    			"				<tr>\n" + 
    			"					<td><input type=\"text\" name=\"Code\" /></td>\n" + 
    			"					<td><input type=\"text\" name=\"City\" /></td>\n" + 
    			"					<td><input type=\"text\" name=\"Name\" /></td></td>\n" + 
    			"					<td><input type=\"text\" name=\"Address\" /></td>\n" + 
    			"				</tr>\n" + 
    			"			</tbody>\n" + 
    			"		</table>\n" + 
    			"		\n" + 
    			"		<input type=\"submit\" name = \"click\" value=\"Add Airport\" />\n" + 
    			"	</div>\n" + 
    			"	</form>\n" + 
    			"<p>${error}</p>"+
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			
			PreparedStatement stmt;	
			
			stmt=con.prepareStatement("Select * from AirportTable");  
			
			
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
		
		//navigation
		if(request.getParameter("click").equals("BookUser")) {
			response.sendRedirect("BookUser");
			return;
		}
		if(request.getParameter("click").equals("ChangeFlight")) {
			response.sendRedirect("ChangeFlight");
			return;
		}
		if(request.getParameter("click").equals("ManageFlights")) {
			response.sendRedirect("ManageFlights");
			return;
		}
		if(request.getParameter("click").equals("ManageAirlines")) {
			response.sendRedirect("ManageAirlines");
			return;
		}
		if(request.getParameter("click").equals("ManageAirports")) {
			//return;
		}
		if(request.getParameter("click").equals("ManageAirplanes")) {
			response.sendRedirect("ManageAirplanes");
			return;
		}
		if(request.getParameter("click").equals("GetWaitlist")) {
			response.sendRedirect("GetWaitlist");
			return;
		}
		
		
		if(request.getParameter("click").equals("Add Airport")) {
			String code = "";
			String city = "";
			String name = "";
			String address = "";
			
			
			String error = "";

			
			if(request.getParameter("Code") != null) {
				code = request.getParameter("Code");
			}else {
				error = "Airport code cannot be empty. ";
			}
			if(request.getParameter("City") != null) {
				city = request.getParameter("City");
			}else {
				error = "Airport city cannot be empty. ";
			}
			
			if(request.getParameter("Name") != null) {
				name = request.getParameter("Name");
			}else {
				error += "Airport name cannot be empty. ";
			}
			
			if(request.getParameter("Address") != null) {
				address = request.getParameter("Address");
			}else {
				error += "Airport address cannot be empty. ";
			}
		
			if(error.length() > 0) {
				
			}
			else {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Insert into AirportTable (AirportCode,"
							+ "AirportCity, AirportName, AirportAddress) Values (?, ?, ?, ?)");  
				
					//RequestDispatcher dispatcher;
					stmt.setString(1, code);
					stmt.setString(2, city);
					stmt.setString(3, name);
					stmt.setString(4, address);
					
					int count = stmt.executeUpdate();
					if(count > 0) {
						System.out.println("Inserted");
					}
					con.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
					
			}
			doGet(request, response);
			return;
		}
		else {
			String code = request.getParameter("click");
			if(code != null) {
				//request.setAttribute("TailNumber", tailNumber);
				RequestDispatcher dispatcher = request.getRequestDispatcher("EditAirport");
				request.setAttribute("Code", code);
				dispatcher.forward( request, response);
//				response.sendRedirect("EditAirplane");
				return;
			}
			
		}
		doGet(request, response);
	}

}
