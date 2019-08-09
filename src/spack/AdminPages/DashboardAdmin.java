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
