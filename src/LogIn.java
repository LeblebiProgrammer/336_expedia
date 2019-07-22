

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogIn
 */
@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogIn() {
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
		
		String email = "";
		String password = "";
		
		password = request.getParameter("password");
		email = request.getParameter("email");

		//System.out.println(fname+ lastName+ username + password+ address + contact);
		
		boolean isCorrect = true;
		
		
		if(password.length() == 0) {
			isCorrect = false;
			request.setAttribute("error3", "Please enter password");
		}
		if(email.length() == 0) {
			request.setAttribute("error4", "Please enter an email");
			isCorrect = false;
		}
		
		if(isCorrect == false) {
			 
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward( request, response);
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia","ssg103","password");   
			PreparedStatement stmt=con.prepareStatement("Select FirstName from UserTable where EmailAddress = ? and Password = ?");  
		
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			
			ResultSet rs = stmt.executeQuery();
			
//			if(stmtResult == true) {
//				
//			}
//			else {
//				
//			}
			//ResultSet rs=stmt.executeQuery();  
			
			int count = 0;
			String fname = "";
			RequestDispatcher dispatcher;
			while(rs.next()) {
				count++;
				fname = rs.getString("FirstName");
                
			}
			if(count == 1) {
				request.setAttribute("value", "Welcome " + fname);
				request.setAttribute("username", email);
				dispatcher = request.getRequestDispatcher("Main.jsp");
			}
			else {
				dispatcher = request.getRequestDispatcher("Login.jsp");
			}
			
//			while(rs.next())  
//			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			con.close();  
			dispatcher.forward(request, response);
		}
		catch(Exception e){ System.out.println(e);}  
		
		
	}

}
