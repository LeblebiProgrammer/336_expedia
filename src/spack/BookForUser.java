package spack;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class BookForUser
 */
@WebServlet("/BookForUser")
public class BookForUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookForUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void initialHtml(java.io.PrintWriter out) {
    	String str = "\n" + 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>Book for User</title>\n" + 
    			"<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css\">\n" + 
    			"  <link rel=\"stylesheet\" href=\"/resources/demos/style.css\">\n" + 
    			"<script src=\"https://code.jquery.com/jquery-1.12.4.js\"></script>\n" + 
    			"  <script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>\n" + 
    			"  <script>\n" + 
    			"  $( function() {\n" + 
    			"    $( \"#datepicker_from\" ).datepicker();\n" + 
    			"    $( \"#datepicker_return\" ).datepicker();\n" + 
    			"  } );\n" + 
    			"  </script>\n" + 
    			"\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"BookForUser\" method=\"post\">\n" + 
    	 
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

//    private int makeTable(java.sql.ResultSet rs, java.io.PrintWriter out)
//    	    throws Exception {
//		 int rowCount = 0;
//		
//		 out.println("<P ALIGN='center'><TABLE BORDER=1>");
//		 ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
//		 int columnCount = rsmd.getColumnCount();
//		 // table header
//		 out.println("<TR>");
//    	 for (int i = 0; i < columnCount; i++) {
//    		 out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
//		 }
//    	 out.println("</TR>");
//		 // the data
//		while (rs.next()) {
//			rowCount++;
//			out.println("<TR>");
//			for (int i = 0; i < columnCount; i++) {
//				if(rsmd.getColumnLabel(i+1).equals("AirportCode")) {
//					out.println("<TD>");
//					out.println("<input name=\"click\" type=\"submit\" value=\""+ rs.getString(i+1)  +"\" /> ");
//					
//				}else {
//					out.println("<TD>" + rs.getString(i + 1) + "</TD>");
//				}
//			}
////			out.println("<input name=\"click\" type=\"submit\" value=\"Book for User\" /> ");
//			out.println("</TR>");
//		}
//		out.println("</TABLE></P>");
//		return rowCount;
//	}
    
    protected void finishHtml(java.io.PrintWriter out) {
    	String str = "<div>\n" + 
    			"		<br> <br> <br> <br>\n" + 
    			"		<p>Make Reservation</p>\n" + 
    			"		<table border=\"2\">\n" + 
    			"			<tbody>\n" + 
    			"				<tr>\n" + 
    			"					<td>Email Address</td>\n" + 
    			"					<td>Flight Number</td>\n" +
    			"					<td>Date</td>\n" +
    			"				</tr>\n" + 
    			"				<tr>\n" + 
    			"					<td><input type=\"text\" name=\"Email\" /></td>\n" + 
    			"					<td><input type=\"text\" name=\"FlightNumber\" /></td>\n" + 
    			"					<td><input type=\"date\" name=\"Date\" value= id=\"datepicker_from\"/></td>\n" + 
    			"				</tr>\n" + 
    			"			</tbody>\n" + 
    			"		</table>\n" + 
    			"		\n" + 
    			"		<input type=\"submit\" name = \"click\" value=\"Add Reservation\" />\n" + 
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
//		HttpSession _session = request.getSession();
//		User _user = (User) _session.getAttribute("username");
		try {
			//if(_user.getType() == 2) {
			
			
			String fromDate = "";
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
			request.setAttribute("_fromDate", fromDate);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("BookForUser.jsp");
			dispatcher.forward( request, response);
			return;
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String className = "BookUser";
		//navigation
		if(request.getParameter("click").equals("BookUser")) {
//			response.sendRedirect("BookUser");
//			return;
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
		if(request.getParameter("click").equals("Next")) {
			String email = request.getParameter("Email");
			String flightNumber = request.getParameter("FlightNumber");
			String date = request.getParameter("fromDate");
			
			String error = "";
			
			if(error.length() == 0) {
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("BookUserConfirm");
				request.setAttribute("email", email);
				request.setAttribute("flightNumber", flightNumber);
				request.setAttribute("date", date);
				
				dispatcher.forward(request, response);
				return;
				
			}
			
			if(error.length() > 0) {
				
			}
			
		}
		
		if(request.getParameter("click") != null) {
			if(!request.getParameter("click").equals(className)) {
				
			}
		}
		doGet(request, response);
	}

}
