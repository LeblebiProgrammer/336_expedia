package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class BookUserConfirm
 */
@WebServlet("/BookUserConfirm")
public class BookUserConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private int ID = 0;

	private void setID(int value) {
		ID = value;
	}

	private int getID() {
		return ID;
	}

	private int day = 0;

	private void setDay(int value) {
		day = value;
	}

	private int getDay() {
		return day;
	}

	private String date = "";

	private void setDate(String value) {
		date = value;
	}

	private String getDate() {
		return date;
	}

	private String flightNumber = "";

	private void setFlight(String value) {
		flightNumber = value;
	}

	private String getFlight() {
		return flightNumber;
	}

	private int flightID = 0;

	private void setFlightID(int value) {
		flightID = value;
	}

	private int getFlightID() {
		return flightID;
	}

	private float[] prices = new float[3];

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookUserConfirm() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void initialHtml(java.io.PrintWriter out) {
		String str = "\n" + "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n"
				+ "<title>Confirm Reservation</title>\n" + "</head>\n" + "<body>\n"
				+ "	<form action=\"BookUserConfirm\" method=\"post\">\n" + "	<div>\n"
				+ "		<table style=\"height: 51px; width: 100px; float: left;\" border=\"1\">\n"
				+ "			<tbody>\n" + "<tr style=\"height: 27px;\">\n"
				+ "					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n"
				+ "						name=\"click\" type=\"submit\" value=\"BookUser\" /></td>\n"
				+ "				</tr>\n" + "				<tr style=\"height: 27px;\">\n"
				+ "					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n"
				+ "						name=\"click\" type=\"submit\" value=\"ChangeFlight\" /></td>\n"
				+ "				</tr>\n" + "				<tr style=\"height: 27px;\">\n"
				+ "					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n"
				+ "						name=\"click\" type=\"submit\" value=\"ManageFlights\" /></td>\n"
				+ "				</tr>\n" + "				<tr style=\"height: 27px;\">\n"
				+ "					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n"
				+ "						name=\"click\" type=\"submit\" value=\"ManageAirports\" /></td>\n"
				+ "				</tr>\n" + "				<tr style=\"height: 27px;\">\n"
				+ "					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n"
				+ "						name=\"click\" type=\"submit\" value=\"ManageAirlines\" /></td>\n"
				+ "				</tr>\n" + "				<tr style=\"height: 27px;\">\n"
				+ "					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n"
				+ "						name=\"click\" type=\"submit\" value=\"ManageAirplanes\" /></td>\n"
				+ "				</tr>\n" + "				<tr style=\"height: 27px;\">\n"
				+ "					<td style=\"width: 260px; height: 27px; text-align: center;\"><input\n"
				+ "						name=\"click\" type=\"submit\" value=\"GetWaitlist\" /></td>\n"
				+ "				</tr>" + "			</tbody>\n" + "		</table>  	</div>";
		out.print(str);
	}

	protected void finishHtml(java.io.PrintWriter out, String perror) {
		String str = "<div>\n<p>" + perror + "</p>	</div>\n" + "	</form>\n" + "</body>\n" + "</html>";
		out.write(str);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	private int getWeekDay(String _dateEntered) {
		int result = 0;

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date;
		try {
			date = dateFormat.parse(_dateEntered);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			result = calendar.get(Calendar.DAY_OF_WEEK);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;

	}

	private int makeTable(java.sql.ResultSet rs, java.io.PrintWriter out) throws Exception {
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
		do {
			rowCount++;
			out.println("<TR>");
			int count = 0;
			for (int i = 0; i < columnCount; i++) {
				if (rsmd.getColumnLabel(i + 1).equals("PublishedEconomyPrice")
						|| rsmd.getColumnLabel(i + 1).equals("PublishedBusinessPrice")
						|| rsmd.getColumnLabel(i + 1).equals("PublishedFirstPrice")) {
					prices[count] = rs.getFloat(i + 1);
					count++;
					out.println("<TD>");
					// out.println("<input name=\"click\" type=\"submit\" value=\""+
					// rs.getString(i+1) +"\" /> ");
					out.println("<input name=\"tripType\" type=\"radio\" value=\"" + rsmd.getColumnLabel(i + 1)
							+ "\"/><p name = name=\"" + rsmd.getColumnLabel(i + 1) + "\" >" + rs.getString(i + 1)
							+ "</p></td>");

				} else {
					out.println("<TD>" + rs.getString(i + 1) + "</TD>");
				}
			}
//			out.println("<input name=\"click\" type=\"submit\" value=\"Book for User\" /> ");
			out.println("</TR>");
		} while (rs.next());
		out.println("<tr><td><input name=\"click\" type=\"submit\" value=\"Buy\" /> </td></tr>");
		out.println("</TABLE></P>");
		return rowCount;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		try {
			initialHtml(response.getWriter());
			String error = "";

			String emailAddress = (String) request.getAttribute("email");
			String fn = (String) request.getAttribute("flightNumber");
			String date = (String) request.getAttribute("date");

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia", "ssg103",
					"password");

			PreparedStatement stmt;

			stmt = con.prepareStatement("Select ID from UserTable where EmailAddress = ?");
			stmt.setString(1, emailAddress);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				this.setID(rs.getInt("ID"));
			} else {
				error = "Email doesn't exist";
				finishHtml(response.getWriter(), error);
				return;
			}

			int dayOfWeek = getWeekDay(date);
			if (dayOfWeek > 0) {
				this.setDay(dayOfWeek);
				this.setDate(date);
			} else {
				error = "Day doesnt exist";
				finishHtml(response.getWriter(), error);
				return;
			}
			this.setFlight(fn);

			PreparedStatement stmt2 = con
					.prepareStatement("Select * from FlightsTable where FlightNumber = ? and Day = ?");
			stmt2.setString(1, this.getFlight());
			stmt2.setInt(2, this.getDay());

			ResultSet rs2 = stmt2.executeQuery();
			if (rs2.next()) {
				this.setFlightID(rs2.getInt("FlightsID"));
			} else {
				error = "No flights found";
				finishHtml(response.getWriter(), error);
				return;
			}

			makeTable(rs2, response.getWriter());

			finishHtml(response.getWriter(), error);
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String randomAlphaNumeric(int count) {
		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("click") != null) {
			if (request.getParameter("click").equals("BookUser")) {
				response.sendRedirect("BookUser");
				return;
			}
			if (request.getParameter("click").equals("ChangeFlight")) {
				response.sendRedirect("ChangeFlight");
				return;
			}
			if (request.getParameter("click").equals("ManageFlights")) {
				response.sendRedirect("ManageFlights");
				return;
			}
			if (request.getParameter("click").equals("ManageAirlines")) {
				response.sendRedirect("ManageAirlines");
				return;
			}
			if (request.getParameter("click").equals("ManageAirports")) {
				response.sendRedirect("ManageAirports");
				return;
			}
			if (request.getParameter("click").equals("ManageAirplanes")) {
				response.sendRedirect("ManageAirplanes");
				return;
			}
			if (request.getParameter("click").equals("GetWaitlist")) {
				response.sendRedirect("GetWaitlist");
				return;
			}
			if (request.getParameter("click").equals("Buy")) {
				String str = request.getParameter("tripType");
				String code = randomAlphaNumeric(5);
				String ff = request.getParameter(str);
				int type = 0;
				int price = -1;
				if (str.contains("Economy")) {
					type = 4;
					price = 0;
				}
				if (str.contains("Business")) {
					type = 5;
					price = 1;
				}
				if (str.contains("First")) {
					type = 6;
					price = 2;
				}
				if (str.contains("Waitlist")) {
					type = 7;
					price = 0;
				}

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection(
							"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia",
							"ssg103", "password");

					PreparedStatement stmt;

					stmt = con.prepareStatement(
							"Insert into ReservationTable (ReservationNumber, UserID, ReservationDate, StatusID) values(?,?,?,?)");
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

					String _dd = getDate();
					_dd = _dd.replace('/', '-');

					java.util.Date date = sdf.parse(_dd);
					java.sql.Date _date = new java.sql.Date(date.getTime());

					stmt.setString(1, code);
					stmt.setInt(2, getID());
					stmt.setDate(3, _date);
					stmt.setInt(4, type);

					boolean b = stmt.execute();
					if(b == true) {
						
					}

					PreparedStatement stmt2 = con.prepareStatement(
							"Insert into ReservationsFlightsTable (ReservationNumber, FlightsID, StatusID, BookedPrice) values(?,?,?,?)");

					if (type != 7) {

					}
					stmt2.setString(1, code);
					stmt2.setInt(2, getFlightID());
					stmt2.setInt(3, type);
					stmt2.setFloat(4, prices[price]);

					stmt2.executeUpdate();
					
					con.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		doGet(request, response);
	}

}
