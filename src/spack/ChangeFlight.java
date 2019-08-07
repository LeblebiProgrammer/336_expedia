package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class ChangeFlight
 */
@WebServlet("/ChangeFlight")
public class ChangeFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private String rnumber = "";
	
	private void setRnumber(String value) {
		rnumber = value;
	}
	private String getRnumber() {
		return rnumber;
	}
	
    public ChangeFlight() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void initialHtml(java.io.PrintWriter out) {
    	String str = "\n" + 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>ChangeFlight</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"ChangeFlight\" method=\"post\">\n" + 
    	 
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
    	
    	
    	str += "			<table style=\"height: 51px; width: 100px; float: left;\" border=\"1\">\n" + 
    	"				<tbody>\n" + 
    	"					<tr style=\"height: 27px;\">\n" + 
    	"						<td style=\"width: 260px; height: 27px; text-align: center;\">Enter the ReservationNumber</td>\n";
    	
    	str += "<td width = \"14%\"><input name = \"txSearch\" type=\"text\" value = \""+ getRnumber() +"\" /></td>";
    	    	
    	
    	str += "						<td>	<input name=\"click\" type=\"Submit\" value=\"Search\" /></td>\n"+
    	"					</tr>\n" + 
    	"\n" + 
    	"				</tbody>\n" + 
    	"			</table></div>\n";
    			
    	
    	out.print(str);
    }
    
    private int makeTable(java.sql.ResultSet rs, java.io.PrintWriter out)
    	    throws Exception {
		 int rowCount = 0;
		
		 out.println("<br><br><br><br><br><br><br><br><P ALIGN='center'><TABLE BORDER=1>");
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
				if(rsmd.getColumnLabel(i+1).equals("ReservationNumber")) {
					out.println("<TD>");
					out.println("<input name=\"click\" type=\"submit\" value=\"Delete\" /> ");
					
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
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			initialHtml(response.getWriter());
			if(getRnumber().length()>0) {
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
				
				PreparedStatement stmt=con.prepareStatement("Select rt.ReservationNumber, rt.ReservationDate, rft.statusID, rft.BookedPrice, ft.FlightNumber from ReservationTable rt \n" + 
						"	join \n" + 
						"    ReservationsFlightsTable rft on(rt.ReservationNumber = rft.ReservationNumber)\n" + 
						"    join\n" + 
						"    FlightsTable ft on (rft.FlightsID = ft.FlightsID)\n" + 
						"    where rt.ReservationNumber = ? and rt.ReservationDate >= ?");
				
				java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				stmt.setString(1, getRnumber());
				stmt.setDate(2, date);
				
				ResultSet rs = stmt.executeQuery();
				makeTable(rs, response.getWriter());
			}
			finishHtml(response.getWriter());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("click").equals("BookUser")) {
			response.sendRedirect("BookForUser");
			return;
		}
		if(request.getParameter("click").equals("ChangeFlight")) {
//			response.sendRedirect("ChangeFlight");
//			return;
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
		if(request.getParameter("click").equals("Search")) {
			if(request.getParameter("txSearch")!= null) {
				this.setRnumber(request.getParameter("txSearch"));
			}else {
				this.setRnumber("");
			}
		}
		
		if(request.getParameter("click").equals("Delete")) {
			String rn = getRnumber();
			
			try {
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
				PreparedStatement stmt=con.prepareStatement("Delete from ReservationTable where ReservationNumber = ?");
				stmt.setString(1, rn);
				
				
				int count = stmt.executeUpdate();
				if(count > 0) {
					System.out.println("Deleted");
				}
				con.close();
				
				
//				RequestDispatcher dispatcher = request.getRequestDispatcher("ManageAirplanes");
//				dispatcher.forward( request, response);
				response.sendRedirect("Main");
				return;
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		doGet(request, response);
	}

}
