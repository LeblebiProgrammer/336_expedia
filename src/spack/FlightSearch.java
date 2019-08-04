package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class FlightSearch
 */
@WebServlet("/FlightSearch")
public class FlightSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private String error;
	
	private void setError(String val) {
		error = val;
	}
	private String getError() {
		return error;
	}
	
	protected void initialHtml(java.io.PrintWriter out) {
    	String str = "\n" + 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>Search Flight</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"ManageFlights\" method=\"post\">\n" ;
    	 
    	out.print(str);
    }
//	private int makeTable(java.sql.ResultSet rs, java.io.PrintWriter out)
//	    	    throws Exception {
//			 int rowCount = 0;
//			
//			 out.println("<P ALIGN='center'><TABLE BORDER=1>");
//			 ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
//			 int columnCount = rsmd.getColumnCount();
//			 // table header
//			 out.println("<TR>");
//	    	 for (int i = 0; i < columnCount; i++) {
//	    		 out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
//			 }
//	    	 out.println("</TR>");
//			 // the data
//			while (rs.next()) {
//				rowCount++;
//				out.println("<TR>");
//				for (int i = 0; i < columnCount; i++) {
//					if(rsmd.getColumnLabel(i+1).equals("AirportCode")) {
//						out.println("<TD>");
//						out.println("<input name=\"click\" type=\"submit\" value=\""+ rs.getString(i+1)  +"\" /> ");
//						
//					}else {
//						out.println("<TD>" + rs.getString(i + 1) + "</TD>");
//					}
//				}
////				out.println("<input name=\"click\" type=\"submit\" value=\"Book for User\" /> ");
//				out.println("</TR>");
//			}
//			out.println("</TABLE></P>");
//			return rowCount;
//		}
//	    
	
    protected void displayResult(java.io.PrintWriter out, ResultSet results, String segmentType, String error) throws SQLException {
    	String str = "<div>\n" + 
    			"		<br> <br> <br> <br>\n" + 
    			"		<p>Add Flight</p>\n" + 
    			"		<table border=\"2\">\n" + 
    			"			<tbody>\n" + 
    			"				<tr>\n" + 
    			"					<td>Flight Number</td>\n" + 
    			"					<td width = \"15%\">Aircraft</td>\n" +
    			"					<td>Airline Name</td>\n" + 
    			"					<td>Departure</td>\n" + 
    			"					<td>Arrival</td>\n" +
    			"					<td>Coach Class</td>\n" + 
    			"					<td>Business Class</td>\n" +
    			"					<td>First Class</td>\n" +
    			"				</tr>\n" ;
    			
    	while(results.next()) {
    		str += "<tr>\n"; 
    		str += "<td>" + results.getString("FlightNumber") + "</td>\n";
    		str += "<td>" + results.getString("Model") + "</td>\n";
    		str += "<td>" + results.getString("AirlineName") + "</td>\n";
    		str += "<td>" + results.getString("DepartureTime") + "</td>\n";
    		str += "<td>" + results.getString("ArrivalTime") + "</td>\n";
    		str += "<td>" + results.getString("PublishedEconomyPrice") +  "<input type=\"radio\"  name=\"departSeat\" value=\"coach_" + segmentType + "_" + results.getString("FlightNumber")   +  "\"></td>\n";
    		str += "<td>" + results.getString("PublishedBusinessPrice") + "<input type=\"radio\"  name=\"departSeat\" value=\"business_" + segmentType + "_" + results.getString("FlightNumber")   +  "\"></td>\n";
    		str += "<td>" + results.getString("PublishedFirstPrice") + "<input type=\"radio\"  name=\"departSeat\" value=\"first_" + segmentType + "_" + results.getString("FlightNumber")   +  "\"></td>\n";
    		str += "</tr>\n";
    	}
//		ResultSetMetaData rsmd = (ResultSetMetaData) aircraft.getMetaData();
//		int columnCount = rsmd.getColumnCount();
    	
    	str +=  "			</tbody>\n" + 
    			"		</table>\n" + 
    			"		\n" + 
    			"		<input type=\"submit\" name = \"click\" value=\"Add Flight\" />\n" + 
    			"	</div>\n" + 
    			"	</form>\n" + 
    			"<p>"+ error + "</p>"+
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }
	
	
    private int getWeekDay(String _dateEntered) {
    	int result = 0;
    	
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date;
		try {
		    date = dateFormat.parse(_dateEntered);
		    
		    Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        result = calendar.get(Calendar.DAY_OF_WEEK);
		    
		} 
		catch (ParseException e) {
		    e.printStackTrace();
		}
    	
    	return result;
    	
    }
    
	
    public FlightSearch() {
        super();
        // TODO Auto-generated constructor stub
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
			
			
			
			//String _departDate = "08/02/2019"; //request.getParameter("date");
			//String _returnDate = "08/09/2019"; //request.getParameter("returndate");
			
			
			String _departDate = request.getParameter("fromDate").toString();
			String _returnDate = request.getParameter("returnDate").toString();
			
			
			int departWeekDay = getWeekDay(_departDate);
			int returnWeekDay = getWeekDay(_returnDate);
			
			
//			String departCity = request.getParameter("origin").toString();
//			String returnCity = request.getParameter("destination").toString();
		
			
			String departCity = "JFK"; //request.getParameter("origin").toString();
			String returnCity = "LAX"; //request.getParameter("destination").toString();
			
			
			
			
			
			
			String departSQL = "SELECT FT.FlightsID, FT.FlightNumber, FIT.AirlineName,  FT.AircraftID, CT.Model, \n" + 
					"		FT.PublishedEconomyPrice, FT.PublishedBusinessPrice, \n" + 
					"        FT.PublishedFirstPrice , FIT.DepartureTime, FIT.ArrivalTime\n" + 
					"        FROM RuExpedia.FlightsTable FT\n" + 
					"        JOIN RuExpedia.FlightInfoTable FIT ON (FIT.FlightNumber = FT.FlightNumber) \n" + 
					"        JOIN RuExpedia.AircraftTable CT ON (CT.AircraftID = FT.AircraftID) \n" + 
					"        WHERE FT.Day = " + departWeekDay + " AND FIT.DepartureCity = '" + departCity + "' AND FIT.DestinationCity = '" + returnCity + "'";
			

			String returnSQL = "SELECT FT.FlightsID, FT.FlightNumber, FIT.AirlineName, FT.AircraftID, CT.Model, \n" + 
					"		FT.PublishedEconomyPrice, FT.PublishedBusinessPrice, \n" + 
					"        FT.PublishedFirstPrice , FIT.DepartureTime, FIT.ArrivalTime\n" + 
					"        FROM RuExpedia.FlightsTable FT\n" + 
					"        JOIN RuExpedia.FlightInfoTable FIT ON (FIT.FlightNumber = FT.FlightNumber) \n" + 
					"        JOIN RuExpedia.AircraftTable CT ON (CT.AircraftID = FT.AircraftID) \n" +
					"        WHERE FT.Day = " + returnWeekDay + " AND FIT.DepartureCity = '" + returnCity + "' AND FIT.DestinationCity = '" + departCity + "'";

			
			PreparedStatement departStmt = con.prepareStatement(departSQL);
			PreparedStatement returnStmt = con.prepareStatement(returnSQL);
			
//			PreparedStatement stmt2 = con.prepareStatement("Select * from AircraftTable");
//			PreparedStatement stmt3 = con.prepareStatement("Select * from AirlineTable");
//			PreparedStatement stmt4 = con.prepareStatement("Select * from AirportTable");
			
			
			ResultSet rsDepart = departStmt.executeQuery();
			ResultSet rsReturn = returnStmt.executeQuery();
//			ResultSet rs2 = stmt2.executeQuery();
//			ResultSet rs3 = stmt3.executeQuery();
//			ResultSet rs4 = stmt4.executeQuery();
			
			
			
			initialHtml(response.getWriter());
//			makeTable(rsDepart, response.getWriter());
//			
//			makeTable(rsReturn, response.getWriter());

			String _error = "";
			
			if(this.getError() != null) {
				_error = this.getError();
			}
			else {
				_error = "";
			}
			displayResult(response.getWriter(), rsDepart, "depart", _error);
			displayResult(response.getWriter(), rsReturn, "return", _error);
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
		
		if(request.getParameter("click").equals("Add Flight")) {
			String flightNumber = request.getParameter("FlightNumber");
			String tailNumber = request.getParameter("TailNumber");
			String airlineName = request.getParameter("AirlineName");
			String departure = request.getParameter("Departure");
			String arrival = request.getParameter("Arrival");
			Time departureTime = new Time(0);
			
			String _tdemp = request.getParameter("DepartureTime");
			Time arrivalTime = new Time(0);
			String _tarr = request.getParameter("ArrivalTime");
		
			this.setError("");
			String _error = "";
			
			if(flightNumber == null) {
				_error = "Flight number cannot be empty. ";
			}else {
				if(flightNumber.length() == 0 ) {
					_error = "Flight number cannot be empty. ";
				}
			}
			if(tailNumber == null) {
				_error += "Tail number cannot be empty. ";
			}else {
				if(tailNumber.length() == 0) {
					_error += "Tail number cannot be empty. ";
				}
			}
			if(airlineName == null) {
				_error += "Airline name cannot be empty. ";
			}else {
				if(airlineName.length() == 0) {
					_error += "Airline name cannot be empty. ";
				}
			}
			if(departure == null) {
				_error += "Departure name cannot be empty. ";
			}else {
				if(departure.length() != 0) {
					_error += "Departure name must have 3 characters. ";
				}
			}
			if(arrival == null) {
				_error += "Arrival name cannot be empty. ";
			}else {
				if(arrival.length() != 3) {
					_error += "Arrival name must have 3 characters. ";
				}
			}
			if(_tdemp == null) {
				_error += "Departure time cannot be empty. ";
			}else {
				if(_tdemp.length() == 0) {
					_error += "Departure time cannot be empty. ";
				}else {
					try {
				        LocalTime.parse(_tdemp);
				        //System.out.println("Valid time string: " + _tdemp);
				        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
				        departureTime =  new java.sql.Time(dateFormat.parse(_tdemp).getTime());
				        
				    } catch (Exception e) {
				    	_error += "Invalid time for departure time";
				    }
				}
			}
			if(_tarr == null) {
				_error += "Arrival time cannot be empty";
			}else {
				if(_tarr.length() == 0) {
					_error += "Arrival time cannot be empty";
				}else {
//					try {
//						DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//						dateFormat.parse(arrivalTime);
//					
//					}catch(Exception e) {
//						_error += e.getMessage();
//					}
					try {
				        LocalTime.parse(_tarr);
				        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
				        
				        arrivalTime = new java.sql.Time(dateFormat.parse(_tarr).getTime());
				    } catch (Exception e) {
				        //System.out.println("Invalid time string: " + arrivalTime);
				    	_error += "Invalid time for arrival time";
				    }
				}
			}if(_error.length() == 0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Insert into FlightInfoTable (FlightNumber, TailNumber, AirlineName, DepartureCity, DesinationCity, DepartureTime, ArrivalTime) Values (?, ?, ?, ?, ?, ?, ?)");  
					
					stmt.setString(1, flightNumber);
					stmt.setString(2, tailNumber);
					stmt.setString(3, airlineName);
					stmt.setString(4, departure);
					stmt.setString(5, arrival);
					stmt.setTime(6, departureTime);
					stmt.setTime(7, arrivalTime);
					
					
				}catch(Exception e) {
					_error += e.getMessage();
				}
			}
			
			if(_error.length() > 0) {
				this.setError(_error);
			}
		}
		
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
			
		}
		if(request.getParameter("click").equals("ManageAirlines")) {
			response.sendRedirect("ManageAirlines");
			return;
		}
		if(request.getParameter("click").equals("ManageAirports")) {
			//return;
			response.sendRedirect("ManageAirports");
			return;
		}
		if(request.getParameter("click").equals("ManageAirplanes")) {
			response.sendRedirect("ManageAirplanes");
			return;
		}
		if(request.getParameter("click").equals("GetWaitlist")) {
			response.sendRedirect("GetWaitlist");
			return;
		}
		
		
		doGet(request, response);
	}

}
