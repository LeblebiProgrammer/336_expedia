package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class ManageAirplanes
 */
@WebServlet("/ManageAirplanes")
public class ManageAirplanes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageAirplanes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
				if(rsmd.getColumnLabel(i+1).equals("TailNumber")) {
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
    
    
    protected void initialHtml(java.io.PrintWriter out) {
    	String str = "\n" + 
    			"<!DOCTYPE html>\n" + 
    			"<html>\n" + 
    			"<head>\n" + 
    			"<meta charset=\"UTF-8\">\n" + 
    			"<title>Manage Aircraft</title>\n" + 
    			"</head>\n" + 
    			"<body>\n" + 
    			"	<form action=\"ManageAirplanes\" method=\"post\">\n" + 
    			"	<div>\n" + 
    			"		<table style=\"height: 51px; width: 100px; float: left;\" border=\"1\">\n" + 
    			"			<tbody>\n" + 
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
    			"			</tbody>\n" + 
    			"		</table>  	</div>";
    	out.print(str);
    }


    
    protected void finishHtml(java.io.PrintWriter out) {
    	String str = "<div>\n" + 
    			"		<br> <br> <br> <br>\n" + 
    			"		<p>Add airplane</p>\n" + 
    			"		<table border=\"2\">\n" + 
    			"			<tbody>\n" + 
    			"				<tr>\n" + 
    			"					<td>Tail Number</td>\n" + 
    			"					<td>Manufacturer</td>\n" + 
    			"					<td>Model</td>\n" + 
    			"					<td>Economy Count</td>\n" + 
    			"					<td>Business Count</td>\n" + 
    			"					<td>First Count</td>\n" + 
    			"				</tr>\n" + 
    			"				<tr>\n" + 
    			"					<td><input name=\"tailNumber\" /></td>\n" + 
    			"					<td><input name=\"manufacturer\" /></td>\n" + 
    			"					<td><input name=\"model\" /></td>\n" + 
    			"					<td><input name=\"economyCount\" type = \"number\" min=\"0\" value = \"0\"/></td>\n" + 
    			"					<td><input name=\"businessCount\" type = \"number\" min=\"0\"  value = \"0\" /></td>\n" + 
    			"					<td><input name=\"firstCount\" type = \"number\" min=\"0\"  value = \"0\"/></td>\n" + 
    			"				</tr>\n" + 
    			"			</tbody>\n" + 
    			"		</table>\n" + 
    			"		\n" + 
    			"		<input type=\"submit\" name = \"click\" value=\"Add Airplane\" />\n" + 
    			"	</div>\n" + 
    			"	</form>\n" + 
    			"</body>\n" + 
    			"</html>";
    	out.write(str);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			
			PreparedStatement stmt;	
			if(request.getParameter("SearchParam") == null) {
				stmt=con.prepareStatement("Select TailNumber, Manufacturer, Model, EconomyCount, BusinessCount, FirstCount from AircraftTable");  
			}
			else {
				stmt=con.prepareStatement("Select TailNumber, Manufacturer, Model, EconomyCount, BusinessCount, FirstCount from AircraftTable");
			}
			ResultSet rs = stmt.executeQuery();
			
			initialHtml(response.getWriter());
			makeTable(rs, response.getWriter());
			//searchTable(response.getWriter());
			finishHtml(response.getWriter());
			con.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
//		RequestDispatcher dispatcher = request.getRequestDispatcher("Manage Airplanes.jsp");
//		dispatcher.forward( request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		if(request.getParameter("click").equals("Add Airplane")) {
			String tailNumber = "";
			String manufacturer = "";
			String model = "";
			String economy = "";
			String business = "";
			String first = "";
			
			String error = "";
			
			int x = 0;
			int x2 = 0;
			int x3 = 0;
			
			
			if(request.getParameter("tailNumber") != null) {
				tailNumber = request.getParameter("tailNumber");
			}else {
				error = "Tail number cannot be empty. ";
			}
			
			if(request.getParameter("manufacturer") != null) {
				manufacturer = request.getParameter("manufacturer");
			}else {
				error += "Manufacturer cannot be empty. ";
			}
			
			if(request.getParameter("model") != null) {
				model = request.getParameter("model");
			}else {
				error += "Model cannot be empty. ";
			}
			
			if(request.getParameter("economyCount") != null) {
				economy = request.getParameter("economyCount");
				x = 0;
				try {
					x = Integer.parseInt(economy);
				}catch(Exception e) {
					error += "Please enter a numerical value for economy count. ";
				}
			}
			
			if(request.getParameter("businessCount") != null) {
				business = request.getParameter("businessCount");
				x2 = 0;
				try {
					x2 = Integer.parseInt(business);
				}catch(Exception e) {
					error += "Please enter a numerical value for business count. ";
				}
			}
			
			if(request.getParameter("firstCount") != null) {
				x3 = 0;
				first = request.getParameter("firstCount");
				try {
					x3 = Integer.parseInt(first);
				}catch(Exception e){
					error += "Please enter a numerical value for first count. ";
				}
			}
			if(error.length() > 0) {
				
			}
			else {
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					Connection con=DriverManager.getConnection(  
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
					PreparedStatement stmt=con.prepareStatement("Insert into AircraftTable (Manufacturer,"
							+ " Model, EconomyCount, BusinessCount, FirstCount, TailNumber) Values (?, ?, ?, ?, ?, ?)");  
				
					//RequestDispatcher dispatcher;
					stmt.setString(1, manufacturer);
					stmt.setString(2, model);
					stmt.setInt(3, x);
					stmt.setInt(4, x2);
					stmt.setInt(5, x3);
					stmt.setString(6, tailNumber);
					
					int count = stmt.executeUpdate();
					if(count > 0) {
						System.out.println("Inserted");
					}
					con.close();
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
					
			}
			doGet(request, response);
			return;
		}
		else {
			String tailNumber = request.getParameter("click");
			if(tailNumber != null) {
				//request.setAttribute("TailNumber", tailNumber);
				RequestDispatcher dispatcher = request.getRequestDispatcher("EditAirplane");
				request.setAttribute("TailNumber", tailNumber);
				dispatcher.forward( request, response);
//				response.sendRedirect("EditAirplane");
				return;
			}
			
		}
		doGet(request, response);
		
	}

}
