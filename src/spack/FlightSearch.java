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
    			"<title>Search Flight</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"FlightSearch\" method=\"post\">\n" +
    			"		<p><a href=\"Main\">&lt;Back</a>\n<br/><br/><br/>";
    	
    	
    	if (_showFlexDates) {
    		str += "<input name=\"showFlex\" type=submit value=\"Show Exact Dates\"/> ";
    	} else {
    		if (_selectFlexDate) {
    			
    		} else {
    			str += "<input name=\"showFlex\" type=submit value=\"Show Flex Dates\"/> ";	
    		}
    		
    	}
    			
    			
    			
    	
    	//"		<input type=hidden name=\"fromDate\" value=\"\" />" ;
    			
    	 
    	out.print(str);
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

			
			try {
				if (request.getParameter("click").equals("Search")) {
					_isPostback = false;
				}
			} catch (Exception ex) {
				
			}

			try {
				if (request.getParameter("selectFlex").equals("Select Flight")) {
					_isPostback = true;
					_showFlexDates = false;
					_selectFlexDate = true;
				} 
				
			} catch (Exception ex) {
				
			}
			
			
			if (!_isPostback ) {
				_departDateCache = request.getParameter("fromDate").toString();
				_returnDateCache = request.getParameter("returnDate").toString();
				_departCityCache = request.getParameter("origin").toString();
				_returnCityCache = request.getParameter("destination").toString();
				_tripTypeCache = request.getParameter("tripType");
				_selectedDays = "";
				_selectFlexDate = false;
			}
			
			if (_showFlexDates) {
				RunFlexSearchResult(con, response);
			} else if (_selectFlexDate) {
				
				RunSearchResult(con, response, _selectedDays); 
			} else {  
				RunSearchResult(con, response, "");
			}
			
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private void RunFlexSearchResult(Connection con, HttpServletResponse response) {
		
		
		try {
			int departWeekDay = getWeekDay(_departDateCache);
			int returnWeekDay = getWeekDay(_returnDateCache);
			
			
			
			initialHtml(response.getWriter());
			
			String _error = "";
			
			if(this.getError() != null) {
				_error = this.getError();
			}
			else {
				_error = "";
			}
			
			String departSQL = "SELECT 	MIN(FT.PublishedEconomyPrice) as minPrice , FT.Day\n" + 
					"			FROM RuExpedia.FlightsTable FT\n" + 
					"			INNER JOIN RuExpedia.FlightInfoTable FIT ON (FIT.FlightNumber = FT.FlightNumber)\n" + 
					"			INNER JOIN RuExpedia.AircraftTable CT ON (CT.AircraftID = FT.AircraftID) \n" + 
					"			WHERE  FIT.DepartureCity = '" + _departCityCache + "' AND FIT.DestinationCity = '" + _returnCityCache + "'\n" + 
					"           GROUP BY FT.Day" ; 
					
			PreparedStatement departStmt = con.prepareStatement(departSQL);
			ResultSet rsDepart = departStmt.executeQuery();
			
			int departNumbers[]=new int[7];  
			for(int i=0;i<departNumbers.length -1;i++) {
				departNumbers[i] = -1;
			}
			while(rsDepart.next()) {
				
				int iIndex = Integer.parseInt(rsDepart.getString("Day")) - 1;
				departNumbers[iIndex] = Integer.parseInt(rsDepart.getString("minPrice"));
	    		
	    	}
			
			String returnSQL = "SELECT 	MIN(FT.PublishedEconomyPrice) as minPrice , FT.Day\n" + 
					"			FROM RuExpedia.FlightsTable FT\n" + 
					"			INNER JOIN RuExpedia.FlightInfoTable FIT ON (FIT.FlightNumber = FT.FlightNumber)\n" + 
					"			INNER JOIN RuExpedia.AircraftTable CT ON (CT.AircraftID = FT.AircraftID) \n" + 
					"			WHERE  FIT.DestinationCity = '" + _departCityCache + "' AND FIT.DepartureCity = '" + _returnCityCache + "'\n" + 
					"           GROUP BY FT.Day" ; 
					
			PreparedStatement returnStmt = con.prepareStatement(returnSQL);
			ResultSet rsReturn = returnStmt.executeQuery();
			
			int returnNumbers[]=new int[7];  
			for(int i=0;i<returnNumbers.length -1;i++) {
				returnNumbers[i] = -1;
			}
			while(rsReturn.next()) {
				
				int iIndex = Integer.parseInt(rsReturn.getString("Day")) - 1;
				returnNumbers[iIndex] = Integer.parseInt(rsReturn.getString("minPrice"));
	    		
	    	}
			
			
			String[] departDates = new String[7];
			int iDate = 0;
			for(int i=-3;i<4;i++) {  // find departDates +-3 and selected date
			
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat dateFormatOut = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
				java.util.Date date;
				try {
				    date = dateFormat.parse(_departDateCache);
				    Calendar calendar = Calendar.getInstance();
			        calendar.setTime(date);
			        calendar.add(Calendar.DAY_OF_MONTH, i-1);
			        departDates[iDate] = dateFormatOut.format(calendar.getTime());  

				    
				} 
				catch (ParseException e) {
				    e.printStackTrace();
				}
				iDate ++;
			
			}
			
			String[] returnDates = new String[7];
			iDate = 0;
			for(int i=-3;i<4;i++) {  // find departDates +-3 and selected date
			
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat dateFormatOut = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
				java.util.Date date;
				try {
				    date = dateFormat.parse(_returnDateCache);
				    Calendar calendar = Calendar.getInstance();
			        calendar.setTime(date);
			        calendar.add(Calendar.DAY_OF_MONTH, i-1);
			        returnDates[iDate] = dateFormatOut.format(calendar.getTime());  
				    
				} 
				catch (ParseException e) {
				    e.printStackTrace();
				}
				iDate ++;
			
			}
			
			
			String str = "<div>\n" + 
	    			"		<br> <br> <br> <br>\n" + 
	    			"		<table border=\"2\">\n" + 
	    			"			<tbody>\n" + 
	    			"				<tr>\n" + 
	    			"					<td width=\"100\">&nbsp;</td>\n" ;
			
			for(int i=0;i<departDates.length ;i++) {
			
	    			str += "<td width = \"100\">" +  departDates[i] +   "</td>\n" ;
			}
			str += "</tr>";
			
			for(Integer iX=0;iX<returnDates.length ;iX++) {
				str += "<tr>";	
    			str += "<td width = \"100\">" +  returnDates[iX] +   "</td>\n" ;
    			
    			for(Integer iY=0;iY<7 ;iY++) {
    				
    				int FlightTotal = -1 ;
    				String dayTotal = "&nbsp;";
    				if ((departNumbers[iX] > -1) && (returnNumbers[iX] > -1)) {
    					FlightTotal = departNumbers[iX] + returnNumbers[iX];
    					dayTotal = String.format("%,d", FlightTotal);
    				}
    				
    				String SelectedDay = "";
    				if ((iX == 3) && (iY == 3)) {
    					SelectedDay = "checked";
    				}
    				if (FlightTotal > 0) {
    						str += "<td width = \"100\" align=center> "  + "<input type=\"radio\"  name=\"selectedDay\" " + SelectedDay + " value=\""  + departDates[iY] +  "," + departNumbers[iY] + "|" + returnDates[iX] + "," + returnNumbers[iX] + "\"/> $" +  dayTotal + "</td>\n" ;	
    				} else {
    						str += "<td width = \"100\" align=center> "  + " N/A</td>\n" ;
    				}
    					
    			}
    			
    			str += "</tr>";
		}
			
			
		str += "</table><br/><br/>";
		str += "<input name=\"selectFlex\" type=submit value=\"Select Flight\"/> ";
		response.getWriter().print(str);
			
			
			

			con.close();
		} catch (Exception ex) {
			try {
				response.getWriter().print(ex.getMessage());
			} catch (Exception e) {
				
			}
		}
		
		
	}
	
	
	
	private void RunSearchResult(Connection con, HttpServletResponse response, String selectedDays) {
		
		
		try {
			int departWeekDay = getWeekDay(_departDateCache);
			int returnWeekDay = getWeekDay(_returnDateCache);
			String departSelect = "";
			String returnSelect = "";
			
			
			String departDaySelected = "";
			String departDayPrice = "";
			String returnDaySelected = "";
			String returnDayPrice = "";
			
			
			
			if (!selectedDays.equals("")) {  // this is something like          14 Aug,154|20 Aug,181
				
				if (selectedDays.indexOf('|') > 0) {
					String[] selectionParts = selectedDays.split("\\|");
					if (selectionParts.length == 2) {
						departSelect = selectionParts[0];
						returnSelect = selectionParts[1];
						
						String departWords[] = departSelect.split(",");
						String returnWords[] = returnSelect.split(",");
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						DateFormat dateFormatOut = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
						
						
						if (departWords.length == 2) {  // redo the search the selected day with flex search selected date
							departDaySelected = departWords[0] + " 2019";
							departDayPrice = departWords[1];
							java.util.Date date = dateFormatOut.parse(departDaySelected);
							_departDateCache = dateFormat.format(date);  
							departWeekDay = getWeekDay(_departDateCache);
							
							
						}
						
						if (returnWords.length == 2) { // redo the search the selected day with flex search selected date
							returnDaySelected = returnWords[0] + " 2019";
							returnDayPrice = returnWords[1];
							java.util.Date date = dateFormatOut.parse(returnDaySelected);
							_returnDateCache = dateFormat.format(date);
							returnWeekDay = getWeekDay(_returnDateCache);
						}
						
						
					}
				}
			}
			
			
			initialHtml(response.getWriter());
			
			String _error = "";
			
			if(this.getError() != null) {
				_error = this.getError();
			}
			else {
				_error = "";
			}
			
			String departSQL = "SELECT FT.FlightsID, FT.FlightNumber, FIT.AirlineName,  FT.AircraftID, CT.Model, \n" + 
					"		FT.PublishedEconomyPrice, FT.PublishedBusinessPrice, \n" + 
					"        FT.PublishedFirstPrice , FIT.DepartureTime, FIT.ArrivalTime\n" + 
					"        FROM RuExpedia.FlightsTable FT\n" + 
					"        INNER JOIN RuExpedia.FlightInfoTable FIT ON (FIT.FlightNumber = FT.FlightNumber) \n" + 
					"        INNER JOIN RuExpedia.AircraftTable CT ON (CT.AircraftID = FT.AircraftID) \n" + 
					"        WHERE FT.Day = " + departWeekDay + " AND FIT.DepartureCity = '" + _departCityCache + "' AND FIT.DestinationCity = '" + _returnCityCache + "' \n" +
					_orderBy;
			
			System.out.print("SQL1:" + departSQL);
			
			PreparedStatement departStmt = con.prepareStatement(departSQL);
			ResultSet rsDepart = departStmt.executeQuery();
			displayResult("Departing Flights", _departDateCache, _departCityCache, _returnCityCache,  response.getWriter(), rsDepart, "depart", _error, departDayPrice);
			
			if (_tripTypeCache.equals("round_trip")) {
				
				String returnSQL = "SELECT FT.FlightsID, FT.FlightNumber, FIT.AirlineName, FT.AircraftID, CT.Model, \n" + 
						"		FT.PublishedEconomyPrice, FT.PublishedBusinessPrice, \n" + 
						"        FT.PublishedFirstPrice , FIT.DepartureTime, FIT.ArrivalTime\n" + 
						"        FROM RuExpedia.FlightsTable FT\n" + 
						"        INNER JOIN RuExpedia.FlightInfoTable FIT ON (FIT.FlightNumber = FT.FlightNumber) \n" + 
						"        INNER JOIN RuExpedia.AircraftTable CT ON (CT.AircraftID = FT.AircraftID) \n" +
						"        WHERE FT.Day = " + returnWeekDay + " AND FIT.DepartureCity = '" + _returnCityCache + "' AND FIT.DestinationCity = '" + _departCityCache + "'\n" + 
						_orderBy;

				System.out.print("SQL2:" + departSQL);
				
				PreparedStatement returnStmt = con.prepareStatement(returnSQL);
				ResultSet rsReturn = returnStmt.executeQuery();
				displayResult("Return Flights", _returnDateCache, _returnCityCache, _departCityCache, response.getWriter(), rsReturn, "return", _error, returnDayPrice);
				
			}
			

			con.close();
		} catch (Exception ex) {
			
		}
		
		
	}
	
	
	protected void displayResult(String Caption, String FlightDate, String FlightFrom, String FlightTo, java.io.PrintWriter out, ResultSet results, String segmentType, String error, String selectedPrice) throws SQLException {
    	
		
		boolean selectionMade = false; 
		String str = "<div>\n" + 
    			"		<br> <br> <br> <br>\n" + 
    			"		<p>" + Caption + " " + FlightDate + " From:" + FlightFrom + " To:" + FlightTo +  "</p>\n" + 
    			"		<table border=\"2\">\n" + 
    			"			<tbody>\n" + 
    			"				<tr>\n" + 
    			"					<td>Flight Number</td>\n" + 
    			"					<td width = \"15%\">Aircraft</td>\n" +
    			"					<td>Airline Name</td>\n" + 
    			"					<td>Departure<br><input name=\"orderDep\" type=submit value=\"asc\"/><input name=\"orderDep\" type=submit value=\"dsc\"/></td>\n" + 
    			"					<td>Arrival<br><input name=\"orderArr\" type=submit value=\"asc\"/><input name=\"orderArr\" type=submit value=\"dsc\"/></td>\n" +
    			"					<td>Coach Class<br><input name=\"orderPriCoach\" type=submit value=\"asc\"/><input name=\"orderPriCoach\" type=submit value=\"dsc\"/></td>\n" + 
    			"					<td>Business Class<br><input name=\"orderPriBuss\" type=submit value=\"asc\"/><input name=\"orderPriBuss\" type=submit value=\"dsc\"/></td>\n" +
    			"					<td>First Class<br><input name=\"orderPriFrst\" type=submit value=\"asc\"/><input name=\"orderPriFrst\" type=submit value=\"dsc\"/></td>\n" +
    			"				</tr>\n" ;
    			
    	while(results.next()) {
    		str += "<tr>\n"; 
    		str += "<td>" + results.getString("FlightNumber") + "</td>\n";
    		str += "<td>" + results.getString("Model") + "</td>\n";
    		str += "<td>" + results.getString("AirlineName") + "</td>\n";
    		str += "<td>" + results.getString("DepartureTime") + "</td>\n";
    		str += "<td>" + results.getString("ArrivalTime") + "</td>\n";
    		
    		String checkedString = "";
    		if (!selectionMade) {
    			if (!selectedPrice.equals("")) {
        			if (results.getString("PublishedEconomyPrice").equals(selectedPrice)) {
        				selectionMade = true;
        				checkedString = "checked";
        			}
    			}
    		}
    		
    		str += "<td align=center> $" + results.getString("PublishedEconomyPrice") +  "<input type=\"radio\"  name=\"departSeat\" " + checkedString + " value=\"coach_" + segmentType + "_" + results.getString("FlightNumber")   +  "\"></td>\n";
    		str += "<td align=center> $" + results.getString("PublishedBusinessPrice") + "<input type=\"radio\"  name=\"departSeat\" value=\"business_" + segmentType + "_" + results.getString("FlightNumber")   +  "\"></td>\n";
    		str += "<td align=center> $" + results.getString("PublishedFirstPrice") + "<input type=\"radio\"  name=\"departSeat\" value=\"first_" + segmentType + "_" + results.getString("FlightNumber")   +  "\"></td>\n";
    		str += "</tr>\n";
    	}
//		ResultSetMetaData rsmd = (ResultSetMetaData) aircraft.getMetaData();
//		int columnCount = rsmd.getColumnCount();
    	
    	str +=  "			</tbody>\n" + 
    			"		</table>\n" + 
    			"	</div>\n" + 
    			"	</form>\n" + 
    			"<p>"+ error + "</p>"+
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			if (request.getParameter("showFlex").equals("Show Flex Dates")) {
				_isPostback = true;
				_showFlexDates = true;
				
			} 
			
		} catch (Exception ex) {
			
		}
		
		
		
		
		
		
		
		try {
			_selectedDays = request.getParameter("selectedDay");
			
		} catch (Exception ex) {
			
		}
		
		
		
		
		
		try {
			if (request.getParameter("orderDep").equals("asc")) {
				_isPostback = true;
				_orderBy = "order by FIT.DepartureTime";
			} 
			
		} catch (Exception ex) {
			
		}
		

		try {
			if (request.getParameter("orderDep").equals("dsc")) {
				_isPostback = true;
				_orderBy = "order by FIT.DepartureTime DESC";
			} 
			
		} catch (Exception ex) {
			
		}
		
		
		try {
			if (request.getParameter("orderArr").equals("asc")) {
				_isPostback = true;
				_orderBy = "order by FIT.ArrivalTime";
			} 
			
		} catch (Exception ex) {
			
		}
		

		try {
			if (request.getParameter("orderArr").equals("dsc")) {
				_isPostback = true;
				_orderBy = "order by FIT.ArrivalTime DESC";
			} 
			
		} catch (Exception ex) {
			
		}
		
		
		try {
			if (request.getParameter("orderPriCoach").equals("asc")) {
				_isPostback = true;
				_orderBy = "order by FT.PublishedEconomyPrice";
			} 
			
		} catch (Exception ex) {
			
		}
		

		try {
			if (request.getParameter("orderPriCoach").equals("dsc")) {
				_isPostback = true;
				_orderBy = "order by FT.PublishedEconomyPrice DESC";
			} 
			
		} catch (Exception ex) {
			
		}
		
		
		try {
			if (request.getParameter("orderPriBuss").equals("asc")) {
				_isPostback = true;
				_orderBy = "order by FT.PublishedBusinessPrice";
			} 
			
		} catch (Exception ex) {
			
		}
		

//		try {
//			if (request.getParameter("orderPriBuss").equals("dsc")) {
//				_isPostback = true;
//				_orderBy = "order by FT.PublishedBusinessPrice DESC";
//			} 
//			
//		} catch (Exception ex) {
//			
//		}
		
		
		
		try {
			if (request.getParameter("orderPriFrst").equals("asc")) {
				_isPostback = true;
				_orderBy = "order by FT.PublishedFirstPrice";
			} 
			
		} catch (Exception ex) {
			
		}
		

		try {
			if (request.getParameter("orderPriFrst").equals("dsc")) {
				_isPostback = true;
				_orderBy = "order by FT.PublishedFirstPrice DESC";
			} 
			
		} catch (Exception ex) {
			
		}
		
			
		
		doGet(request, response);
	}

}
