package spack;

import java.io.IOException;
import java.net.InetAddress;
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
import javax.servlet.http.HttpSession;

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

	protected void fullHtml(java.io.PrintWriter out) {
		String str = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<title>Log in</title>\n" + "</head>\n"
				+ "<body>\n" + "<div class = \"container\">\n" + "		<form action=\"LogIn\" method = \"post\">\n"
				+ "			<table>\n" + "				<tr style = \"height:20px\">\n"
				+ "					<td>Email</td>\n"
				+ "					<td><input type=\"text\" name=\"email\" />\n"
				+ "					<td><p style=\"color:Red; font-size: 15px;\"></p></td>\n" + "				</tr>\n"
				+ "					<tr>\n" + "					<td>Password</td>\n"
				+ "					<td><input type=\"password\" name=\"password\" /></td>\n"
				+ "					<td><p style=\" color:Red; font-size: 15px;\"></p></td>\n" + "				</tr>\n"
				+ "				<tr>\n" + "					<td>\n" + "						\n"
				+ "					</td>\n" + "				</tr>\n" + "\n" + "			</table>\n"
				+ "			<input type=\"submit\" name =\"click\"  value=\"Log in\" />\n" + "		</form>\n"
				+ "	</div>\n" + "	\n" + "</body>\n" + "</html>";
		out.write(str);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		fullHtml(response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean refresh = true;
		if (request.getParameter("click").equals("Log in")) {
			refresh = false;
			String email = "";
			String password = "";

			password = request.getParameter("password");
			email = request.getParameter("email");

			// System.out.println(fname+ lastName+ username + password+ address + contact);

			boolean isCorrect = true;

			if (password.length() == 0) {
				isCorrect = false;
				request.setAttribute("error3", "Please enter password");
			}
			if (email.length() == 0) {
				request.setAttribute("error4", "Please enter an email");
				isCorrect = false;
			}

			if (isCorrect == false) {

				RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
				dispatcher.forward(request, response);
				return;
			}

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia", "ssg103",
						"password");
				PreparedStatement stmt = con.prepareStatement(
						"Select FirstName,AccountType from UserTable where EmailAddress = ? and Password = ?");

				stmt.setString(1, email);
				stmt.setString(2, password);

				ResultSet rs = stmt.executeQuery();

				int count = 0;
				String fname = "";
				int type = 0;
				while (rs.next()) {
					count++;
					fname = rs.getString("FirstName");
					type = Integer.parseInt(rs.getString("AccountType"));

				}
				con.close();
				if (count == 1) {
					User _user = new User(fname, email, type);
					HttpSession session = request.getSession();
					session.setAttribute("username", _user);
					//response.getWriter().print("aaaa test");
					response.sendRedirect("Main");
					return;

				} else {
					response.getWriter().print("no user name password found");
				}

				//
				// con.close();
				// dispatcher.forward(request, response);
			} catch (Exception e) {
				response.getWriter().print(e.getMessage());
				response.getOutputStream().close();
			}
		}
		doGet(request, response);
	}

}
