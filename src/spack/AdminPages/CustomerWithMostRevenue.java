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
 * Servlet implementation class CustomerWithMostRevenue
 */
@WebServlet("/CustomerWithMostRevenue")
public class CustomerWithMostRevenue extends HttpServlet implements NavigationBar {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private String aic = "";
	
	private void setCode(String code) {
		aic = code;
	}
	
	private String getCode() {
		return aic;
	}
     
      protected void initialHtml(java.io.PrintWriter out, ResultSet airport) throws SQLException {
	    	String str = "\n" + 
	    			"<!DOCTYPE html>\n" + 
	    			"<html>\n" + 
	    			"<head>\n" + 
	    			"<meta charset=\"UTF-8\">\n" + 
	    			"<title>Customer who generated the most revenue</title>\n" + 
	    			"</head>\n" + 
	    			"<body>\n" +
	    			"<p align=center>Customer - Most Revenue</p>" +
	    			navbarhtml + 
			"			</table></div>\n";
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

    public CustomerWithMostRevenue() {
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
			
			stmt=con.prepareStatement("select t.UserID, t.FirstName, t.LastName, MAX(t.totalRevenue) as Revenue\n" + 
					"from\n" + 
					"(\n" + 
					"	select rt.UserID, ut.FirstName, ut.LastName, SUM(BookedPrice) as totalRevenue\n" + 
					"	from ReservationTable rt\n" + 
					"	join ReservationsFlightsTable rft using (ReservationNumber)\n" + 
					"    join UserTable ut on (rt.UserID = ut.ID)\n" + 
					"	group by (UserID)) as t");  
			
			
			ResultSet rs = stmt.executeQuery();
			
			initialHtml(response.getWriter(), rs);
			makeTable(rs, response.getWriter());

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
    String className = "ManageAirlines";
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
			response.sendRedirect("ManageFlights");
			return;
		}
		if(request.getParameter("click").equals("ManageAirlines")) {
			
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
		}
		if(request.getParameter("click").equals("GetAllFlights")) {
			response.sendRedirect("AllFlightsGivenAirport");
			return;
		}
		if(request.getParameter("click").equals("Customer - Most Revenue")) {
			response.sendRedirect("CustomerWithMostRevenue");
			return;
		}
		
		doGet(request, response);
	}

}
