package spack;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession _session = request.getSession();
		
		User _user = (User) _session.getAttribute("username");
		String address = "";
		if(_user != null){
			if(_user.getType() == 3) {
				address = "Dashboard.jsp";
			}
			else if(_user.getType() == 2) {
				address = "Dashboardcr.jsp";
			}
			else if(_user.getType() == 1) {
				address = "DashboardAdmin.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward( request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//admin
if(request.getParameter("click").equals("BookUser")) {
			
		}
		if(request.getParameter("click").equals("Manage users")) {
			response.sendRedirect("ManageUsers");
			return;
		}
		if(request.getParameter("click").equals("GetSales")) {
			
		}
		if(request.getParameter("click").equals("GetReservations")) {
			
		}
		if(request.getParameter("click").equals("GetRevenue")) {
			//response.sendRedirect("ManageAirplanes");
		}
		if(request.getParameter("click").equals("GetWaitlist")) {
			
		}
		if(request.getParameter("click").equals("GetFlightHistory")) {
			//response.sendRedirect("ManageAirplanes");
		}
		if(request.getParameter("click").equals("GetallFlights")) {
			
		}
		
		//for cr
		if(request.getParameter("click").equals("BookUser")) {
			
		}
		if(request.getParameter("click").equals("ChangeFlight")) {
					
		}
		if(request.getParameter("click").equals("ManageFlights")) {
			
		}
		if(request.getParameter("click").equals("ManageAirlines")) {
			
		}
		if(request.getParameter("click").equals("ManageAirplanes")) {
			response.sendRedirect("ManageAirplanes");
		}
		if(request.getParameter("click").equals("GetWaitlist")) {
			
		}

		
		
		doGet(request, response);
	}

}
