<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,java.util.*, java.text.DateFormat, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search your next flight</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker_from" ).datepicker();
    $( "#datepicker_return" ).datepicker();
  } );
  </script>

</head>

<body>


	<h2 style="text-align: center;">Ru expedia</h2>
	<form action="Main" method="post">
		<div>

			<table style="height: 32px; float: right;">
				<tbody>
					<tr>
						<td><p>${value}</p></td>
					</tr>
					<tr>
						<td>
							<%%> <input name="click" type="submit" value=Dashboard></input>
						</td>
						<td style="width: 37.671875px;"><input name="click"
							type="submit" value="signup" /></td>
						<td style="width: 37.671875px;"><input name="click"
							type="submit" value="login" /></td>
						<td style="width: 37.671875px;"><input name="click"
							type="submit" value="log out" /></td>
					</tr>
				</tbody>
			</table>

		</div>
		<br> <br> <br> <br> <br> <br> <br>
		<table
			style="height: 54px; margin-left: auto; margin-right: auto; width: 235.921875px;">
			<tbody>
				<tr style="height: 27px;">
					<td style="width: 74px; height: 27px; text-align: center;">From</td>
					<td style="width: 74.921875px; height: 27px; text-align: center;">
						${_departureCityDroddown}
						
					</td>
					<td style="width: 74px; height: 27px; text-align: center;">To</td>
					<td style="width: 74.921875px; height: 27px; text-align: center;">
						${_returnCityDroddown}
					</td>
				</tr>
				<tr>
					<td>One Way</td>
					<td><input name="tripType" type="radio" value="one_way"></td>
					<td>Round trip</td>
					<td><input name="tripType" type="radio" checked value="round_trip"></td>
				</tr>
				<tr>
					<td style="width: 74px; height: 27px; text-align: center;">When</td>
					<td style="width: 74.921875px; height: 27px; text-align: center;">
						<input type="date" name="fromDate" value=${_fromDate} id="datepicker_from">
					</td>
					<td style="width: 74px; height: 27px; text-align: center;">Return</td>
					<td style="width: 74.921875px; height: 27px; text-align: center;">
						
						<input type="date" name="returnDate" value=${_returnDate} id="datepicker_return">
					</td>
				</tr>
			</tbody>
		</table>
		<p style="text-align: center; color: red; font-size: 15px;">
			<input name="click" type="submit" value="Search" />
		</p>
	</form>
	<p>${error}</p>
</body>
</html>