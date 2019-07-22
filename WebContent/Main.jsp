<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,java.util.*, java.text.DateFormat, java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<%-- 		<p>${value}</p>
		<form action="Main" method="post">
			<table style="height: 32px; float: right;" width="200">
				<tbody>
					<tr>
					  <td style="width: 37.671875px;"><input name="click" type="submit" value="signup" /></td>
					  <td style="width: 37.671875px;"><input name="click" type="submit" value="login" /></td>
					  <td style="width: 37.671875px;"><input name="click" type="submit" value="log out" /></td>
					</tr>	
				</tbody>
			</table>
		</form>
 --%>

	<h2 style="text-align: center;">Ru expedia</h2>
	<form action="Main" method="post">
		<div>

			<table style="height: 32px; float: right;">
				<tbody>
					<tr>
						<td><a href="dashboard" type="submit">${value}</a></td>
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
					<td style="width: 74.921875px; height: 27px; text-align: center;"><input
						name="origin" type="text" /></td>
					<td style="width: 74px; height: 27px; text-align: center;">To</td>
					<td style="width: 74.921875px; height: 27px; text-align: center;"><input
						name="destination" type="text" /></td>
				</tr>
				<tr>
					<td>One Way</td>
					<td><input name="oneWay" type="checkbox" /></td>
					<td>Round trip</td>
					<td><input name="roundTrip" type="checkbox" checked /></td>
				</tr>
				<tr>
					<td style="width: 74px; height: 27px; text-align: center;">When</td>
					<td style="width: 74.921875px; height: 27px; text-align: center;">
						<input type="date" name="fromDate"
						value=<%DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			out.print(dateFormat.format(date));%>>
					</td>
					<td style="width: 74px; height: 27px; text-align: center;">Return</td>
					<td style="width: 74.921875px; height: 27px; text-align: center;"><input
						name="returnDate" type="date" /></td>
				</tr>
			</tbody>
		</table>
		<p style="text-align: center;">
			<input name="click" type="submit" value="Search" />
		</p>
	</form>
	<p>${error}</p>
</body>
</html>