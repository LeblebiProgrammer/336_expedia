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
 * Servlet implementation class AllFlightsGivenAirport
 */
@WebServlet("/AllFlightsGivenAirport")
public class AllFlightsGivenAirport extends HttpServlet {
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
     
     protected void initialHtml(java.io.PrintWriter out, ResultSet airport) throws SQLException {
	    	String str = "\n" + 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
	    			"<head>\n" + 
	    			"<meta charset=\"UTF-8\">\n" + 
	    			"<title>All flights for airport</title>\n" + 
	    			"</head>\n" + 
	    			"<body>\n" + 
	    			"	<form action=\"AllFlightsGivenAirport\" method=\"post\">\n" + 
	    	 
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
//    			"		<p>Add Airport</p>\n" + 
//    			"		<table border=\"2\">\n" + 
//    			"			<tbody>\n" + 
//    			"				<tr>\n" + 
//    			"					<td>Airline Name</td>\n" +		
//    			"				</tr>\n" + 
//    			"				<tr>\n" + 
//    			"					<td><input type=\"text\" name=\"Name\" /></td>\n" + 			
//    			"				</tr>\n" + 
//    			"			</tbody>\n" + 
//    			"		</table>\n" + 
//    			"		\n" + 
//    			"		<input type=\"submit\" name = \"click\" value=\"Add Airline\" />\n" + 
//    			"	</div>\n" + 
    			"	</form>\n" + 
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }

    public AllFlightsGivenAirport() {
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
			
			PreparedStatement stmt2= con.prepareStatement("Select AirportName, AirportCode from AirportTable");
			ResultSet rs2 = stmt2.executeQuery();
			
			
			initialHtml(response.getWriter(), rs2);
			PreparedStatement stmt;	
			if(this.getCode().length() > 0) {
				stmt=con.prepareStatement("Select fit.FlightNumber, fit.AirlineName, fit.DepartureCity, fit.DestinationCity, fit.DepartureTime, fit.ArrivalTime from AirportTable ait join FlightInfoTable fit on (ait.AirportCode = fit.DepartureCity) where ait.AirportCode = ? union "
						+ "Select fit.FlightNumber, fit.AirlineName, fit.DepartureCity, fit.DestinationCity, fit.DepartureTime, fit.ArrivalTime from AirportTable ait join FlightInfoTable fit on (ait.AirportCode = fit.DestinationCity) where ait.AirportCode = ? group by FlightNumber");  
				stmt.setString(1, this.getCode());
				stmt.setString(2, getCode());
				ResultSet rs = stmt.executeQuery();
				makeTable(rs, response.getWriter());
			}

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
//		String className = "ManageAirlines";
		//navigation
		if(request.getParameter("click").equals("Search")) {
			String code = request.getParameter("Code");
			if(code != null) {
				this.setCode(code);
			}
		}
		
		doGet(request, response);
	}

}
