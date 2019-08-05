package spack;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		String departureCityDropdown = "?";
		String returnCityDropdown = "?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			
			String departSQL = "SELECT Distinct DepartureCity FROM RuExpedia.FlightsTable FT " + 
					"	JOIN RuExpedia.FlightInfoTable FIT on (FIT.FlightNumber = FT.FlightNumber)";
			
			PreparedStatement departStmt = con.prepareStatement(departSQL);
			ResultSet rsDepart = departStmt.executeQuery();
			
			
			departureCityDropdown = "<select name = \"origin\" size = \"1\">" + 
					"<option value = \"\">Select Depart City</option>\n";
	    	do {
	    		
	    		try {
	    			String option = "<option value = \"" + rsDepart.getString("DepartureCity") + "\">" + rsDepart.getString("DepartureCity") + "</option>\n";
	    			departureCityDropdown += option;
	    		}
	    		catch(Exception e) {
	    			System.out.print(e.getMessage());
	    		}
	    	}while(rsDepart.next());
	    	departureCityDropdown += "</select>";
	    	
	    	
	    	String returnSQL = "SELECT Distinct DestinationCity FROM RuExpedia.FlightsTable FT " + 
					"	JOIN RuExpedia.FlightInfoTable FIT on (FIT.FlightNumber = FT.FlightNumber)";
			
			PreparedStatement returnStmt = con.prepareStatement(returnSQL);
			ResultSet rsReturn = returnStmt.executeQuery();
			
			
			returnCityDropdown = "<select name = \"destination\" size = \"1\">"+ 
					"<option value = \"\">Select Arrival City</option>\n";
	    	do {
	    		//String option= "<option value = \"" + aircraft.getString("TailNumber") + "\"" + aircraft.getString("TailNumber") + "</option>";\
	    		try {
	    			String option = "<option value = \"" + rsReturn.getString("DestinationCity") + "\">" + rsReturn.getString("DestinationCity") + "</option>\n";
	    			returnCityDropdown += option;
	    		}
	    		catch(Exception e) {
	    			System.out.print(e.getMessage());
	    		}
	    	}while(rsReturn.next());
	    	returnCityDropdown += "</select>";
	    	
	    	
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//departureCityDropdown = "Error: " ;
			e.printStackTrace();
		}  
		
		
		
		
		request.setAttribute("_departureCityDroddown", departureCityDropdown);
		request.setAttribute("_returnCityDroddown", returnCityDropdown);
		
		
		
		
		
		
		String fromDate = "";
		String returnDate = "";
		
		String origin = "JFK"; // for now i want to put hard coded searches on Main JSP
		String destination = "LAX";
		
		request.setAttribute("_origin", origin);
		request.setAttribute("_destination", destination);
		
