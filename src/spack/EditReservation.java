package spack;

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
 * Servlet implementation class editReservation
 */
@WebServlet("/EditReservation")
public class EditReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditReservation() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String reservationNumber = "";
    
    private void setReservation(String value) {
    	reservationNumber = value;
    }
    private String getReservation() {
    	return reservationNumber;
    }
    
    protected void initialHtml(java.io.PrintWriter out) {
    	String str = "\n" + 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>View Reservations</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"ViewReservation\" method=\"post\">\n"+
    			"	<p>Delete </p>";
    
    	
    	out.print(str);
    }
    
    
    
    
    private int makeTable(java.sql.ResultSet rs, ResultSet enumSet, java.io.PrintWriter out, boolean buttonNeeded)
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
				if(rsmd.getColumnLabel(i+1).equals("ReservationNumber") && buttonNeeded == true &&
						(rs.getInt("StatusID") == 5 || rs.getInt("StatusID") == 6)) {
					
					out.println("<TD>");
					out.println("<input name=\"click\" type=\"submit\" value=\""+ rs.getString(i+1)  +"\" /> ");
					
				}else if(rsmd.getColumnLabel(i+1).equals("StatusID")){
					out.println("<TD>");
					if(enumSet.next()) {
						do {
							try {
								if (enumSet.getString("EnumID").equals(rs.getString("StatusID"))) {
	
									String option = enumSet.getString("Description");
									out.write(option);
								} 
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						} while (enumSet.next());
						enumSet.beforeFirst();
					}
					out.println("</TD>");
					
				}
				else {
//					if(rsmd.getColumnLabel(i+1).equals("AirlineName")) {
//						out.println("<TD name = \"aname\" value = \""+rs.getString(i+1)+ "\">" + rs.getString(i + 1) + "</TD>");
					
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
		String str = //"</div>\n" +

				"	</form>\n" +
				"</body>\n" + "</html>";
		out.write(str);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			String str = request.getParameter("_reservation");
			if(str != null) {
				this.setReservation(str);
				
					//User _user = (User) session.getAttribute("username");
					//this.setID( _user.getID());
					//this.setID(14);
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					
					PreparedStatement stmt;	
					
					stmt=con.prepareStatement("SELECT rt.ReservationNumber, rt.ReservationDate, fit.DepartureTime, fit.ArrivalTime, ft.FlightNumber, fit.AirlineName, fit.DepartureCity, fit.DestinationCity, \n" + 
							"\n" + 
							"rft.StatusID, rft.BookedPrice FROM RuExpedia.ReservationTable rt\n" + 
							"	 join \n" + 
							"			ReservationsFlightsTable rft on(rt.ReservationNumber = rft.ReservationNumber)\n" + 
							"	 join\n" + 
							"			FlightsTable ft on (rft.FlightsID = ft.FlightsID)\n" + 
							"	 join \n" + 
							"			FlightInfoTable fit on (fit.FlightNumber = ft.FlightNumber)\n" + 
							"            \n" + 
							"where rt.ReservationNumber = ?");
		 
					//java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
					
					stmt.setString(1, this.getReservation());
					
					//System.out.println(dtf.format(now)); 
					
					PreparedStatement stmt3 = con.prepareStatement("Select * from EnumTable");
					
					ResultSet rs = stmt.executeQuery();
					
					ResultSet rs3 = stmt3.executeQuery();
					
					
					initialHtml(response.getWriter());
					makeTable(rs, rs3, response.getWriter(), true);
	
					
					finishHtml(response.getWriter());
	
					con.close();
				}
				else {
				}
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
		doGet(request, response);
	}

}
