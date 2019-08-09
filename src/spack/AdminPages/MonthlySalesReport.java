package spack.AdminPages;

import java.io.IOException;
import java.text.DateFormatSymbols;
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
 * Servlet implementation class ReservationByName
 */
@WebServlet("/MonthlySalesReport")
public class MonthlySalesReport extends HttpServlet implements NavigationBar{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private int month = 0;
	private int year = -1; 
     protected void initialHtml(java.io.PrintWriter out) {
	    	String str = "\n" + 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
	    			"<head>\n" + 
	    			"<meta charset=\"UTF-8\">\n" + 
	    			"<title>Monthly Sales Report</title>\n" + 
	    			"</head>\n" +  
	    			"<body>\n" + "<p align=center>Monthly Sales Report"; 
	    			if(month > 0 && month < 13) str += " For: " + new DateFormatSymbols().getMonths()[month-1] + "/" + Integer.toString(year);
	    			str += "</p>" + 
	    			navbarhtml +
	    			"<form action=\"MonthlySalesReport\" method=post>" +
	    			"Month: <select name=\"month\">" +
	    			"<option value=\"1\">Jan</option>" +
	    			"<option value=\"2\">Feb</option>" +
	    			"<option value=\"3\">March</option>" +
	    			"<option value=\"4\">April</option>" +
	    			"<option value=\"5\">May</option>" +
	    			"<option value=\"6\">June</option>" +
	    			"<option value=\"7\">July</option>" +
	    			"<option value=\"8\">Aug</option>" +
	    			"<option value=\"9\">Sept</option>" +
	    			"<option value=\"10\">Oct</option>" +
	    			"<option value=\"11\">Nov</option>" +
	    			"<option value=\"12\">Dec</option>" +
	    			"</select>" +
 	    			"Year:<input type=\"number\" name=\"year\" min=\"0\" max=\"2019\" required>" +
	    			"</br><input type=\"submit\" name=\"click\" value=\"Generate Report\">" +
	    			"</form>";

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

    public MonthlySalesReport() {
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
			
			if(month != 0 && year != -1)
			{
				stmt = con.prepareStatement("select ft.FlightsID, fit.FlightNumber, fit.AirlineName, rt.ReservationDate, rft.ReservationNumber, rft.BookedPrice\n" + 
						"from ReservationTable rt\n" + 
						"join ReservationsFlightsTable rft using (ReservationNumber)\n" + 
						"join FlightsTable ft using (FlightsID)\n" + 
						"join FlightInfoTable fit using (FlightNumber)\n" + 
						"where month(rt.ReservationDate) = ? and year(rt.ReservationDate) = ?");
				stmt.setString(1, Integer.toString(month));
				stmt.setString(2, Integer.toString(year));
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
		String searchmonth = request.getParameter("month");
		String searchyear = request.getParameter("year");
		System.out.println("search on " + searchmonth + searchyear);
		if(searchmonth != null && searchyear != null)
		{
			month = Integer.parseInt(searchmonth);
			year = Integer.parseInt(searchyear);
			response.sendRedirect("MonthlySalesReport");
			return;
		}
		//doGet(request, response);
	}

}
