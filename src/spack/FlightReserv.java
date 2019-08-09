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
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class FlightSearch
 */
@WebServlet("/FlightReserv")
public class FlightReserv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private String error = "";
	
	private String _departDateCache = "";
	private String _returnDateCache = "";
	
	private String _departCityCache = "";
	private String _returnCityCache = "";
	private String _tripTypeCache = "";
	private String _orderBy = "";
	private String _selectedDays = "";
	private Boolean _isPostback = false;
	private Boolean _showFlexDates = false;
	private Boolean _selectFlexDate = false;
	
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
    			"<title>Reserv Flight</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"FlightReserv\" method=\"post\">\n" +
    			"		<p><a href=\"Main\">&lt;Back</a>\n<br/><br/><br/>";
    	
       			
    	 
    	out.print(str);
    }
  
	
	protected void formEnd(java.io.PrintWriter out) {
		
		String str = "	</form>\n" + 
		    			"<p>"+ error + "</p>"+
		    			"</body>\n" + 
		    			"</html>";
		
		out.write(str);
		
	}
	
	
	
    public FlightReserv() {
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

			
			initialHtml(response.getWriter());
			
			
			String departFlightInfo = request.getParameter("depart_seat");
			String returnFlightInfo = request.getParameter("return_seat");
			boolean continueReserv = true;
			boolean noReturnFlight = false;
			
			String departSeatType = "";
			String departFlightNumber = "";
			String departDate = "";
			int departSeatCost = 0; 
			int departFreeSpace = 0;
			
			String returnSeatType = "";
			String returnFlightNumber = "";
			String returnDate = "";
			int returnSeatCost = 0;
			int returnFreeSpace = 0;
			
			
			
			
			if (departFlightInfo == null) {
				error = "please select depart flight!";
				continueReserv = false;
			} else {
				
				String[] departWords = departFlightInfo.split("_");
				if (departWords.length < 5) {
					error = "incorrect flight selection";
					continueReserv = false;
				} else {
					departSeatType = departWords[0];
					departFlightNumber = departWords[2];
					departDate = departWords[3];
					departSeatCost = Integer.parseInt(departWords[4]);
					
					departFreeSpace = calculateFreeSeat(departSeatType,departFlightNumber, departDate, con);
					
					response.getWriter().print("<table>");
					
					response.getWriter().print("<tr><td width=150> Departure Date : </td><td width=100 align=right>" + departDate + "</td></tr>");
					response.getWriter().print("<tr><td width=150> Departure Flight # :</td><td width=100 align=right>" + departFlightNumber + "</td></tr>");
					response.getWriter().print("<tr><td width=150> Departure Seat Type :</td><td width=100 align=right>" + departSeatType + "</td></tr>");
					response.getWriter().print("<tr><td width=150> Departure Seat Cost :</td><td width=100 align=right>" + String.format("%,d", departSeatCost) + "</td></tr>");
					
					//response.getWriter().print("<tr><td width=150> Seats Available :</td><td width=100 align=right>" + departFreeSpace + "</td></tr>");
					
					response.getWriter().print("</table>");
					
					if (departFreeSpace < 1) {
						departSeatType = "WaitList";
					}
					
					response.getWriter().print(ReserveFlight(departDate,departFlightNumber,departSeatType,departSeatCost, con, response, request, "Departing Flight"));
					response.getWriter().print("<br/><br/><br/>");
					
				}
				
				
				//response.getWriter().print(departFlightInfo);
			}
			
			if (continueReserv) {
				response.getWriter().print("<br/>");
				//response.getWriter().print(returnFlightInfo);
				
				
				String[] returnWords = returnFlightInfo.split("_");
				if (returnWords.length < 5) {
					error = "incorrect flight selection";
					continueReserv = false;
				} else {
					returnSeatType = returnWords[0];
					returnFlightNumber = returnWords[2];
					returnDate = returnWords[3];
					returnSeatCost = Integer.parseInt(returnWords[4]);
					
					returnFreeSpace = calculateFreeSeat(returnSeatType,returnFlightNumber, returnDate, con);
					
					response.getWriter().print("<table>");
					
					response.getWriter().print("<tr><td width=150> Return Date : </td><td>" + returnDate + "</td></tr>");
					response.getWriter().print("<tr><td width=150> Return Flight # :</td><td>" + returnFlightNumber + "</td></tr>");
					response.getWriter().print("<tr><td width=150> Return Seat Type :</td><td>" + returnSeatType + "</td></tr>");
					response.getWriter().print("<tr><td width=150> Return Seat Cost :</td><td>" + String.format("%,d", returnSeatCost) + "</td></tr>");
					
					//response.getWriter().print("<tr><td width=150> Seats Available :</td><td>" + returnFreeSpace + "</td></tr>");
					
					response.getWriter().print("</table>");
					
					if (returnFreeSpace < 1) {
						returnSeatType = "WaitList";
					}
					
					response.getWriter().print(ReserveFlight(returnDate,returnFlightNumber,returnSeatType,returnSeatCost, con, response, request, "Return Flight" ));
					response.getWriter().print("<br/><br/><br/>");
					
				}
				
				
			}
			formEnd(response.getWriter());
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private String ReserveFlight(String flightDate, String flightNumber, String seatType, int cost,  Connection con, HttpServletResponse response, HttpServletRequest request, String FlightDescription) { 
	
		String result = "Your " + FlightDescription  + " is booked";
		try {
			
			
			
			String code = randomAlphaNumeric(5);
			
			int type = 0;
			
			if (seatType.contains("coach")) {
				type = 4;
			}
			if (seatType.contains("business")) {
				type = 5;
				
			}
			if (seatType.contains("first")) {
				type = 6;
				
			}
			if (seatType.contains("WaitList")) {
				type = 7;
				result = "Your "  + FlightDescription  +  " is booked as <b>WaitListed</b>";
				
			}
			
			
			
			PreparedStatement stmt;

			stmt = con.prepareStatement(
					"Insert into ReservationTable (ReservationNumber, UserID, ReservationDate, StatusID) values(?,?,?,?)");
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatOut = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			
			java.util.Date date = dateFormat.parse(flightDate);
			java.sql.Date _date = new java.sql.Date(date.getTime());
			
			
			int userID = 0;
			
			HttpSession _session = request.getSession();
			
			User _user = (User) _session.getAttribute("username");
			if(_user != null) {

				userID = _user.getID();
			}
			

			stmt.setString(1, code);
			stmt.setInt(2, userID);
			stmt.setDate(3, _date);
			stmt.setInt(4, type);

			boolean b = stmt.execute();
			if(b == true) {
				
			}

			PreparedStatement stmt2 = con.prepareStatement(
					"Insert into ReservationsFlightsTable (ReservationNumber, FlightsID, StatusID, BookedPrice) values(?,?,?,?)");

			if (type != 7) {

			}
			stmt2.setString(1, code);
			stmt2.setInt(2, getFlightID(flightNumber, flightDate, con));
			stmt2.setInt(3, type);
			stmt2.setFloat(4, cost);

			stmt2.executeUpdate();
			result = result + "<br/>" + "Your reservation number is " + code;
			
		} catch (Exception ex) {
			result = "Error happened " + ex.getMessage();
		}
			
		return result;
		
		
	}
		
	public static String randomAlphaNumeric(int count) {
		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	
	
	private int calculateFreeSeat(String seatType, String flightNumber, String flightDate, Connection con) { 
		int result = 0; 
		
			
		try {
			
			int WeekDay = getWeekDay(flightDate);
			
			String capacitySQL = "SELECT AFT.EconomyCount, AFT.BusinessCount, AFT.FirstCount FROM RuExpedia.FlightsTable FT\n" + 
					"    INNER JOIN RuExpedia.AircraftTable AFT ON (AFT.AircraftID = FT.AircraftID)\n" + 
					"WHERE FT.FlightNumber = '"+ flightNumber + "' AND FT.Day = " + WeekDay; 
					
						
			PreparedStatement capacityStmt = con.prepareStatement(capacitySQL);
			ResultSet rsCapacity = capacityStmt.executeQuery();
			
			int coachCapacity = 0;
			int businessCapacity = 0;
			int firstCapacity = 0;
			int coachReservations = 0;
			int businessReservations = 0;
			int firstReservations = 0;
			int waitListReservations = 0;
		
			if(rsCapacity.next()) {
				
				coachCapacity = Integer.parseInt(rsCapacity.getString("EconomyCount")) ;
				businessCapacity = Integer.parseInt(rsCapacity.getString("BusinessCount")) ;
				firstCapacity = Integer.parseInt(rsCapacity.getString("FirstCount")) ;
				
				
	    	}
			
			
			
			
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat dateFormatOut = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			
			java.util.Date date = dateFormat.parse(flightDate);
			String sqlDate = dateFormatOut.format(date); 
			
			
			String reservationsSQL = "SELECT COUNT(FT.FlightNumber) as cnt, RT.StatusID\n" + 
											" FROM RuExpedia.ReservationTable RT\n" + 
											"	INNER JOIN RuExpedia.ReservationsFlightsTable RFT ON (RFT.ReservationNumber = RT.ReservationNumber)\n" + 
											"    INNER JOIN RuExpedia.FlightsTable FT ON (FT.FlightsID = RFT.FlightsID)\n" + 
											"WHERE ReservationDate = '" + sqlDate + "' AND FT.FlightNumber = '" + flightNumber + "'\n" + 
											"GROUP BY RT.StatusID" ; 
					
			PreparedStatement reservStmt = con.prepareStatement(reservationsSQL);
			ResultSet rsReserv = reservStmt.executeQuery();
			
			while(rsReserv.next()) {
				
				if (rsReserv.getString("StatusID").equals("4")) {
					coachReservations = Integer.parseInt(rsReserv.getString("cnt"));
				}
				if (rsReserv.getString("StatusID").equals("5")) {
					businessReservations = Integer.parseInt(rsReserv.getString("cnt"));
				}
				if (rsReserv.getString("StatusID").equals("6")) {
					firstReservations = Integer.parseInt(rsReserv.getString("cnt"));
				}
				if (rsReserv.getString("StatusID").equals("7")) {
					waitListReservations = Integer.parseInt(rsReserv.getString("cnt"));
				}
				
				
	    	}
			
			if (seatType.equals("coach")) {
				result = coachCapacity - coachReservations;
			}
			
			if (seatType.equals("business")) {
				result = businessCapacity - businessReservations;
			}
			
			if (seatType.equals("first")) {
				result = firstCapacity - firstReservations;
			}
			

			//con.close();
		} catch (Exception ex) {
			try {
				//response.getWriter().print(ex.getMessage());
			} catch (Exception e) {
				
			}
		}
		
	
		
		return result;
		
	}
	
	

	private int getFlightID(String flightNumber, String flightDate, Connection con) { 
		int flID = 0; 
		
			
		try {
			
			int WeekDay = getWeekDay(flightDate);
			
			String capacitySQL = "SELECT FlightsID FROM RuExpedia.FlightsTable \n" + 
					"WHERE FlightNumber = '"+ flightNumber + "' AND Day=  " + WeekDay; 
					
						
			PreparedStatement capacityStmt = con.prepareStatement(capacitySQL);
			ResultSet rsCapacity = capacityStmt.executeQuery();
			
			if(rsCapacity.next()) {
				
				flID = Integer.parseInt(rsCapacity.getString("FlightsID")) ;
				
	    	}
			


			//con.close();
		} catch (Exception ex) {
			try {
				//response.getWriter().print(ex.getMessage());
			} catch (Exception e) {
				
			}
		}
		
	
		
		return flID;
		
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
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
			
		
		doGet(request, response);
	}

}
