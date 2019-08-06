package spack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * Servlet implementation class EditFlight
 */
@WebServlet("/EditFlight")
public class EditFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String error = "";

	private String getError() {
		return error;
	}

	private void setError(String value) {
		error = value;
	}

	private String flightNumber = "";

	private void setFlightNumber(String _flightNum) {
		flightNumber = _flightNum;

	}

	private String getFlightNumber() {
		return flightNumber;

	}

	protected void initialHtml(java.io.PrintWriter out) {
		String str = "\n" + "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n"
				+ "<title>Edit Flights</title>\n" + "</head>\n" + "<body>\n"
				+ "	<form action=\"EditFlight\" method=\"post\">\n" +

				"			<table style=\"height: 51px; width: 100px; float: left;\" border=\"1\">\n"
				+ "				<tbody>\n" + "<tr style=\"height: 27px;\">\n"
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
				+ "				</tr>" + "				</tbody>\n" + "			</table></div>\n";

		out.print(str);
	}

	private int makeAlterTable(java.sql.ResultSet rs, ResultSet airline, ResultSet ar1, ResultSet ar2,
			java.io.PrintWriter out) throws Exception {
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
//					if(rsmd.getColumnLabel(i+1).equals("FlightNumber")) {
//						out.println("<TD>");
//						out.println("<input name=\"click\" type=\"submit\" value=\""+ rs.getString(i+1)  +"\" /> ");
//						
//					}else {
				if (rsmd.getColumnLabel(i + 1).equals("FlightNumber")) {

					out.println("<td><input type=\"text\" name=\"FlightNumber\" value = \""
							+ rs.getString("FlightNumber") + "\" /></td>\n");

				} else if (rsmd.getColumnLabel(i + 1).equals("AirlineName")) {

					out.println(" <td width = \"14%\"><select name = \"AirlineName\" size =\"1\">\n\t");
					do {
						// String option= "<option value = \"" + aircraft.getString("TailNumber") + "\""
						// + aircraft.getString("TailNumber") + "</option>";\
						try {
							if (airline.getString("Name").equals(rs.getString("AirlineName"))) {
								String option = "<option value = \"" + airline.getString("Code") + ";"
										+ airline.getString("Name") + "\" selected>" + airline.getString("Name")
										+ "</option>\n";
								out.write(option);
							} else {
								String option = "<option value = \"" + airline.getString("Code") + ";"
										+ airline.getString("Name") + "\">" + airline.getString("Name") + "</option>\n";
								out.write(option);
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} while (airline.next());
					out.write("</select></td>");

				} else if (rsmd.getColumnLabel(i + 1).equals("DepartureCity")) {

					out.println(" <td width = \"14%\"><select name = \"Departure\" size =\"1\">\n\t");
					do {
						// String option= "<option value = \"" + aircraft.getString("TailNumber") + "\""
						// + aircraft.getString("TailNumber") + "</option>";\
						try {
							if (ar1.getString("AirportCode").equals(rs.getString("DepartureCity"))) {
								String option = "<option value = \"" + ar1.getString("AirportCode") + "\" selected>"
										+ ar1.getString("AirportName") + "</option>\n";
								out.write(option);
							} else {
								String option = "<option value = \"" + ar1.getString("AirportCode") + "\">"
										+ ar1.getString("AirportName") + "</option>\n";
								out.write(option);
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} while (ar1.next());
					out.write("</select></td>");
				} else if (rsmd.getColumnLabel(i + 1).equals("DestinationCity")) {

					out.println(" <td width = \"14%\"><select name = \"Arrival\" size =\"1\">\n\t");
					do {
						// String option= "<option value = \"" + aircraft.getString("TailNumber") + "\""
						// + aircraft.getString("TailNumber") + "</option>";\
						try {
							if (ar2.getString("AirportCode").equals(rs.getString("DestinationCity"))) {
								String option = "<option value = \"" + ar2.getString("AirportCode") + "\" selected>"
										+ ar2.getString("AirportName") + "</option>\n";
								out.write(option);
							} else {
								String option = "<option value = \"" + ar2.getString("AirportCode") + "\">"
										+ ar2.getString("AirportName") + "</option>\n";
								out.write(option);
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} while (ar2.next());
					out.write("</select></td>");

				} else {
					if (rsmd.getColumnLabel(i + 1).equals("DepartureTime")) {
						out.println("<td><input type=\"text\" name=\"DepartureTime\" value = \""
								+ rs.getString("DepartureTime") + "\" /></td>\n");
					}
					if (rsmd.getColumnLabel(i + 1).equals("ArrivalTime")) {
						out.println("<td><input type=\"text\" name=\"ArrivalTime\" value = \""
								+ rs.getString("ArrivalTime") + "\" /></td>\n");
					}
				}
				// }
			}
//				out.println("<input name=\"click\" type=\"submit\" value=\"Book for User\" /> ");
			out.println("</TR>");
		}
		out.println("<TR>");
		out.println("<td><input name=\"click\" type=\"submit\" value=\"Update\" /> </td>");
		out.println("<td><input name=\"click\" type=\"submit\" value=\"Delete\" /> </td></TR>");
		out.println("</TABLE></P>");
		return rowCount;
	}

	

	private int makeTable(java.sql.ResultSet rs, ResultSet airplane, java.io.PrintWriter out) throws Exception {
		int rowCount = 0;
		out.println("<P ALIGN='center'><TABLE BORDER=1>");
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		// table header
		out.println("<TR>");
		for (int i = 0; i < columnCount; i++) {
			if(!rsmd.getColumnLabel(i + 1).equals("FlightsID")) {
				out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
			}else {
				out.println("<TH>" + "Flies" + "</TH>");
			}
		}
		out.println("</TR>");
//		if(true) {
		for (int j = 1; j <= 7; j++) {
			out.println("<TR><td><input name=\"flies_" + j + "\" type=\"checkbox\"");
			String str = "";
			if(!rs.next())
			{
				str = "0";
			}
			else {
				str = rs.getString("Day");
			}
			if(Integer.parseInt(str) == j) {
				out.println("checked>");
				for (int i = 0; i < columnCount; i++) {
					if (rsmd.getColumnLabel(i + 1).equals("FlightsID")) {
//							out.println("<TD>");
//							out.println("<input name=\"click\" type=\"submit\" value=\"" + rs.getString(i + 1) + "\" /> ");
	
					}else if(rsmd.getColumnLabel(i+1).equals("AircraftID")){
						

						out.println(" <td width = \"14%\"><select name = \"AircraftID_"+ j +"\" size =\"1\">\n\t");
						do {

							try {
								if (airplane.getInt("AircraftID") == (rs.getInt("AircraftID"))) {
									String option = "<option value = \"" + airplane.getString("AircraftID") + "\" selected>"
											+ airplane.getString("Model") + "</option>\n";
									out.write(option);
								} else {
									String option = "<option value = \"" + airplane.getString("AircraftID") + "\">"
											+ airplane.getString("Model") + "</option>\n";
									out.write(option);
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						} while (airplane.next());
						out.write("</select></td>");
						airplane.beforeFirst();
						
						
					}else if(rsmd.getColumnLabel(i+1).equals("PublishedEconomyPrice")) {
						out.println("<td><input type=\"text\" name=\"EconomyPrice_"+j+"\" value = \""+ rs.getString(i+1) +"\"></td>");
					}else if(rsmd.getColumnLabel(i+1).equals("PublishedBusinessPrice")) {
						
						out.println("<td><input type=\"text\" name=\"BusinessPrice_"+j+"\" value = \""+ rs.getString(i+1) +"\"></td>");
					
					}else if(rsmd.getColumnLabel(i+1).equals("PublishedFirstPrice")) {
						
						out.println("<td><input type=\"text\" name=\"FirstPrice_"+j+"\" value = \""+ rs.getString(i+1) +"\"></td>");
					
					} else {
						
						out.println("<TD>" + rs.getString(i + 1) + "</TD>");
						
					}
	
	//			out.println("<input name=\"click\" type=\"submit\" value=\"Book for User\" /> ");
				}
				//rs.next();
			}
			else {
				//if not in the list
				//flightNumber
				rs.previous();
				out.println("><td>"+this.getFlightNumber()+"</td>");
				//airplane
				out.println("<td width = \"14%\"><select name = \"AircraftID_"+ j +"\" size =\"1\">\n\t");
				do {

					try {
					
						String option = "<option value = \"" + airplane.getString("AircraftID")+ "\">"
								+ airplane.getString("Model") + "</option>\n";
						out.write(option);
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} while (airplane.next());
				out.write("</select></td>");
				airplane.beforeFirst();
				
				//day
				out.println("<td>"+j+"</td>");
				out.println("<td><input type=\"text\" name=\"EconomyPrice_"+j+"\" value = \"\"></td>");
				out.println("<td><input type=\"text\" name=\"BusinessPrice_"+j+"\" value = \"\"></td>");
				out.println("<td><input type=\"text\" name=\"FirstPrice_"+j+"\" value = \"\"></td>");
			}
			out.println("</TR>");
			
		}

		out.println("<TR><td><input name=\"click\" type=\"submit\" value=\"Update dayFlight\" /> </td>");
		//out.println("<td><input name=\"click\" type=\"submit\" value=\"Delete dayFlight\" /> </td></TR>");
		out.println("</TABLE></P>");
		return rowCount;
	}

	protected void finishHtml(java.io.PrintWriter out) {
		String str = "</div>\n" +

				"	</form>\n" +
//	    			"<p>${error}</p>"+
				"</body>\n" + "</html>";
		out.write(str);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditFlight() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String _flightNum = (String) request.getAttribute("_flight");
		
		if (_flightNum != null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia", "ssg103",
						"password");

				PreparedStatement stmt;

				stmt = con.prepareStatement("Select * from FlightInfoTable where FlightNumber = ?");
				this.setFlightNumber(_flightNum);
				stmt.setString(1, _flightNum);

				PreparedStatement stmt3 = con.prepareStatement("Select * from AirlineTable");
				PreparedStatement stmt4 = con.prepareStatement("Select * from AirportTable");
				PreparedStatement stmt5 = con.prepareStatement("Select * from AirportTable");
				PreparedStatement stmt6 = con.prepareStatement("Select * from FlightsTable where FlightNumber = ?");
				PreparedStatement stmt7 = con.prepareStatement("Select ac.AircraftID, ac.Model from AircraftTable ac"
																+ " join AirlineTable at on ac.Airline = at.Code  where at.Name = ?");
				stmt6.setString(1, this.flightNumber);
				
				//
				ResultSet rs = stmt.executeQuery();

				ResultSet rs3 = stmt3.executeQuery();
				ResultSet rs4 = stmt4.executeQuery();
				ResultSet rs5 = stmt5.executeQuery();
				ResultSet rs6 = stmt6.executeQuery();
				
				String airlineName = "";
				if(rs.next()) {
					airlineName = rs.getString("AirlineName");
					rs.beforeFirst();
					stmt7.setString(1, airlineName);
				}
				ResultSet rs7 = stmt7.executeQuery();
				//
				//
				initialHtml(response.getWriter());

				rs3.next();
				rs4.next();
				rs5.next();

				makeAlterTable(rs, rs3, rs4, rs5, response.getWriter());
				makeTable(rs6, rs7, response.getWriter());

				if (this.getError().length() > 0) {
					response.getWriter().println("<p>" + this.getError() + "</p>");
				}
				finishHtml(response.getWriter());
				con.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("click").equals("Update")) {
			String flightNumber = request.getParameter("FlightNumber");

			String airlineCombo = request.getParameter("AirlineName");
			String airlineName = airlineCombo.substring(airlineCombo.indexOf(';') + 1);
			String code = airlineCombo.substring(0, airlineCombo.indexOf(';'));
			String departure = request.getParameter("Departure");
			String arrival = request.getParameter("Arrival");
			Time departureTime = new Time(0);

			String _tdemp = request.getParameter("DepartureTime");
			Time arrivalTime = new Time(0);
			String _tarr = request.getParameter("ArrivalTime");

			this.setError("");
			String _error = "";

			if (flightNumber == null) {
				_error = "Flight number cannot be empty. ";
			} else {
				if (flightNumber.length() == 0) {
					_error = "Flight number cannot be empty. ";
				}

			}

			if (airlineName == null) {
				_error += "Airline name cannot be empty. ";
			} else {
				if (airlineName.length() == 0) {
					_error += "Airline name cannot be empty. ";
				} else {
					if (flightNumber.length() > 0) {
						if (!flightNumber.contains(code)) {
							flightNumber = code + flightNumber;
						}
					}
				}
			}
			if (departure == null) {
				_error += "Departure name cannot be empty. ";
			} else {
				if (departure.length() != 3) {
					_error += "Departure name must have 3 characters. ";
				}

			}
			if (arrival == null) {
				_error += "Arrival name cannot be empty. ";
			} else {
				if (arrival.length() != 3) {
					_error += "Arrival name must have 3 characters. ";
				}
			}
			if (_tdemp == null) {
				_error += "Departure time cannot be empty. ";
			} else {
				if (_tdemp.length() == 0) {
					_error += "Departure time cannot be empty. ";
				} else {
					try {
						if (_tdemp.length() == 4) {
							_tdemp = "0" + _tdemp;
						}
						LocalTime.parse(_tdemp);
						// System.out.println("Valid time string: " + _tdemp);
						DateFormat dateFormat = new SimpleDateFormat("hh:mm");
						departureTime = new java.sql.Time(dateFormat.parse(_tdemp).getTime());

					} catch (Exception e) {
						_error += "Invalid time for departure time";
					}
				}
			}
			if (_tarr == null) {
				_error += "Arrival time cannot be empty";
			} else {
				if (_tarr.length() == 0) {
					_error += "Arrival time cannot be empty";
				} else {
//					try {
//						DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//						dateFormat.parse(arrivalTime);
//					
//					}catch(Exception e) {
//						_error += e.getMessage();
//					}
					try {
						LocalTime.parse(_tarr);
						DateFormat dateFormat = new SimpleDateFormat("hh:mm");

						arrivalTime = new java.sql.Time(dateFormat.parse(_tarr).getTime());
					} catch (Exception e) {
						// System.out.println("Invalid time string: " + arrivalTime);
						_error += "Invalid time for arrival time";
					}
				}
			}
			if (_error.length() == 0) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection(
							"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia",
							"ssg103", "password");
					PreparedStatement stmt = con.prepareStatement(
							"Update FlightInfoTable set FlightNumber = ?, AirlineName = ?, DepartureCity = ?, DestinationCity = ?, DepartureTime = ?, ArrivalTime = ? where FlightNumber = ?");

					stmt.setString(1, flightNumber);

					stmt.setString(2, airlineName);
					stmt.setString(3, departure);
					stmt.setString(4, arrival);
					stmt.setTime(5, departureTime);
					stmt.setTime(6, arrivalTime);
					stmt.setString(7, this.getFlightNumber());
					stmt.executeUpdate();
					con.close();

					response.sendRedirect("ManageFlights");
					return;

				} catch (Exception e) {
					_error += e.getMessage();

					System.out.println(e.getMessage());
				}
			}

			if (_error.length() > 0) {
				this.setError(_error);
			}
		} else if (request.getParameter("click").contentEquals("Delete")) {
			try {

				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia", "ssg103",
						"password");
				PreparedStatement stmt = con.prepareStatement("Delete from FlightInfoTable where FlightNumber = ?");

				stmt.setString(1, this.getFlightNumber());

				stmt.executeUpdate();
				con.close();

				response.sendRedirect("ManageFlights");
				return;

			} catch (Exception e) {
				this.setError(e.getMessage());
				System.out.println(e.getMessage());
			}
		}
		else if(request.getParameter("click").equals("Update dayFlight")) {
			try {
			String error = "";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://cs336-summer19db.cfgjjfomqrbi.us-east-2.rds.amazonaws.com/RuExpedia", "ssg103",
					"password");
				for(int i = 1; i<= 7; i++) {
					
					String aircraftStr = request.getParameter("AircraftID_"+i);
					String isActive = request.getParameter("flies_" + i);
					String ecoPriceStr = request.getParameter("EconomyPrice_" +i);
					String busPriceStr = request.getParameter("BusinessPrice_" +i);
					String firPriceStr = request.getParameter("FirstPrice_" +i);
					
					int ecoPrice = 0;
					int busPrice = 0;
					int firPrice = 0;
					int aircraft = 0;
					
					boolean update = false;
					boolean inDB = false;
					
					if(isActive != null) {
						update = true;
					}
					
					
					if(update == true) {
						if(aircraftStr != null) {
							aircraft = Integer.parseInt(aircraftStr);
						}else {
							error += "Please add an aircraft for airline. ";
						}
						if(ecoPriceStr != null) {
							try {
								ecoPrice = Integer.parseInt(ecoPriceStr);
							}catch(Exception e) {
								error += "Eco price cannot be empty. ";
							}
						}else {
							error += "Eco price cannot be empty. ";
						}
						if(busPriceStr != null) {
							try {
								busPrice = Integer.parseInt(busPriceStr);
							}catch(Exception e) {
								error += "Bus price cannot be empty. ";
							}
						}else {
							error += "Bus price cannot be empty. ";
						}
						if(firPriceStr != null) {
							try {
								firPrice = Integer.parseInt(firPriceStr);
							}catch(Exception e) {
								error += "Fir price cannot be empty. ";
							}
						}else {
							error += "Fir price cannot be empty. ";
						}
					}
					
					
					
					
					
					
					
					//check if in sql
					
				
					
					PreparedStatement stmt = con.prepareStatement("SELECT count(FlightNumber) FROM FlightsTable where FlightNumber = ? and Day = ?");
					
					stmt.setString(1, this.getFlightNumber());
					stmt.setString(2, ""+i);
					
					ResultSet rs = stmt.executeQuery();
					if(rs.next()) {
						int c = rs.getInt(1);
						if(c >0) {
							inDB = true;
						}
					}
					
					
					if(inDB == true) {
						if(update == true) {
							if(error.length() == 0) {
								//update
								PreparedStatement stmt2 = con.prepareStatement("Update FlightsTable set AircraftID = ?, PublishedEconomyPrice = ?, PublishedBusinessPrice = ?, PublishedFirstPrice = ? where FlightNumber = ? and Day = ?");
								stmt2.setInt(1, aircraft);
								stmt2.setInt(2, ecoPrice);
								stmt2.setInt(3, busPrice);
								stmt2.setInt(4, firPrice);
								stmt2.setString(5, this.getFlightNumber());
								stmt2.setString(6, ""+i);
								stmt2.executeUpdate();
							}
						}
						else {
							//delete from table
							PreparedStatement stmt2 = con.prepareStatement("Delete from FlightsTable where FlightNumber = ? and Day = ?");
							stmt2.setString(1, this.getFlightNumber());
							stmt2.setString(2, ""+i);
							stmt2.executeUpdate();
						}
					}
					else {
						if(update == true) {
							//needs to be added
							PreparedStatement stmt2 = con.prepareStatement("Insert into FlightsTable (FlightNumber, Day, AircraftID, PublishedEconomyPrice, PublishedBusinessPrice, PublishedFirstPrice) values (?,?,?,?,?,?)");
							stmt2.setString(1, this.getFlightNumber());
							stmt2.setString(2, ""+i);
							stmt2.setInt(3, aircraft);
							stmt2.setInt(4, ecoPrice);
							stmt2.setInt(5, busPrice);
							stmt2.setInt(6, firPrice);
							stmt2.executeUpdate();
							
						}
					}
					
				}
				request.setAttribute("_flight", this.getFlightNumber());
				con.close();
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
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
			response.sendRedirect("ManageAirlines");
			return;
		}
		if(request.getParameter("click").equals("ManageAirports")) {
			//return;
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
		doGet(request, response);
	}

}
