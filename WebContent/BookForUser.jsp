<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,java.util.*, java.text.DateFormat, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book for user</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker_from").datepicker();

	});
</script>

</head>

<body>
	<form action="BookForUser" method="post">
		<div>
			<table style="height: 51px; width: 100px; float: left;" border="1">
				<tbody>

					<tr style="height: 27px;">
						<td style="width: 260px; height: 27px; text-align: center;"><input
							name="click" type="submit" value="BookUser" /></td>
					</tr>
					<tr style="height: 27px;">
						<td style="width: 260px; height: 27px; text-align: center;"><input
							name="click" type="submit" value="ChangeFlight" /></td>
					</tr>
					<tr style="height: 27px;">
						<td style="width: 260px; height: 27px; text-align: center;"><input
							name="click" type="submit" value="ManageFlights" /></td>
					</tr>
					<tr style="height: 27px;">
						<td style="width: 260px; height: 27px; text-align: center;"><input
							name="click" type="submit" value="ManageAirports" /></td>
					</tr>
					<tr style="height: 27px;">
						<td style="width: 260px; height: 27px; text-align: center;"><input
							name="click" type="submit" value="ManageAirlines" /></td>
					</tr>
					<tr style="height: 27px;">
						<td style="width: 260px; height: 27px; text-align: center;"><input
							name="click" type="submit" value="ManageAirplanes" /></td>
					</tr>
					<tr style="height: 27px;">
						<td style="width: 260px; height: 27px; text-align: center;"><input
							name="click" type="submit" value="GetWaitlist" /></td>
					</tr>

				</tbody>
			</table>


		</div>
		<br> <br> <br> <br> <br> <br> <br>
		<table
			style="height: 54px; margin-left: auto; margin-right: auto; width: 235.921875px;">
			<tbody>
				<tr>
					<td>Email Address</td>
					<td>Flight Number</td>
					<td>Date</td>
				</tr>
				<tr>
					<td><input type="text" name="Email" /></td>
					<td><input type="text" name="FlightNumber" /></td>
					<td style="width: 74.921875px; height: 27px; text-align: center;">
						<input type="date" name="fromDate" value=${_fromDate
						} id="datepicker_from">
					</td>
				</tr>
			</tbody>
		</table>
		<p style="text-align: center; color: red; font-size: 15px;">
			<input name="click" type="submit" value="Next" />
		</p>
	</form>
	<p>${error}</p>
</body>
</html>