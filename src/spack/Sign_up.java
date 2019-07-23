package spack;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;  

/**
 * Servlet implementation class Sign_up
 */
@WebServlet("/Sign up")
public class Sign_up extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sign_up() {
        super();
        // TODO Auto-generated constructor stub
        //request.getParameter("error");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String str = "";
		str = request.getParameter("error");
		if(str.length() > 0) {
			
		}
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
		String phone = "";

		
		firstName = request.getParameter("first_name");
		lastName = request.getParameter("last_name");
		
		password = request.getParameter("password");
		emailAddress = request.getParameter("address");
		phone = request.getParameter("contact");
		
		
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
		if(phone.length() == 0) {
			request.setAttribute("error5", "Please enter a phone number");
			isCorrect = false;
		}
		
		if(isCorrect == false) {
			 
			RequestDispatcher dispatcher = request.getRequestDispatcher("Sign Up.jsp");
			dispatcher.forward( request, response);
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			PreparedStatement stmt=con.prepareStatement("Insert into UserTable (FirstName, LastName, Password, AccountType, EmailAddress) Values (?, ?, ?, ?, ?)");  
		
			RequestDispatcher dispatcher;
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, password);
			stmt.setString(4, "1");
			stmt.setString(5, emailAddress);
			
			int count = stmt.executeUpdate();
			
			if(count>0) {
				
				request.setAttribute("value", "Welcome " + firstName);
				request.setAttribute("username", emailAddress);
				dispatcher = request.getRequestDispatcher("Main.jsp");
				
			}
			else {
				 dispatcher = request.getRequestDispatcher("Sign Up.jsp");
			}
  
			con.close();  
			dispatcher.forward(request, response);
		}
		catch(Exception e){ System.out.println(e);}  
		
	}

}
