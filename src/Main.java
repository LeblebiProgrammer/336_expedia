

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		if(request.getParameter("click").equals("log out")) {
			request.setAttribute("value", "");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
			dispatcher.forward( request, response);
			return;
		}
		else if(request.getParameter("click").equals("signup")) {
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sign Up.jsp");
			dispatcher.forward( request, response);
			return;
		}
		else if(request.getParameter("click").equals("login")) {
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("LogIn.jsp");
			dispatcher.forward( request, response);
			return;
		}
		else if (request.getParameter("click").equals("Search")) {
			String origin = request.getParameter("origin");
			String destination = request.getParameter("destination");
			
			String fromDate = request.getParameter("fromDate");
			String returnDate = request.getParameter("returnDate");
			
			String roundtrip = request.getParameter("roundTrip");
			String oneway = request.getParameter("oneWay");
			System.out.println("Hello");
		}
	}

}
