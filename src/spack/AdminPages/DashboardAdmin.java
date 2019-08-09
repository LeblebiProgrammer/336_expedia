package spack.AdminPages;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AllFlightsGivenAirport
 */
@WebServlet("/DashboardAdmin")
public class DashboardAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//navigation
		if(request.getParameter("click").equals("Manage Users")) {
			response.sendRedirect("ManageUsers");
			return;
		}
		if(request.getParameter("click").equals("Monthly Sales Report")) {
			response.sendRedirect("MonthlySalesReport");
			return;
		}
		if(request.getParameter("click").equals("Get Reservations")) {
			response.sendRedirect("Reservations");
			return;
		}
		if(request.getParameter("click").equals("Get Revenue")) {
			response.sendRedirect("GetRevenue");
			return;
		}
		if(request.getParameter("click").equals("Most Active Flights")) {
			response.sendRedirect("MostActiveFlights");
			return;
		}
		if(request.getParameter("click").equals("Get all Flights")) {
			response.sendRedirect("AllFlightsGivenAirport");
			return;
		}	
		if(request.getParameter("click").equals("Customer - Most Revenue")) {
			response.sendRedirect("CustomerWithMostRevenue");
			return;
		}
		if(request.getParameter("click").equals("Customer - Most Revenue")) {
			response.sendRedirect("CustomerWithMostRevenue");
			return;
		}
	}

}