//		String str = "";
		HttpSession session = request.getSession();
		if(request.getParameter("_fromDate") == null) {
			
			Date dateOfSearch = new Date();
			
			int noOfDays = 7; 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateOfSearch);            
			calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
			Date date = calendar.getTime();
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			fromDate = dateFormat.format(date);
		}
		else {
			//fromDate = request.getParameter("_fromDate");	
		}
		
		
		
		if(request.getParameter("_returnDate") == null) {
			Date dateOfSearch = new Date();
			
			int noOfDays = 14; 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateOfSearch);            
			calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
			Date date = calendar.getTime();
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			returnDate = dateFormat.format(date);
		}
		else {
			//fromDate = request.getParameter("_fromDate");	
		}
		
		if(request.getParameter("_returnDate") == null) {
			request.setAttribute("_returnDate", "");
		}
		if(request.getParameter("error") == null) {
			//str = "";
		}
		else {
			//str = request.getParameter("error");
		}
		if(session.getAttribute("username") != null) {
			User _user = (User) session.getAttribute("username");
			request.setAttribute("value", "Welcome " + _user.getName());
		}

		
		request.setAttribute("_fromDate", fromDate);
		request.setAttribute("_returnDate", returnDate);
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
		dispatcher.forward( request, response);
		return;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		HttpSession session = request.getSession();
		
		
		//
		if(request.getParameter("click").equals("log out")) {
			request.setAttribute("value", "");
			session.removeAttribute("username");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
			dispatcher.forward( request, response);
			return;
		}
		//signup
		else if(request.getParameter("click").equals("signup")) {
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sign up.jsp");
			dispatcher.forward( request, response);
			return;
		}
		//login
		else if(request.getParameter("click").equals("login")) {
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("LogIn");
			dispatcher.forward( request, response);
			return;
		}
		//search
		else if (request.getParameter("click").equals("Search")) {
			boolean oneWayBox = false;
			boolean roundTripBox = true;
			
			String error = "";
			String origin = request.getParameter("origin");
			
			
			//origin
			if(origin != null) {
				origin = origin.replace("&nbsp;", " ");
				origin = origin.replace("Â", "");
				origin = origin.replace("  ", " ");
				if(origin.length() == 0) {
					error = "Origin city cannot be empty. ";
				}
				else {
					//origin is valid do checks
					request.setAttribute("_origin", origin);
				}
			}
			else {
				error = "Origin city cannot be empty. ";
			}
			//DESTINATION
			String destination = request.getParameter("destination");
			if(destination != null) {
				destination = destination.replace("&nbsp;", " ");
				destination = destination.replace("Â", "");
				destination = destination.replace("  ", " ");
				if(destination.length() == 0) {
					error += "Destination city cannot be empty. ";
				}
				else {
					//destination is valid do checks
					request.setAttribute("_destination", destination);
				}
			}
			else {
				error += "Destination city cannot be empty. ";
			}
			
			//from Date
			String fromDate = request.getParameter("fromDate");
			if(fromDate != null) {
				fromDate = fromDate.replace("&nbsp;", " ");
				fromDate = fromDate.replace("Â", "");
				fromDate = fromDate.replace("  ", " ");
				if(fromDate.length() == 0) {
					error += "From Date cannot be empty. ";
				}
				else {
					//from date is valid do checks
					request.setAttribute("_fromDate", fromDate);
				}
			}else {
				error += "From Date cannot be empty. ";
			}
			
			//roundTripBox
//			String roundTrip = request.getParameter("roundTripBox");
//			if(tripType != null) {
//				if(roundTrip.toLowerCase().equals("on")) {
//					roundTripBox = true;
//				}
//				else {
//					roundTripBox = false;
//				}
//			}
//			else {
//				roundTripBox = false;
//			}
			String tripType = request.getParameter("tripType");
			if(tripType != null) {
				if(tripType.toLowerCase().equals("one_way")) {
					oneWayBox = true;
					roundTripBox = false;
				}
				else {
					oneWayBox = false;
					roundTripBox = true;
				}
			}
			else {
				roundTripBox = true;
				oneWayBox = false;
			}
			
			 //check if returnDate is 
			String returnDate = request.getParameter("returnDate");
			if(roundTripBox == true && oneWayBox == false) {
				if(returnDate != null) {
					
					returnDate = returnDate.replace("&nbsp;", " ");
					returnDate = returnDate.replace("Â", "");
					returnDate = returnDate.replace("  ", " ");
					if(returnDate.length() == 0) {
						error += "Return date cannot be empty when round trip is selected. ";
					}
					else {
						//return date is valid do checks

						request.setAttribute("_returnDate", returnDate);
					}
				}
				else {
					error += "Return date cannot be empty when round trip is selected. ";
				}
			}
			else if(oneWayBox == true && roundTripBox == false) {
				//valid. If it does not come either here or above then it has an error.
			}
			else if(oneWayBox == true && roundTripBox == true) {
				error += "Both checkboxes cannot be checked at the same time. ";
			}
			else if(oneWayBox == false && roundTripBox == false) {
				error += "Both checkboxes cannot be empty at the same time. ";
			}
			
			if(error.length() > 0 ) {
				request.setAttribute("error", error);

				RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
				dispatcher.forward(request, response);
			}
			else {
				//SearchObject _so = new SearchObject(origin, destination, fromDate, returnDate, oneWayBox, roundTripBox);
				//session.setAttribute("search", _so);
				
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("FlightSearch");
				dispatcher.forward(request, response);
				
				
//				RequestDispatcher dispatcher = request.getRequestDispatcher("SearchResult");
//				dispatcher.forward(request, response);
				return;
			}
		}
		else if(request.getParameter("click").equals("Dashboard")) {

			try {
				HttpSession _session = request.getSession();
				
				User _user = (User) _session.getAttribute("username");
				if(_user != null) {

//					RequestDispatcher dispatcher = request.getRequestDispatcher("Dashboard");
//					dispatcher.forward(request, response);
					response.sendRedirect("Dashboard");
					return;
				}
				else {
					
					
					
					request.setAttribute("error", "Please log in to continue");
					
					request.setAttribute("_fromDate", request.getAttribute("_fromDate"));
					
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
					dispatcher.forward(request, response);
					//response.setIntHeader("refresh", 0);
			
				}
			}
			catch(Exception e){
				
			}
		}
	}

}
