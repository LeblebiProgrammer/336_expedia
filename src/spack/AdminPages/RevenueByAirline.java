package spack.AdminPages;

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

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class RevenueByAirline
 */
@WebServlet("/RevenueByAirline")
public class RevenueByAirline extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
     private String aic = "";
	
	private void setCode(String code) {
		aic = code;
	}
	
	private String getCode() {
		return aic;
	}
	
     protected void initialHtml(java.io.PrintWriter out) {
	    	String str = "\n" + 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
	    			"<head>\n" + 
	    			"<meta charset=\"UTF-8\">\n" + 
	    			"<title>Amount of sale by airline</title>\n" + 
	    			"</head>\n" + 
	    			"<body>\n" + 
	    			"	<form action=\"RevenueByAirline\" method=\"post\">\n" + 
	    	 
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
			"							name=\"click\" type=\"submit\" value=\"GetAllFlights\" /></td>\n" + 
			"					</tr>\n" + 
			"\n" + 
			"				</tbody>\n" + 
			"			</table></div>\n"+
			
	"			<table style=\"height: 51px; width: 100px; float: left;\" border=\"1\">\n" + 
	"				<tbody>\n" + 
	"					<tr style=\"height: 27px;\">\n" + 
	"						<td style=\"width: 260px; height: 27px; text-align: center;\">Search for airport</td>\n";
	
	    	str += "<td width = \"14%\"><select name = \"Code\" size = \"1\">";
	    	
	    	
	    	
	    	//departure
	    	while(airport.next()) {
	    		//String option= "<option value = \"" + aircraft.getString("TailNumber") + "\"" + aircraft.getString("TailNumber") + "</option>";\
	    		try {
	    			if (airport.getString("AirportCode").equals(this.getCode())) {
						String option = "<option value = \"" + airport.getString("AirportCode") + "\" selected>"
								+ airport.getString("AirportName") + "</option>\n";
						str += option;
					} else {
	    			String option = "<option value = \"" + airport.getString("AirportCode") + "\">" + airport.getString("AirportName") + "</option>\n";
	    			str += option;
					}
	    		}
	    		catch(Exception e) {
	    			System.out.print(e.getMessage());
	    		}
	    	}
	    	str += "</select></td>";
	
	
	
	
	str += "						<td>	<input name=\"click\" type=\"Submit\" value=\"Search\" /></td>\n"+
	"					</tr>\n" + 
	"\n" + 
	"				</tbody>\n" + 
	"			</table></div>\n";
			
			;
	    	
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
				if(rsmd.getColumnLabel(i+1).equals("AirlineID")) {
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
    			"					<td>Airline Name</td>\n" +		
    			"				</tr>\n" + 
    			"				<tr>\n" + 
    			"					<td><input type=\"text\" name=\"Name\" /></td>\n" + 			
    			"				</tr>\n" + 
    			"			</tbody>\n" + 
    			"		</table>\n" + 
    			"		\n" + 
    			"		<input type=\"submit\" name = \"click\" value=\"Add Airline\" />\n" + 
    			"	</div>\n" + 
    			"	</form>\n" + 
    			"<p>${error}</p>"+
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }

    public RevenueByAirline() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
    try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			
			PreparedStatement stmt;	
			
			stmt=con.prepareStatement("select at.AirlineName, at.AirlineID, sum(rft.BookedPrice) from AirlineTable at join ReservationsFlightsTable rft using (AirlineID) group by at.AirlineID");  
			
			
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
    String className = "ManageAirlines";
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
			
		}
		if(request.getParameter("click").equals("ManageAirports")) {
			response.sendRedirect("ManageAirports");
			return;
		}
		if(request.getParameter("click").equals("ManageAirplanes")) {
			response.sendRedirect("ManageAirplanes");
			return;
		}
		if(request.getParameter("click").equals("GetWaitlist")) {
			response.sendRedirect("GetWaitlist");
		}
		
		
		
		
		if(request.getParameter("click").equals("Add Airline")) {
			String name = "";
			
			
			String error = "";

			
			
			if(request.getParameter("Name") != null) {
				name = request.getParameter("Name");
			}else {
				error = "Airport name cannot be empty. ";
			}
			
			if(error.length() > 0) {
				
			}
			else {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Insert into AirlineTable (Name) Values (?)");  
				
					//RequestDispatcher dispatcher;
					stmt.setString(1, name);
					
					
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
			String id = request.getParameter("click");
			if(id != null) {
				if(!id.equals(className)) {
					//request.setAttribute("TailNumber", tailNumber);
					RequestDispatcher dispatcher = request.getRequestDispatcher("EditAirline");
					request.setAttribute("_id", id);
					dispatcher.forward( request, response);
	//				response.sendRedirect("EditAirplane");
					return;
				}
			}
			
		}
		doGet(request, response);
	}

}
