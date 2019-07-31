<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
td {
  height: 20px;
  /* horizontal-align: middle; */
  vertical-align:top;
}

table, td, th {
  /* border: 1px solid black; */
  border-collapse: collapse;
}


tr:nth-child(even) {background-color: #f2f2f2;}

</style>
<meta charset="UTF-8">
<title>Sign Up</title>
</head>
<body>

	<div class = "container">
		<form action="SignUp" method="post">
			<table>
				<tr style = "height:20px">
					<td>First Name</td>
					<td><input type="text" name="first_name" />
					<td><p style="color:Red; font-size: 15px;">${error0}</p></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td><input type="text" name="last_name" /></td>
					<td><p style=" color:Red; font-size: 15px;">${error1}</p></td>
				</tr>
					<tr>
					<td>Password</td>
					<td><input type="password" name="password" /></td>
					<td><p style=" color:Red; font-size: 15px;">${error3}</p></td>
				</tr>
				<tr>
					<td>EmailAddress</td>
					<td><input type="text" name="email" /></td>
					<td><p style=" color:Red; font-size: 15px;">${error4}</p></td>
				</tr>
				</table>
			<input type="submit" value="signup" />
		</form>
	</div>

</body>
</html>