package spack;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;  

/**
 * Servlet implementation class Sign_up
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
        //request.getParameter("error");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//
//		String str = "";
//		str = request.getParameter("error");
//		if(str.length() > 0) {
//			
//		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("Sign up.jsp");
		dispatcher.forward( request, response);
		//return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		String firstName = "";
		String lastName = "";

		String password = "";
		String emailAddress = "";

		
		firstName = request.getParameter("first_name");
		lastName = request.getParameter("last_name");
		
		password = request.getParameter("password");
		emailAddress = request.getParameter("email");
		
		
		boolean isCorrect = true;
		
		if(firstName.length() == 0) {
			isCorrect = false;
			request.setAttribute("error0", "Please enter a first name");
		}
		if(lastName.length() == 0) {
			request.setAttribute("error1", "Please enter a last name");
			isCorrect = false;
		}
		if(password.length() == 0) {
			isCorrect = false;
			request.setAttribute("error3", "Please enter password");
		}
		if(emailAddress.length() == 0) {
			request.setAttribute("error4", "Please enter an email");
			isCorrect = false;
		}
		
		if(isCorrect == false) {
			 
//			RequestDispatcher dispatcher = request.getRequestDispatcher("Sign up.jsp");
//			dispatcher.forward( request, response);
//			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			PreparedStatement stmt=con.prepareStatement("Insert into UserTable (FirstName, LastName, Password, AccountType, EmailAddress) Values (?, ?, ?, ?, ?)");  
		
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, password);
			stmt.setString(4, "3");
			stmt.setString(5, emailAddress);
			
			int count = stmt.executeUpdate();
			
			if(count>0) {
				//sign up screen is for users only so we can set the account type directly to 3
				User _user = new User(firstName, emailAddress, 3);
				HttpSession session = request.getSession();
				session.setAttribute("username", _user);
				request.setAttribute("value", "Welcome " + firstName);
				con.close();  
				//request.setAttribute("username", emailAddress);
				//dispatcher = request.getRequestDispatcher("Main.jsp");
				response.sendRedirect("Main");
				return;
			}
			else {
				con.close();  
				//dispatcher = request.getRequestDispatcher("Sign Up.jsp");
			}
  
			
			//dispatcher.forward(request, response);
		}
		catch(Exception e){ System.out.println(e);}  
		doGet(request, response);
		
	}

}
