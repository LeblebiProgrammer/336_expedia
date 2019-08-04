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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class ManageFlights
 */
@WebServlet("/ManageFlights")
public class ManageFlights extends HttpServlet {
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
    			"<title>Manage Flights</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"ManageFlights\" method=\"post\">\n" + 
    	 
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
					if(rsmd.getColumnLabel(i+1).equals("FlightNumber")) {
						out.println("<TD>");
						out.println("<input name=\"click\" type=\"submit\" value=\""+ rs.getString(i+1)  +"\" /> ");
						
					}else {
						out.println("<TD>" + rs.getString(i + 1) + "</TD>");
					}
				}
//				out.println("<input name=\"click\" type=\"submit\" value=\"Book for User\" /> ");
				out.println("</TR>");
			}
			out.println("</TABLE></P>");
			return rowCount;
		}
	    
	
    protected void finishHtml(java.io.PrintWriter out,  ResultSet airline, ResultSet airport, ResultSet airport2, String error) throws SQLException {
    	String str = "<div>\n" + 
    			"		<br> <br> <br> <br>\n" + 
    			"		<p>Add Flight</p>\n" + 
    			"		<table border=\"2\">\n" + 
    			"			<tbody>\n" + 
    			"				<tr>\n" + 
    			"					<td width = \"14%\">Flight Number</td>\n" + 
    			"					<td width = \"14%\">Airline Name</td>\n" + 
    			"					<td width = \"14%\">Departure</td>\n" + 
    			"					<td width = \"14%\">Destination</td>\n" +
    			"					<td width = \"14%\">Departure Time</td>\n" + 
    			"					<td width = \"14%\">Arrival Time</td>\n" + 
    			"				</tr>\n" + 
    			"				<tr>\n" + 
    			"					<td><input type=\"text\" name=\"FlightNumber\" /></td>\n"; 
    	
    	//aircraft
//    	do {
//    		//String option= "<option value = \"" + aircraft.getString("TailNumber") + "\"" + aircraft.getString("TailNumber") + "</option>";\
//    		try {
//    			String option = "<option value = \"" + aircraft.getString("TailNumber") + "\">" + aircraft.getString("TailNumber") + "</option>\n";
//    			str += option;
//    		}
//    		catch(Exception e) {
//    			
//    		}
//    	}while(aircraft.next());
    	

    	//airline
    	str +=	"					</select></td><td width = \"14%\"><select name = \"AirlineName\" size =\"1\">\n\t" ;
    	do {
    		//String option= "<option value = \"" + aircraft.getString("TailNumber") + "\"" + aircraft.getString("TailNumber") + "</option>";\
    		try {
    			String option = "<option value = \"" + airline.getString("Code") +";" +airline.getString("Name") + "\">" + airline.getString("Name") + "</option>\n";
    			str += option;
    		}
    		catch(Exception e) {
    			
    		}
    	}while(airline.next());
    	str += "</select></td>";

    	str += "					<td width = \"14%\"><select name = \"Departure\" size = \"1\">";
    	
    	
    	
    	//departure
    	do {
    		//String option= "<option value = \"" + aircraft.getString("TailNumber") + "\"" + aircraft.getString("TailNumber") + "</option>";\
    		try {
    			String option = "<option value = \"" + airport.getString("AirportCode") + "\">" + airport.getString("AirportName") + "</option>\n";
    			str += option;
    		}
    		catch(Exception e) {
    			System.out.print(e.getMessage());
    		}
    	}while(airport.next());
    	str += "</select></td>";
    	
    	
    	
    	//arrival
    	str += "<td width = \"14%\"><select name = \"Arrival\" size = \"1\">";
    	
		do {
    		//String option= "<option value = \"" + aircraft.getString("TailNumber") + "\"" + aircraft.getString("TailNumber") + "</option>";\
    		try {
    			String option = "<option value = \"" + airport2.getString("AirportCode") + "\">" + airport2.getString("AirportName") + "</option>\n";
    			str += option;
    		}
    		catch(Exception e) {
    			System.out.print(e.getMessage());
    		}
    	}while(airport2.next());
    	str += "</select></td>";
    	
    	
    		
    	str += "					<td width = \"14%\"><input type=\"text\" name=\"DepartureTime\" /></td></td>\n" + 
    			"					<td width = \"14%\"><input type=\"text\" name=\"ArrivalTime\" /></td>\n" +
    			"				</tr>\n" + 
    			"			</tbody>\n" + 
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
	
	
	
    public ManageFlights() {
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
			
			PreparedStatement stmt;	
			
			stmt=con.prepareStatement("Select * from FlightInfoTable");  
			
			//PreparedStatement stmt2 = con.prepareStatement("Select * from AircraftTable");
			PreparedStatement stmt3 = con.prepareStatement("Select * from AirlineTable");
			PreparedStatement stmt4 = con.prepareStatement("Select * from AirportTable");
			PreparedStatement stmt5 = con.prepareStatement("Select * from AirportTable");
			
			ResultSet rs = stmt.executeQuery();
			//ResultSet rs2 = stmt2.executeQuery();
			ResultSet rs3 = stmt3.executeQuery();
			ResultSet rs4 = stmt4.executeQuery();
			ResultSet rs5 = stmt5.executeQuery();
			
			
			initialHtml(response.getWriter());
			makeTable(rs, response.getWriter());

			String _error = "";
			
			if(this.getError() != null) {
				_error = this.getError();
			}
			else {
				_error = "";
			}
			finishHtml(response.getWriter(), rs3, rs4, rs5, _error);
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
			
			String airlineCombo = request.getParameter("AirlineName");
			String airlineName = airlineCombo.substring(airlineCombo.indexOf(';')+1);
			String code = airlineCombo.substring(0, airlineCombo.indexOf(';'));
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
			
			if(airlineName == null) {
				_error += "Airline name cannot be empty. ";
			}else {
				if(airlineName.length() == 0) {
					_error += "Airline name cannot be empty. ";
				}
				else {
					if(flightNumber.length() > 0) {
						flightNumber = code + flightNumber;
					}
				}
			}
			if(departure == null) {
				_error += "Departure name cannot be empty. ";
			}else {
				if(departure.length() != 3) {
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
						if(_tdemp.length() == 4) {
							_tdemp = "0"+_tdemp;
						}
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
					PreparedStatement stmt=con.prepareStatement("Insert into FlightInfoTable (FlightNumber, AirlineName, DepartureCity, DestinationCity, DepartureTime, ArrivalTime) Values (?, ?, ?, ?, ?, ?)");  
					
					stmt.setString(1, flightNumber);
					
					stmt.setString(2, airlineName);
					stmt.setString(3, departure);
					stmt.setString(4, arrival);
					stmt.setTime(5, departureTime);
					stmt.setTime(6, arrivalTime);
					
					stmt.executeUpdate();
					con.close();
					
					request.setAttribute("flightNumber", flightNumber);
//					RequestDispatcher dispatcher = request.getRequestDispatcher("EditFlight");
//					dispatcher.forward(request, response);
//					return;
					
				}catch(Exception e) {
					_error += e.getMessage();
					System.out.println(e.getMessage());
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
		if(request.getParameter("click") != null) {
			String str = request.getParameter("click");
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("EditFlight");
			request.setAttribute("_flight", str);
			dispatcher.forward(request, response);
			return;
			
		}
		
		
		doGet(request, response);
	}

}
