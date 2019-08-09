package spack.AdminPages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class ReservationByFlight
 */
@WebServlet("/Reservations")
public class Reservations extends HttpServlet implements NavigationBar{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	private String searchby = null;
	 
    protected void initialHtml(java.io.PrintWriter out) {
	    	String str = "\n" + 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
	    			"<head>\n" + 
	    			"<meta charset=\"UTF-8\">\n" + 
	    			"<title>Reservations</title>\n" + 
	    			"</head>\n" + 
	    			"<body>\n" + "<p align=center>Reservations"; 
	    			if(searchby != null) str += " By " + searchby;
	    			str += "</p>" + 
	    			navbarhtml 
	    			+ "<form action=Reservations method=post>List According To:"
	    			+ "</br><input type=\"radio\" name=\"searchtype\" value=\"flight\" checked>Flight" 
	    			+ "<input type=\"radio\" name=\"searchtype\" value=\"customer\">Customer"
	    			+ "</br><input type=\"Submit\" name=\"searchtype\" value=\"Generate Reservation Listing\">"
	    			+ "</form>";
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
    	
    }

    public Reservations() {
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
			ResultSet rs;
			initialHtml(response.getWriter());
			
			if(searchby.equals("Flight"))
			{
				stmt = con.prepareStatement("select ft.FlightsID, ft.FlightNumber, rft.ReservationNumber\n" + 
						"from ReservationsFlightsTable rft\n" + 
						"join FlightsTable ft using (FlightsID)");  
				rs = stmt.executeQuery();
				makeTable(rs, response.getWriter());
			}
			if(searchby.equals("Customer"))
			{
				stmt = con.prepareStatement("select ut.FirstName, ut.LastName, rft.ReservationNumber\n" + 
						"from ReservationsFlightsTable rft\n" + 
						"join ReservationTable rt using (ReservationNumber)\n" + 
						"join UserTable ut on (rt.UserID=ut.ID)");  
				rs = stmt.executeQuery();
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
		if(request.getParameter("searchtype").equals("flight")) {
			searchby="Flight";
			System.out.println("byflight");
			response.sendRedirect("Reservations");
			return;
		}
		if(request.getParameter("searchtype").equals("customer")) {
			searchby="Customer";
			System.out.println("bycustomer");
			response.sendRedirect("Reservations");
			return;
		}
		//doGet(request, response);
	}

}
