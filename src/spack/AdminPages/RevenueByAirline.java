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
     
	private String airlineCode = "";
	
	private void setCode(String code) {
		airlineCode = code;
	}
	
	private String getCode() {
		return airlineCode;
	}
     
     protected void initialHtml(java.io.PrintWriter out, ResultSet airline) throws SQLException {
	    	String str = "\n" + 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
	    			"<head>\n" + 
	    			"<meta charset=\"UTF-8\">\n" + 
	    			"<title>Revenue by Airlinet</title>\n" + 
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
	"						<td style=\"width: 260px; height: 27px; text-align: center;\">Search for Airline</td>\n";
	
	    	str += "<td width = \"14%\"><select name = \"Code\" size = \"1\">";
	    	
	    	
	    	
	    	//departure
	    	while(airline.next()) {
	    		//String option= "<option value = \"" + aircraft.getString("TailNumber") + "\"" + aircraft.getString("TailNumber") + "</option>";\
	    		try {
	    			if (airline.getString("Name").equals(this.getCode())) {
						String option = "<option value = \"" + airline.getString("Name") + "\" selected>"
								+ airline.getString("Name") + "</option>\n";
						str += option;
					} else {
	    			String option = "<option value = \"" + airline.getString("Name") + "\">" + airline.getString("Name") + "</option>\n";
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
			
			PreparedStatement stmt=con.prepareStatement("select Name from AirlineTable");  
			ResultSet rs = stmt.executeQuery();
			initialHtml(response.getWriter(), rs);
			
			
			if(this.airlineCode.length() >0) {
				PreparedStatement stmt2 = con.prepareStatement("Select sum(rft.bookedPrice) from ReservationsFlightsTable rft join " + 
						"	FlightsTable ft on(rft.FlightsID = ft.FlightsID) join\n" + 
						"    FlightInfoTable fit on (ft.FlightNumber = fit.FlightNumber) where fit.AirlineName = ?");
				
				stmt2.setString(1, this.getCode());
				ResultSet rs2 = stmt2.executeQuery();
				makeTable(rs2, response.getWriter());
			}
			
			
			
			//makeTable(rs, response.getWriter());

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
		if(request.getParameter("click").equals("Search")) {
			String code = request.getParameter("Code");
			if(code != null) {
				this.setCode(code);
			}
		}
		doGet(request, response);
	}

}
